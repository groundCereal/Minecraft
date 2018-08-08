package groundCereal.minecraft.clanWars.commands;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.helpers.SimpleClansHelper;
import groundCereal.minecraft.clanWars.helpers.Timers;
import groundCereal.minecraft.clanWars.managers.CommunicationManager;
import groundCereal.minecraft.clanWars.managers.ConfigManager;
import groundCereal.minecraft.clanWars.managers.MessagesManager;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class ChallengeCommand {
	private Player player;
	private String[] cmdArgs;

	public ChallengeCommand(Player player, String[] args) {
		this.player = player;
		this.cmdArgs = args;
	}

	public void execute() {
		MessagesManager msgMgr = ClanWars.getInstance().getMessagesManager();
		CommunicationManager commMgr = ClanWars.getInstance().getCommunicationManager();
		ConfigManager cfgMgr = ClanWars.getInstance().getConfigManager();

		Clan challengingClan;

		if (!SimpleClansHelper.isClanLeader(this.player)) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getReservedForLeaders());
			return;
		} else {
			challengingClan = SimpleClansHelper.getPlayerClan(player);
		}

		if (this.cmdArgs.length != 3 || !StringUtils.isNumeric(this.cmdArgs[2])) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getInvalidParameters());
			commMgr.sendPlayerUsageMessage(player, cfgMgr.getChallengeCommand());
			return;
		}

		Integer nbrPlayers = Integer.parseInt(this.cmdArgs[2]);
		if (nbrPlayers < cfgMgr.getMinimumPlayers()) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getAtLeastNPlayers());
			return;
		}

		if (this.cmdArgs[1].toLowerCase().equals(challengingClan.getTag())) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getCannotChallengeSelf());
			return;
		}

		if (!SimpleClansHelper.clanExists(this.cmdArgs[1].toLowerCase())) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getUnknownClan());
			return;
		}

		if (cfgMgr.getAClanSpwanLocation() == null || cfgMgr.getBClanSpwanLocation() == null
				|| cfgMgr.getLobbyLocation() == null || cfgMgr.getEndLobbyLocation() == null) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getBattleFieldNotSet());
			return;
		}

		if (ClanWars.getInstance().getBattle().getStatus() != BattleStatus.NO_BATTLE) {
			commMgr.sendPlayerErrorMessage(player, msgMgr.getBattleAlreadyOn());
			return;
		}

		Battle battle = ClanWars.getInstance().getBattle();
		battle.setChallengedClan(SimpleClans.getInstance().getClanManager().getClan(this.cmdArgs[1]));
		battle.setChallengingClan(challengingClan);
		battle.setStatus(BattleStatus.CHALLENGED);
		battle.setChallengingPlayer(player);
		battle.setNbrPlayers(nbrPlayers);

		commMgr.sendBroadcastMessage(msgMgr.getClanChallengedClan(challengingClan.getName(), this.cmdArgs[1]));

		List<Player> leaders = SimpleClansHelper.getLeaders(battle.getChallengedClan());
		Boolean isAnyOnline = false;
		for (Player p : leaders) {
			if (p != null) {
				isAnyOnline = true;
				commMgr.sendClickableMessage((Player)p, msgMgr.getChallengeAccept(),
						"/" + cfgMgr.getMainCommand() + " " + cfgMgr.getAcceptCommand(), true);
				commMgr.sendClickableMessage((Player)p, msgMgr.getChallengeDecline(),
						"/" + cfgMgr.getMainCommand() + " " + cfgMgr.getDeclineCommand(), true);
			}
		}
		
		if (!isAnyOnline) {
			battle.reset();
			commMgr.sendBroadcastMessage(msgMgr.getNoLeaderOnline());
			return;
		}			

		Timers.launchChallengeTimer();
	}
}
