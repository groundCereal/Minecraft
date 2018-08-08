package groundCereal.minecraft.clanWars.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.LocationType;
import groundCereal.minecraft.clanWars.helpers.ConfigHelper;
import groundCereal.minecraft.clanWars.managers.CommunicationManager;
import groundCereal.minecraft.clanWars.managers.MessagesManager;
import groundCereal.minecraft.clanWars.managers.PermissionsManager;

public class SetLocationCommand {
	private Player player;
	private LocationType locationType;
	
	public SetLocationCommand(Player player, LocationType locType, String[] args) { 
		this.player = player;
		this.locationType = locType;
	}
	
	public void execute() {
		
		PermissionsManager permMgr = ClanWars.getInstance().getPermissionsManager();
		MessagesManager msgMgr = ClanWars.getInstance().getMessagesManager();
		CommunicationManager commMgr = ClanWars.getInstance().getCommunicationManager();
		
		if (!permMgr.isAdministrator(this.player)) {
			commMgr.sendPlayerErrorMessage(this.player, msgMgr.getUnknownCommand());
			return;
		}
		
		String msg;
		switch (this.locationType) {
		case LOBBY:
			msg = msgMgr.getLobbyLocationSet();
			break;
		case END_LOBBY:
			msg = msgMgr.getEndLobbyLocationSet();
			break;
		case BALCONY:
			msg = msgMgr.getBalconyLocationSet();
			break;
		case A_CLAN_SPAWN:
			msg = msgMgr.getASpawnLocationSet();
			break;
		case B_CLAN_SPAWN:
			msg = msgMgr.getBSpawnLocationSet();
			break;
		default:
			Bukkit.getLogger().config("Unknow LocationType defined in SetLocationCommand.execute (" + this.locationType.toString() + ")");
			commMgr.sendPlayerErrorMessage(this.player, msgMgr.getErrorSettingLocation());
			return;
		}

		ConfigHelper.setLocation(player.getLocation(), this.locationType);

		commMgr.sendSuccessMessage(this.player, msg);
	}
}
