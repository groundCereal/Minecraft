package groundCereal.minecraft.clanWars;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import groundCereal.minecraft.clanWars.helpers.SimpleClansHelper;
import net.sacredlabyrinth.phaed.simpleclans.Clan;

public class PlayerSelectMenu {

	public static Boolean BuildMenu(Battle battle, Player player) {

		Clan clan;
		if (player == battle.getChallengingPlayer()) {
			clan = battle.getChallengingClan();
		} else {
			clan = battle.getChallengedClan();
		}

		return BuildClanMenu(clan, player, battle.getNbrPlayers());
	}

	private static Boolean BuildClanMenu(Clan clan, Player player, int selectNbr) {

		List<Player> players = SimpleClansHelper.getAllClanPlayers(clan);
		if (players.size() < selectNbr) {
			return false;
		}

		int lines = ((players.size() + 1) / 9);
		if ((players.size() + 1) % 9 != 0) {
			lines++;
		}

		Inventory inv = Bukkit.createInventory(null, lines * 9,
				ClanWars.getInstance().getMessagesManager().getSelectPlayer());
		inv.clear();
		

		for (int idx = 0; idx < players.size(); idx++) {
			ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

			SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
			playerheadmeta.setOwner(players.get(idx).getName());
			playerheadmeta.setDisplayName(players.get(idx).getName());
			playerhead.setItemMeta(playerheadmeta);
			inv.setItem(idx, playerhead);
		}

		ItemStack button = new ItemStack(Material.DIAMOND);
		ItemMeta dmeta = button.getItemMeta();
		dmeta.setDisplayName(ClanWars.getInstance().getMessagesManager().getFinishSelection());
		button.setItemMeta(dmeta);
		inv.setItem(lines * 9 - 1, button);

		if (ClanWars.getInstance().getConfigManager().getAllowExcessPlayers()) {
			ItemStack all = new ItemStack(Material.EMERALD);
			ItemMeta emeta = button.getItemMeta();
			emeta.setDisplayName(ClanWars.getInstance().getMessagesManager().getSelectAll());
			all.setItemMeta(emeta);
			inv.setItem(lines * 9 - 2, all);
		}

		player.openInventory(inv);

		return true;
	}
}
