package groundCereal.minecraft.clanWars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.LocationType;

/**
 * @author groudCereal
 *
 * ClanWars plugin main command executor.
 * Parses the arguments and invokes sub command's executors
 */
public class ClanWarsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		try {
			Log.trace("-> ClanWarsCommand.onCommand: ", label, String.join(", ", args));
			ClanWars plugin = ClanWars.getInstance();

			Player senderPlayer = null;
			if (sender instanceof Player) {
				senderPlayer = (Player) sender;

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
			return true;
		} catch (Exception ex) {
			Log.error("Error onCommand", ex.getMessage(), ex.getStackTrace());
			return true;
		}
	}
}
