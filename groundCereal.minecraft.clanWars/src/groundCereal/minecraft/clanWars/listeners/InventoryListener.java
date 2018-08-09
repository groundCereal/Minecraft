package groundCereal.minecraft.clanWars.listeners;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattlePlayer;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.helpers.Timers;
import groundCereal.minecraft.clanWars.managers.CommunicationManager;
import groundCereal.minecraft.clanWars.managers.MessagesManager;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
		try {
			Inventory inv = event.getClickedInventory();
			if (!inv.getName().equals(ClanWars.getInstance().getMessagesManager().getSelectPlayer())) {
				return;
			}
			
			event.setCancelled(true);

			MessagesManager msgMgr = ClanWars.getInstance().getMessagesManager();

			ItemStack item = event.getCurrentItem();
			if (!item.hasItemMeta()) {
				return;
			}
			int slot = event.getSlot();

			Player sender = (Player) event.getWhoClicked();
			Boolean isChallenging = this.getIsChallenging(sender);
			List<BattlePlayer> battlePlayers = this.getBattlePlayersList(isChallenging);

			Battle battle = ClanWars.getInstance().getBattle();

			if (item.getType() == Material.SKULL_ITEM) {
				this.selectPlayer(inv, item, slot, sender, battlePlayers, isChallenging);
			} else if (item.getType() == Material.IRON_SWORD) {
				inv.clear(slot);
				ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
				String owner = item.getItemMeta().getDisplayName();

				SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
				playerheadmeta.setOwner(owner);
				playerheadmeta.setDisplayName(owner);
				playerhead.setItemMeta(playerheadmeta);
				inv.setItem(slot, playerhead);

				Player player = Bukkit.getServer().getPlayer(owner);
				removePlayerFromList(battlePlayers, player);
			} else if (item.getType() == Material.DIAMOND) {
				if (battlePlayers.size() >= battle.getNbrPlayers()) {
					synchronized (battle) {
						if (battle.getStatus() == BattleStatus.SELECTING_PLAYERS) {
							battle.setStatus(BattleStatus.SELECTED_PLAYERS);
						} else if (battle.getStatus() == BattleStatus.SELECTED_PLAYERS) {
							battle.setStatus(BattleStatus.WAITING_FOR_PLAYERS);
						} else {
							ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(sender,
									msgMgr.getChallengeExpired());
							sender.closeInventory();
							return;
						}
					}

					ClanWars.getInstance().getCommunicationManager().sendSuccessMessage(sender,
							msgMgr.getPlayersCanNowEnter());

					if (battle.getStatus() == BattleStatus.WAITING_FOR_PLAYERS) {
						this.sendPlayersLoginMessage();
						Timers.launchLoginTimer();
					}
					sender.closeInventory();

				} else {
					ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(sender,
							msgMgr.getSelectMorePlayers(battle.getNbrPlayers() - battlePlayers.size()));
				}
			} else if (item.getType() == Material.EMERALD) {
				HashMap<Integer, ? extends ItemStack> skulls = inv.all(Material.SKULL_ITEM);
				for (Integer skullSlot : skulls.keySet()) {
					this.selectPlayer(inv, skulls.get(skullSlot), skullSlot, sender, battlePlayers, isChallenging);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void selectPlayer(Inventory inv, ItemStack item, int slot, Player sender,
			List<BattlePlayer> battlePlayers, Boolean isChallenging) {
		Battle battle = ClanWars.getInstance().getBattle();

		inv.clear(slot);
		String owner = item.getItemMeta().getDisplayName();
		Player player = Bukkit.getServer().getPlayer(owner);
		if (player != null) {
			if (!ClanWars.getInstance().getConfigManager().getAllowExcessPlayers()
					&& battlePlayers.size() == battle.getNbrPlayers()) {
				ClanWars.getInstance().getCommunicationManager().sendPlayerErrorMessage(sender,
						ClanWars.getInstance().getMessagesManager().getPlayersListFull());
				return;
			}

			battlePlayers.add(new BattlePlayer(player, isChallenging));

			ItemStack swordItem = new ItemStack(Material.IRON_SWORD, 1, (short) 0);
			ItemMeta im = swordItem.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.setDisplayName(owner);
			swordItem.setItemMeta(im);
			inv.setItem(slot, swordItem);
		} else { // player is not online
			ItemStack coalItem = new ItemStack(Material.COAL, 1, (short) 0);
			ItemMeta im = coalItem.getItemMeta();
			im.setDisplayName(ClanWars.getInstance().getMessagesManager().getPlayerOffline());
			coalItem.setItemMeta(im);
			inv.setItem(slot, coalItem);
		}
	}

	private void sendPlayersLoginMessage() {
		Battle battle = ClanWars.getInstance().getBattle();
		String message = ClanWars.getInstance().getMessagesManager().getPlayerInvitation();
		String command = "/" + ClanWars.getInstance().getConfigManager().getMainCommand() + " "
				+ ClanWars.getInstance().getConfigManager().getLoginCommand();
		CommunicationManager commMgr = ClanWars.getInstance().getCommunicationManager();

		for (BattlePlayer battlePlayer : battle.getChallengingClanPlayers()) {
			commMgr.sendClickableMessage(battlePlayer.getPlayer(), message, command, true);
			if (battle.getChallengingClanPlayers().size() > battle.getNbrPlayers()) {
				commMgr.sendSuccessMessage(battlePlayer.getPlayer(),
						ClanWars.getInstance().getMessagesManager().getLimitedPlaces());
			}
		}
		for (BattlePlayer battlePlayer : battle.getChallengedClanPlayers()) {
			commMgr.sendClickableMessage(battlePlayer.getPlayer(), message, command, true);
			if (battle.getChallengedClanPlayers().size() > battle.getNbrPlayers()) {
				commMgr.sendSuccessMessage(battlePlayer.getPlayer(),
						ClanWars.getInstance().getMessagesManager().getLimitedPlaces());
			}
		}
	}

	private void removePlayerFromList(List<BattlePlayer> battlePlayers, Player player) {
		for (BattlePlayer bp : battlePlayers) {
			if (bp.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				battlePlayers.remove(bp);
				return;
			}
		}
	}

	private List<BattlePlayer> getBattlePlayersList(Boolean isChallenging) {
		Battle battle = ClanWars.getInstance().getBattle();
		if (isChallenging) {
			return battle.getChallengingClanPlayers();
		} else {
			return battle.getChallengedClanPlayers();
		}
	}
	
	private Boolean getIsChallenging(Player player) {
		Battle battle = ClanWars.getInstance().getBattle();
		if (player.getUniqueId().equals(battle.getChallengingPlayer().getUniqueId())) {
			return true;
		} else {
			return false;
		}
	}
}
