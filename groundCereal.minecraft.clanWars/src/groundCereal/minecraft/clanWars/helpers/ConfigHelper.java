package groundCereal.minecraft.clanWars.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.LocationType;

public class ConfigHelper {

	public static void setLocation(Location loc, LocationType locType) {

		try {
			String configPath = "location.";
			if (locType == LocationType.LOBBY) {
				configPath += "lobby";
			} else if (locType == LocationType.BALCONY) {
				configPath += "balcony";
			} else if (locType == LocationType.END_LOBBY) {
				configPath += "endLobby";
			} else if (locType == LocationType.A_CLAN_SPAWN) {
				configPath += "spawnA";
			} else if (locType == LocationType.B_CLAN_SPAWN) {
				configPath += "spawnB";
			}

			FileConfiguration config = ClanWars.getInstance().getConfig();
			config.set(configPath + ".world", loc.getWorld().getName());
			config.set(configPath + ".x", loc.getX());
			config.set(configPath + ".y", loc.getY());
			config.set(configPath + ".z", loc.getZ());

			ClanWars.getInstance().saveConfig();
		} catch (Exception e) {
			Bukkit.getLogger().config("Error setting location (" + locType.toString() + ")" + e.getMessage());
		}
	}

	public static Location getLocation(LocationType locType) {

		try {
			String configPath = "location.";

			if (locType == LocationType.LOBBY) {
				configPath += "lobby";
			} else if (locType == LocationType.BALCONY) {
				configPath += "balcony";
			} else if (locType == LocationType.END_LOBBY) {
				configPath += "endLobby";
			} else if (locType == LocationType.A_CLAN_SPAWN) {
				configPath += "spawnA";
			} else if (locType == LocationType.B_CLAN_SPAWN) {
				configPath += "spawnB";
			}

			if (!ClanWars.getInstance().getConfig().isSet(configPath + ".world")) {
				return null;
			}

			FileConfiguration config = ClanWars.getInstance().getConfig();
			String world = config.getString(configPath + ".world");
			Double x = config.getDouble(configPath + ".x");
			Double y = config.getDouble(configPath + ".y");
			Double z = config.getDouble(configPath + ".z");

			return new Location(Bukkit.getWorld(world), x, y, z);
		} catch (Exception e) {
			Bukkit.getLogger().config("Error getting location (" + locType.toString() + ")" + e.getMessage());
			return null;
		}
	}

	public static ChatColor getChatColor(FileConfiguration config, String path) {
		String color = config.getString(path);
		if (color == null) {
			return ChatColor.RESET;
		}
		return ChatColor.valueOf(color.toUpperCase());
	}
}
