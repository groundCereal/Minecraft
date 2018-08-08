package groundCereal.minecraft.clanWars;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.helpers.Timers;
import groundCereal.minecraft.clanWars.managers.CommunicationManager;
import net.sacredlabyrinth.phaed.simpleclans.Clan;

public class Battle {
	private Clan challengingClan;
	private Clan challengedClan;
	private int nbrPlayers;
	private List<BattlePlayer> challengingClanPlayers;
	private List<BattlePlayer> challengedClanPlayers;
	private BattleStatus status;
	private int challengingClanAlive;
	private int challengedClanAlive;
	private Player challengingPlayer;
	private Player acceptedPlayer;

	public Battle() {
		setStatus(BattleStatus.NO_BATTLE);
		this.challengingClanPlayers = new ArrayList<BattlePlayer>();
		this.challengedClanPlayers = new ArrayList<BattlePlayer>();
	}

	public Battle reset() {
		this.challengingClan = null;
		this.challengedClan = null;
		this.nbrPlayers = 0;
		this.challengingClanPlayers = new ArrayList<BattlePlayer>();
		this.challengedClanPlayers = new ArrayList<BattlePlayer>();
		this.status = BattleStatus.NO_BATTLE;
		this.challengingClanAlive = 0;
		this.challengedClanAlive = 0;
		this.challengingPlayer = null;
		this.acceptedPlayer = null;

		return this;
	}

	public Boolean battleLogin(Player player) {
		UUID playerUuid = player.getUniqueId();
		BattlePlayer battlePlayer = null;

		int idx = 0;
		while (battlePlayer == null && idx < challengingClanPlayers.size()) {
			if (challengingClanPlayers.get(idx).getPlayer().getUniqueId().equals(playerUuid)) {
				battlePlayer = challengingClanPlayers.get(idx);
			}
			idx++;
		}
		idx = 0;
		if (battlePlayer == null) {
			while (battlePlayer == null && idx < challengedClanPlayers.size()) {
				if (challengedClanPlayers.get(idx).getPlayer().getUniqueId().equals(playerUuid)) {
					battlePlayer = challengedClanPlayers.get(idx);
				}
				idx++;
			}

			if (battlePlayer == null) {
				if (this.status != BattleStatus.WAITING_FOR_PLAYERS) {
					ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(player,
							ClanWars.getInstance().getMessagesManager().getCommandNotAvailable());
				} else {
					ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(player,
							ClanWars.getInstance().getMessagesManager().getNotInvited());
				}
				return false;
			}
		}

		if (this.status == BattleStatus.COUNTDOWN || this.status == BattleStatus.BATTLE_ON || this.status == BattleStatus.BATTLE_FINISHED) {
			ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(player, ClanWars.getInstance().getMessagesManager().getAlreadyStarted());
			return false;
		}
		if (this.status != BattleStatus.WAITING_FOR_PLAYERS) {
			ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(player,
					ClanWars.getInstance().getMessagesManager().getCommandNotAvailable());
			return false;
		}

		Boolean battleFull = false;
		synchronized (this) {
			if (battlePlayer.getIsChallenging()) {
				if (this.challengingClanAlive == this.nbrPlayers) {
					battleFull = true;
				}
			} else {
				if (this.challengedClanAlive == this.nbrPlayers) {
					battleFull = true;
				}
			}
		}
		if (battleFull) {
			ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(player, ClanWars.getInstance().getMessagesManager().getBattleFull());
			return false;
		}

		battlePlayer.setAcceptedInvitation(true);
		battlePlayer.setIsAlive(true);

		synchronized (this) {
			if (battlePlayer.getIsChallenging()) {
				this.challengingClanAlive++;
			} else {
				this.challengedClanAlive++;
			}
		}

		if (this.challengingClanAlive == this.nbrPlayers && this.challengedClanAlive == this.nbrPlayers) {
			this.status = BattleStatus.COUNTDOWN;
			Timers.launchCountdown();
		}

		return true;
	}

	public void broadcastAll(String message) {
		this.broadcastToClan(true, message);
		this.broadcastToClan(false, message);
	}

	public void broadcastToClan(Boolean challengingClan, String message) {
		CommunicationManager commMgr = ClanWars.getInstance().getCommunicationManager();

		if (challengingClan) {
			for (BattlePlayer battlePlayer : this.challengingClanPlayers) {
				if (battlePlayer.getIsAlive()) {
					commMgr.sendSuccessMessage(battlePlayer.getPlayer(), message);
				}
			}
		} else {
			for (BattlePlayer battlePlayer : this.challengedClanPlayers) {
				if (battlePlayer.getIsAlive()) {
					commMgr.sendSuccessMessage(battlePlayer.getPlayer(), message);
				}
			}
		}
	}

	public void startBattle() {
		for (BattlePlayer battlePlayer : this.challengingClanPlayers) {
			battlePlayer.getPlayer().teleport(ClanWars.getInstance().getConfigManager().getAClanSpwanLocation());
		}
		for (BattlePlayer battlePlayer : this.challengingClanPlayers) {
			battlePlayer.getPlayer().teleport(ClanWars.getInstance().getConfigManager().getBClanSpwanLocation());
		}
		this.status = BattleStatus.BATTLE_ON;
	}

	public BattlePlayer getBattlePlayer(Player player) {
		int idx = 0;
		while (idx < this.challengingClanPlayers.size()) {
			if (this.challengingClanPlayers.get(idx).getPlayer().getUniqueId() == player.getUniqueId()) {
				return this.challengingClanPlayers.get(idx);
			}
			idx++;
		}
		idx = 0;
		while (idx < this.challengedClanPlayers.size()) {
			if (this.challengedClanPlayers.get(idx).getPlayer().getUniqueId() == player.getUniqueId()) {
				return this.challengedClanPlayers.get(idx);
			}
			idx++;
		}
		return null;
	}

	public void endBattle() {
		for (BattlePlayer battlePlayer : this.challengingClanPlayers) {
			if (battlePlayer.getIsAlive()) {
				battlePlayer.getPlayer().teleport(ClanWars.getInstance().getConfigManager().getEndLobbyLocation());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + battlePlayer.getPlayer().getName() +  " " + String.valueOf(ClanWars.getInstance().getConfigManager().getPrize()));
			}
		}
		for (BattlePlayer battlePlayer : this.challengingClanPlayers) {
			if (battlePlayer.getIsAlive()) {
				battlePlayer.getPlayer().teleport(ClanWars.getInstance().getConfigManager().getEndLobbyLocation());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + battlePlayer.getPlayer().getName() +  " " + String.valueOf(ClanWars.getInstance().getConfigManager().getPrize()));
			}
		}
		this.status = BattleStatus.NO_BATTLE;
	}

	/**
	 * @return the challengingClan
	 */
	public Clan getChallengingClan() {
		return challengingClan;
	}

	/**
	 * @param challengingClan the challengingClan to set
	 */
	public void setChallengingClan(Clan challengingClan) {
		this.challengingClan = challengingClan;
	}

	/**
	 * @return the challengedClan
	 */
	public Clan getChallengedClan() {
		return challengedClan;
	}

	/**
	 * @param challengedClan the challengedClan to set
	 */
	public void setChallengedClan(Clan challengedClan) {
		this.challengedClan = challengedClan;
	}

	/**
	 * @return the nbrPlayers
	 */
	public int getNbrPlayers() {
		return nbrPlayers;
	}

	/**
	 * @param nbrPlayers the nbrPlayers to set
	 */
	public void setNbrPlayers(int nbrPlayers) {
		this.nbrPlayers = nbrPlayers;
	}

	/**
	 * @return the challengingClanPlayers
	 */
	public List<BattlePlayer> getChallengingClanPlayers() {
		return challengingClanPlayers;
	}

	/**
	 * @param challengingClanPlayers the challengingClanPlayers to set
	 */
	public void setChallengingClanPlayers(List<BattlePlayer> challengingClanPlayers) {
		this.challengingClanPlayers = challengingClanPlayers;
	}

	/**
	 * @return the challengedClanPlayers
	 */
	public List<BattlePlayer> getChallengedClanPlayers() {
		return challengedClanPlayers;
	}

	/**
	 * @param challengedClanPlayers the challengedClanPlayers to set
	 */
	public void setChallengedClanPlayers(List<BattlePlayer> challengedClanPlayers) {
		this.challengedClanPlayers = challengedClanPlayers;
	}

	/**
	 * @return the status
	 */
	public BattleStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(BattleStatus status) {
		this.status = status;
	}

	/**
	 * @return the challengingPlayer
	 */
	public Player getChallengingPlayer() {
		return challengingPlayer;
	}

	/**
	 * @param challengingPlayer the challengingPlayer to set
	 */
	public void setChallengingPlayer(Player challengingPlayer) {
		this.challengingPlayer = challengingPlayer;
	}

	/**
	 * @return the acceptedPlayer
	 */
	public Player getAcceptedPlayer() {
		return acceptedPlayer;
	}

	/**
	 * @param acceptedPlayer the acceptedPlayer to set
	 */
	public void setAcceptedPlayer(Player acceptedPlayer) {
		this.acceptedPlayer = acceptedPlayer;
	}

	/**
	 * @return the challengingClanDeaths
	 */
	public int getChallengingClanAlive() {
		return challengingClanAlive;
	}

	/**
	 * @param challengingClanAlive the challengingClanAlive to set
	 */
	public void setChallengingClanAlive(int challengingClanAlive) {
		this.challengingClanAlive = challengingClanAlive;
	}

	/**
	 * @return the challengedClanDeaths
	 */
	public int getChallengedClanAlive() {
		return challengedClanAlive;
	}

	/**
	 * @param challengedClanAlive the challengedClanAlive to set
	 */
	public void setChallengedClanAlive(int challengedClanAlive) {
		this.challengedClanAlive = challengedClanAlive;
	}
}
