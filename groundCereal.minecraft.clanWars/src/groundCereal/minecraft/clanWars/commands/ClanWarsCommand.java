package groundCereal.minecraft.clanWars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.LocationType;

public class ClanWarsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		ClanWars plugin = ClanWars.getInstance();
		
		Player senderPlayer = null;
		if (sender instanceof Player)
		{
			senderPlayer = (Player)sender;

			if (args.length > 0) {
				String param1 = args[0];
				if (param1.toLowerCase().equals(plugin.getConfigManager().getLobbyCommand())) {
					SetLocationCommand lc = new SetLocationCommand(senderPlayer, LocationType.LOBBY, args);
					lc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getEndLobbyCommand())) {
					SetLocationCommand lc = new SetLocationCommand(senderPlayer, LocationType.END_LOBBY, args);
					lc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getBalconyCommand())) {
					SetLocationCommand lc = new SetLocationCommand(senderPlayer, LocationType.BALCONY, args);
					lc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getAClanSpawnCommand())) {
					SetLocationCommand lc = new SetLocationCommand(senderPlayer, LocationType.A_CLAN_SPAWN, args);
					lc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getBClanSpawnCommand())) {
					SetLocationCommand lc = new SetLocationCommand(senderPlayer, LocationType.B_CLAN_SPAWN, args);
					lc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getChallengeCommand())) {
					ChallengeCommand cc = new ChallengeCommand(senderPlayer, args);
					cc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getAcceptCommand())) {
					AcceptCommand ac = new AcceptCommand(senderPlayer);
					ac.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getDeclineCommand())) {
					DeclineCommand dc = new DeclineCommand(senderPlayer);
					dc.execute();
				} else if (param1.toLowerCase().equals(plugin.getConfigManager().getLoginCommand())) {
					LoginCommand lc = new LoginCommand(senderPlayer);
					lc.execute();
				}
			}
		}
		
//		if (args.length > 0)
//		{
//			switch (args[0])
//			{
//			case "vai":
//				Location pLoc = senderPlayer.getLocation();
//				Location loc = new Location(senderPlayer.getWorld(), pLoc.getX() + 20, pLoc.getY(), pLoc.getZ());
//				
//				senderPlayer.teleport(loc);
//				break;
//			case "le":
//				TextComponent tc = new TextComponent("&ccarrega aqui");
//				tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teste vai"));
//				senderPlayer.spigot().sendMessage(tc);
//			}
//		}

		return true;
	}
	

}
