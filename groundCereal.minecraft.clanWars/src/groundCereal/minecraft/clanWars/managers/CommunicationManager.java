package groundCereal.minecraft.clanWars.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommunicationManager {

	public void sendPlayerErrorMessage(Player player, String message) {
		message = this.highlightMessage(message, ClanWars.getInstance().getConfigManager().getErrorMessageColor());
		sendPlayerMessage(player, message, ClanWars.getInstance().getConfigManager().getErrorMessageColor());
	}

	public void sendPlayerMessage(Player player, String message, ChatColor color) {
		player.sendMessage(color + message);
	}

	public void sendSuccessMessage(Player player, String message) {
		message = this.highlightMessage(message, ClanWars.getInstance().getConfigManager().getSuccessMessageColor());
		sendPlayerMessage(player, message, ClanWars.getInstance().getConfigManager().getSuccessMessageColor());
	}

	public void sendPlayerUsageMessage(Player player, String command) {
		ConfigManager cfgMgr = ClanWars.getInstance().getConfigManager();
		MessagesManager msgMgr = ClanWars.getInstance().getMessagesManager();

		if (command == null || command.equals(cfgMgr.getChallengeCommand())) {
			player.sendMessage(
					cfgMgr.getCommandMessageColor() + cfgMgr.getMainCommand() + " " + cfgMgr.getChallengeCommand()
							+ " <" + msgMgr.getClanName() + "> <" + msgMgr.getNumberOfPlayers() + ">");
		}
	}

	public void sendBroadcastMessage(String message) {
		message = this.highlightMessage(message, ClanWars.getInstance().getConfigManager().getBroadcastMessageColor());
		Bukkit.broadcastMessage(ClanWars.getInstance().getConfigManager().getBroadcastMessageColor() + message);
	}

	public void sendClickableMessage(Player player, String message, String command, boolean displayCommand) {
		if (displayCommand) {
			message = message + "[" + command + "]";
		}
		message = this.highlightMessage(message, ClanWars.getInstance().getConfigManager().getSuccessMessageColor());
		TextComponent tc = new TextComponent(
				ClanWars.getInstance().getConfigManager().getSuccessMessageColor() + message);
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		player.spigot().sendMessage(tc);
	}

	private String highlightMessage(String message, ChatColor baseColor) {
		ChatColor highlightColor = ClanWars.getInstance().getConfigManager().getHighlightMessageColor();
		return message.replaceAll("\\[", ChatColor.BOLD + "" + highlightColor + "").replaceAll("\\]",
				ChatColor.RESET + "" + baseColor + "");
	}
}
