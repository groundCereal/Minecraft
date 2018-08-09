package groundCereal.minecraft.clanWars;

import org.bukkit.plugin.java.JavaPlugin;

import groundCereal.minecraft.clanWars.commands.ClanWarsCommand;
import groundCereal.minecraft.clanWars.helpers.CommandHelper;
import groundCereal.minecraft.clanWars.listeners.InventoryListener;
import groundCereal.minecraft.clanWars.listeners.PlayerListener;
import groundCereal.minecraft.clanWars.managers.CommunicationManager;
import groundCereal.minecraft.clanWars.managers.ConfigManager;
import groundCereal.minecraft.clanWars.managers.MessagesManager;
import groundCereal.minecraft.clanWars.managers.PermissionsManager;

public class ClanWars extends JavaPlugin {

	private static ClanWars instance;
	private ConfigManager configManager;
	private CommunicationManager communicationManager;
	private PermissionsManager permissionsManager;
	private MessagesManager messagesManager;
	private Battle battle;

	@Override
	public void onEnable() {
		instance = this;
		
		configManager = new ConfigManager();
		communicationManager = new CommunicationManager();
		permissionsManager = new PermissionsManager();
		messagesManager = new MessagesManager();
		battle = new Battle();

		CommandHelper.registerCommand(configManager.getMainCommand());
		this.getCommand(configManager.getMainCommand()).setExecutor(new ClanWarsCommand());
		
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public static ClanWars getInstance() {
		return instance;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}

	public PermissionsManager getPermissionsManager() {
		return permissionsManager;
	}

	public MessagesManager getMessagesManager() {
		return messagesManager;
	}

	public Battle getBattle() {
		return battle;
	}
}
