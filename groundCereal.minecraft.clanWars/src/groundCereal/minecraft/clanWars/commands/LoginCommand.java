package groundCereal.minecraft.clanWars.commands;

import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;

public class LoginCommand {
	private Player player;
	
	public LoginCommand(Player player) {
		this.player = player;
	}
	
	public void execute() {
		Boolean login = ClanWars.getInstance().getBattle().battleLogin(player);
		
		if (login) {
			player.teleport(ClanWars.getInstance().getConfigManager().getLobbyLocation());
		}
	}
}
