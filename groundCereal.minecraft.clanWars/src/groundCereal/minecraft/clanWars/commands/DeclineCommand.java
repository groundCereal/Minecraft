package groundCereal.minecraft.clanWars.commands;

import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.helpers.SimpleClansHelper;

public class DeclineCommand {
	private Player player;

	public DeclineCommand(Player player) {
		this.player = player;
	}

	public void execute() {
		ClanWars plugin = ClanWars.getInstance();
		Battle battle = plugin.getBattle();

		synchronized (battle) {
			if (battle.getStatus() != BattleStatus.CHALLENGED
					|| !SimpleClansHelper.isClanLeader(battle.getChallengedClan(), this.player)) {
				plugin.getCommunicationManager().sendPlayerErrorMessage(player,
						plugin.getMessagesManager().getCommandNotAvailable());
				return;
			}

			battle.reset();
		}
		plugin.getCommunicationManager().sendBroadcastMessage(plugin.getMessagesManager().getDeclinedBattle());

	}
}
