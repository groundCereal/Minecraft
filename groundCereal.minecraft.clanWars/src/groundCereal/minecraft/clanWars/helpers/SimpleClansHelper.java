package groundCereal.minecraft.clanWars.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

/**
 * Provides methods to simplify access to SimpleCalns plugin
 * @author groundCereals
 *
 */
/**
 * @author Paizinhos
 *
 */
public class SimpleClansHelper {
	
	/**
	 * Checks if a clan exists with the given clan tag
	 * @param clanTag The tag to look for
	 * @return true if a clan exists with the given tag, false otherwise
	 */
	public static Boolean clanExists(String clanTag) {
		Clan clan = SimpleClans.getInstance().getClanManager().getClan(clanTag);
		return clan != null;
	}

	/**
	 * Returns the clan the player belongs to
	 * @param player - the player to look for the clan
	 * @return
	 */
	public static Clan getPlayerClan(Player player) {
	    return SimpleClans.getInstance().getClanManager().getClanByPlayerUniqueId(player.getUniqueId());
	}
	
	/**
	 * Returns the leaders of a given clan
	 * @param clan The clan to get the leaders from
	 * @return the leaders of the clan
	 */
	public static List<Player> getLeaders(Clan clan) {
		List<ClanPlayer> leaders = clan.getLeaders();
		List<Player> players = new ArrayList<Player>();
		
		for (ClanPlayer cp : leaders) {
			players.add(cp.toPlayer());
		}
		
		return players;
	}
	
	/**
	 * Checks if the player is leader of any clan
	 * @param player the player to check
	 * @return true if the player is leader of a clan, false otherwise
	 */
	public static Boolean isClanLeader(Player player) {
		ClanPlayer clanPlayer = SimpleClans.getInstance().getClanManager().getClanPlayer(player);
		if (clanPlayer == null) {
			return false;
		}
		return clanPlayer.isLeader();
	}
	
	/**
	 * Checks if a player is leader of a given clan
	 * @param clan the clan to check
	 * @param player the player to check
	 * @return true if the player is leader of the clan, false otherwise
	 */
	public static Boolean isClanLeader(Clan clan, Player player) {
		return clan.isLeader(player);
	}
	
	
	/**
	 * Returns all the players of a clan
	 * @param clan The clan from which to get all the members
	 * @return The players belonging to the clan
	 */
	public static List<Player> getAllClanPlayers(Clan clan) {
		List<Player> players = new ArrayList<Player>();
		
		List<ClanPlayer> clanPlayers = clan.getOnlineMembers();
		for (ClanPlayer cp : clanPlayers) {
			players.add(cp.toPlayer());
		}
		
		return players;
	}
}
