package groundCereal.minecraft.clanWars.commands;

import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.PlayerSelectMenu;
import groundCereal.minecraft.clanWars.helpers.SimpleClansHelper;
import groundCereal.minecraft.clanWars.helpers.Timers;

public class AcceptCommand {

	private Player player;

	public AcceptCommand(Player player) {
		this.player = player;
	}

	public void execute() {
		ClanWars plugin = ClanWars.getInstance();
		Battle battle = plugin.getBattle();
		BattleStatus battleStatus = battle.getStatus();
		Boolean isInvalidCommand = false;

		synchronized (battle) {
			if (battleStatus == BattleStatus.CHALLENGED) {
				if (!SimpleClansHelper.isClanLeader(battle.getChallengedClan(), this.player)) {
					isInvalidCommand = true;
				} else {
					battle.setStatus(BattleStatus.SELECTING_PLAYERS);
				}
			} else if (battleStatus == BattleStatus.SELECTING_PLAYERS) {
				if (!this.player.equals(battle.getChallengingPlayer())) {
					isInvalidCommand = true;
				}
			} else {
				isInvalidCommand = true;
			}
		}

		if (isInvalidCommand) {
			plugin.getCommunicationManager().sendPlayerErrorMessage(player,
					plugin.getMessagesManager().getCommandNotAvailable());
			return;
		}

		battle.setAcceptedPlayer(this.player);

		if (!PlayerSelectMenu.BuildMenu(battle, this.player)) {
			plugin.getCommunicationManager().sendBroadcastMessage(
					plugin.getMessagesManager().getNotEnoughElemets(battle.getChallengedClan().getTag()));
			battle.reset();
			return;
		} else if (!PlayerSelectMenu.BuildMenu(battle, battle.getChallengingPlayer())) {
			this.player.closeInventory();
			plugin.getCommunicationManager().sendBroadcastMessage(
					plugin.getMessagesManager().getNotEnoughElemets(battle.getChallengingClan().getTag()));
			battle.reset();
			return;
		}

		Timers.launchSelectionTimer();
	}
}
