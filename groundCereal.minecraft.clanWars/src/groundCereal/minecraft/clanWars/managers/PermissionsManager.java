package groundCereal.minecraft.clanWars.managers;

import java.util.List;

import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;

public class PermissionsManager {
	private PermissionsManager instance;
	
	public PermissionsManager() {
		instance = this;
	}
	
	public Boolean isAdministrator(Player player) {
		List<String> admins = ClanWars.getInstance().getConfigManager().getAdministrators();
		
		return admins.contains(player.getDisplayName());
	}
	
	public PermissionsManager getInstance() {
		return instance;
	}
}
