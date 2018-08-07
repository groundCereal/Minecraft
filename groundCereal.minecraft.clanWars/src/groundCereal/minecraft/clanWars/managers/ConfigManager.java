package groundCereal.minecraft.clanWars.managers;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.LocationType;
import groundCereal.minecraft.clanWars.helpers.ConfigHelper;

/**
 * @author groundCereals
 *
 *         Manages the configuration settings
 */
public class ConfigManager {
	private ConfigManager instance;
	
	private List<String> administrators;
	private ChatColor errorMessageColor;
	private ChatColor successMessageColor;
	private ChatColor broadcastMessageColor;
	private ChatColor commandMessageColor;
	private ChatColor highlightMessageColor;
	private String mainCommand;
	private String lobbyCommand;
	private String endLobbyCommand;
	private String balconyCommand;
	private String aClanSpawnCommand;
	private String bClanSpawnCommand;
	private String challengeCommand;
	private String acceptCommand;
	private String declineCommand;
	private String loginCommand;
	private Location lobbyLocation;
	private Location endLobbyLocation;
	private Location balconyLocation;
	private Location aSpawnLocation;
	private Location bSpawnLocation;
	private int minimumPlayers;
	private int challengeTimeout;
	private int selectionTimeout;
	private int loginTimeout;
	private int pickupTimeout;
	private int countdownSeconds;
	private int prize;
	private Boolean allowExcessPlayers;
	
	public ConfigManager() {
		instance = this;
		
		FileConfiguration config = ClanWars.getInstance().getConfig();

		administrators = config.getStringList("administrators");
		mainCommand = config.getString("command.main");
		lobbyCommand = config.getString("command.lobby");
		endLobbyCommand = config.getString("command.endLobby");
		balconyCommand = config.getString("command.balcony");
		aClanSpawnCommand = config.getString("command.aClanSpawn");
		bClanSpawnCommand = config.getString("command.bClanSpawn");
		challengeCommand = config.getString("command.challenge");
		acceptCommand = config.getString("command.accept");
		declineCommand = config.getString("command.decline");
		loginCommand = config.getString("command.login");
		errorMessageColor = ConfigHelper.getChatColor(config, "messageColor.error");
		successMessageColor = ConfigHelper.getChatColor(config, "messageColor.success");
		broadcastMessageColor = ConfigHelper.getChatColor(config, "messageColor.broadcast");
		commandMessageColor = ConfigHelper.getChatColor(config, "messageColor.command");
		highlightMessageColor = ConfigHelper.getChatColor(config, "messageColor.highlight");
		lobbyLocation = ConfigHelper.getLocation(LocationType.LOBBY);
		endLobbyLocation = ConfigHelper.getLocation(LocationType.END_LOBBY);
		balconyLocation = ConfigHelper.getLocation(LocationType.BALCONY);
		aSpawnLocation = ConfigHelper.getLocation(LocationType.A_CLAN_SPAWN);
		bSpawnLocation = ConfigHelper.getLocation(LocationType.B_CLAN_SPAWN);
		
		minimumPlayers = config.getInt("battle.minPlayers");
		allowExcessPlayers = config.getBoolean("battle.allowExcessPlayers");
		challengeTimeout = config.getInt("battle.timeout.challenge");
		selectionTimeout = config.getInt("battle.timeout.selection");
		loginTimeout = config.getInt("battle.timeout.login");
		pickupTimeout = config.getInt("battle.timeout.pickup");
		countdownSeconds = config.getInt("battle.countdownSeconds");
		prize = config.getInt("battle.prize");
	}

	public ConfigManager getInstance() {
		return instance;
	}
	
	public List<String> getAdministrators() {
		return administrators;
	}

	public String getMainCommand() {
		if (mainCommand == null) {
			return "cwars";
		} else {
			return mainCommand;
		}
	}

	public String getLobbyCommand() {
		if (lobbyCommand == null) {
			return "lobby";
		} else {
			return lobbyCommand;
		}
	}

	public String getEndLobbyCommand() {
		if (endLobbyCommand == null) {
			return "endlobby";
		} else {
			return endLobbyCommand;
		}
	}
	
	public String getBalconyCommand() {
		if (balconyCommand == null) {
			return "balcony";
		} else {
			return balconyCommand;
		}
	}

	public String getAClanSpawnCommand() {
		if (aClanSpawnCommand == null) {
			return "aSpawn";
		} else {
			return aClanSpawnCommand;
		}
	}

	public String getBClanSpawnCommand() {
		if (bClanSpawnCommand == null) {
			return "bSpawn";
		} else {
			return bClanSpawnCommand;
		}
	}
	
	public String getChallengeCommand() {
		if (challengeCommand == null) {
			return "desafiar";
		} else {
			return challengeCommand;
		}
	}

	public String getAcceptCommand() {
		if (acceptCommand == null) {
			return "aceitar";
		} else {
			return acceptCommand;
		}
	}

	public String getDeclineCommand() {
		if (declineCommand == null) {
			return "recusar";
		} else {
			return declineCommand;
		}
	}
	
	public String getLoginCommand() {
		if (loginCommand == null) {
			return "entrar";
		} else {
			return loginCommand;
		}
	}


	public ChatColor getErrorMessageColor() {
		if (errorMessageColor == ChatColor.RESET) {
			return ChatColor.DARK_RED;
		} else {
			return errorMessageColor;
		}
	}

	public ChatColor getSuccessMessageColor() {
		if (successMessageColor == ChatColor.RESET) {
			return ChatColor.DARK_GREEN;
		} else {
			return successMessageColor;
		}
	}

	public ChatColor getBroadcastMessageColor() {
		if (broadcastMessageColor == ChatColor.RESET) {
			return ChatColor.GOLD;
		} else {
			return broadcastMessageColor;
		}
	}

	public ChatColor getCommandMessageColor() {
		if (commandMessageColor == ChatColor.RESET) {
			return ChatColor.AQUA;
		} else {
			return commandMessageColor;
		}
	}
	
	public ChatColor getHighlightMessageColor() {
		if (highlightMessageColor == ChatColor.RESET) {
			return ChatColor.GREEN;
		} else {
			return highlightMessageColor;
		}
	}

	public Location getLobbyLocation() {
		return lobbyLocation;
	}
	
	public Location getEndLobbyLocation() {
		return endLobbyLocation;
	}
	
	public Location getBalconyLocation() {
		return balconyLocation;
	}
	
	public Location getAClanSpwanLocation() {
		return aSpawnLocation;
	}
	
	public Location getBClanSpwanLocation() {
		return bSpawnLocation;
	}

	public int getMinimumPlayers() {
		if (minimumPlayers == 0) {
			return 3;
		} else {
			return minimumPlayers;
		}
	}

	public int getChallengeTimeout() {
		if (challengeTimeout == 0) {
			return 120;
		} else {
			return challengeTimeout;
		}
	}

	public int getSelectionTimeout() {
		if (selectionTimeout == 0) {
			return 120;
		} else {
			return selectionTimeout;
		}
	}
	
	public int getLoginTimeout() {
		if (loginTimeout == 0) {
			return 120;
		} else {
			return loginTimeout;
		}
	}

	public int getPickupTimeout() {
		if (pickupTimeout == 0) {
			return 60;
		} else {
			return pickupTimeout;
		}
	}

	public Boolean getAllowExcessPlayers() {
		return allowExcessPlayers;
	}

	public int getCountdownSeconds() {
		if (countdownSeconds == 0) {
			return 10;
		} else {
			return countdownSeconds;
		}
	}

	public int getPrize() {
		if (prize == 0) {
			return 100000;
		} else {
			return prize;
		}
	}

}
