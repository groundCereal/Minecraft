package groundCereal.minecraft.clanWars.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattlePlayer;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;
import groundCereal.minecraft.clanWars.helpers.Timers;

public class PlayerListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (ClanWars.getInstance().getBattle().getStatus() != BattleStatus.BATTLE_ON) {
			return;
		}

		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}

		Player player = (Player) event.getEntity();
		BattlePlayer battlePlayer = ClanWars.getInstance().getBattle().getBattlePlayer(player);
		if (event.getFinalDamage() < player.getHealth()) {
			return;
		}

		for (ItemStack itemStack : player.getInventory().getContents()) {
			if (itemStack != null) {
				player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
			}
		}
		if (player.getInventory().getArmorContents() != null) {
			for (ItemStack itemStack : player.getInventory().getArmorContents()) {
				if (itemStack != null && itemStack.getType() != Material.AIR) {
					player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
				}
			}
		}

		event.setCancelled(true);
		player.resetMaxHealth();
		player.setFoodLevel(20);
		battlePlayer.setIsAlive(false);
		player.teleport(ClanWars.getInstance().getConfigManager().getEndLobbyLocation());

		Boolean challengingLost = false;
		Boolean challengedLost = false;

		Battle battle = ClanWars.getInstance().getBattle();
		synchronized (battle) {
			if (battlePlayer.getIsChallenging()) {
				battle.setChallengingClanAlive(battle.getChallengingClanAlive() - 1);
				if (battle.getChallengingClanAlive() == 0) {
					challengingLost = true;
				}
			} else {
				battle.setChallengedClanAlive(battle.getChallengedClanAlive() - 1);
				if (battle.getChallengedClanAlive() == 0) {
					challengedLost = true;
				}
			}
		}

		if (challengingLost || challengedLost) {
			battle.setStatus(BattleStatus.BATTLE_FINISHED);
			String wonClan;
			if (challengingLost) {
				wonClan = battle.getChallengedClan().getTag();
			} else {
				wonClan = battle.getChallengingClan().getTag();
			}
			ClanWars.getInstance().getCommunicationManager().sendBroadcastMessage(ClanWars.getInstance().getMessagesManager().getBattleWon(wonClan));
			battle.broadcastToClan(challengedLost, ClanWars.getInstance().getMessagesManager().getPickupItems());
			battle.broadcastToClan(challengedLost, ClanWars.getInstance().getMessagesManager().getPrizeAnnouncement());
			Timers.launchPickupTimer();
		}
	}
}
