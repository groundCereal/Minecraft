package groundCereal.minecraft.clanWars.helpers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import groundCereal.minecraft.clanWars.Battle;
import groundCereal.minecraft.clanWars.BattleStatus;
import groundCereal.minecraft.clanWars.ClanWars;

public class Timers {
	private static int count = -1;

	public static void launchChallengeTimer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(ClanWars.getInstance(), new Runnable() {
			@Override
			public void run() {
				Battle battle = ClanWars.getInstance().getBattle();
				synchronized (battle) {
					if (battle.getStatus() == BattleStatus.CHALLENGED) {
						battle.reset();
						ClanWars.getInstance().getCommunicationManager()
								.sendBroadcastMessage(ClanWars.getInstance().getMessagesManager().getChallengeExpired());
					}
				}
			}
		}, ClanWars.getInstance().getConfigManager().getChallengeTimeout() * 20);
	}

	public static void launchSelectionTimer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(ClanWars.getInstance(), new Runnable() {
			@Override
			public void run() {
				ClanWars plugin = ClanWars.getInstance();
				Battle battle = plugin.getBattle();
				synchronized (battle) {
					if (battle.getStatus() == BattleStatus.SELECTING_PLAYERS || battle.getStatus() == BattleStatus.SELECTED_PLAYERS) {
						battle.reset();
						plugin.getCommunicationManager().sendBroadcastMessage(plugin.getMessagesManager().getSelectionExpired());
					}
				}
			}
		}, ClanWars.getInstance().getConfigManager().getSelectionTimeout() * 20);
	}

	public static void launchLoginTimer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(ClanWars.getInstance(), new Runnable() {
			@Override
			public void run() {
				ClanWars plugin = ClanWars.getInstance();
				Battle battle = plugin.getBattle();
				synchronized (battle) {
					if (battle.getStatus() == BattleStatus.WAITING_FOR_PLAYERS) {
						battle.setStatus(BattleStatus.COUNTDOWN);
						Timers.launchCountdown();
					}
				}
			}
		}, ClanWars.getInstance().getConfigManager().getLoginTimeout() * 20);
	}

	public static void launchCountdown() {
		if (count == -1) {
			count = ClanWars.getInstance().getConfigManager().getCountdownSeconds();
		}
		int seconds = 5;
		if (count > 5) {
			seconds = count % 5;
			seconds = seconds == 0 ? 5 : seconds;
		} else if (count == 5) {
			seconds = 2;
		} else if (count > 0) {
			seconds = 1;
		} else {
			count = -1;
			ClanWars.getInstance().getBattle().startBattle();
			return;
		}

		String message = ClanWars.getInstance().getMessagesManager().getBattleStartsIn(count);
		ClanWars.getInstance().getBattle().broadcastAll(message);

		count -= seconds;

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(ClanWars.getInstance(), new Runnable() {
			@Override
			public void run() {
				Timers.launchCountdown();
			}
		}, seconds * 20);
	}

	public static void launchPickupTimer() {
		if (count == -1) {
			count = ClanWars.getInstance().getConfigManager().getPickupTimeout();
		}

		int seconds = 0;
		if (count > 15) {
			seconds = count - 15;
		} else if (count > 5) {
			seconds = count % 5;
			seconds = seconds == 0 ? 5 : seconds;
		} else if (count == 5) {
			seconds = 2;
		} else if (count > 0) {
			seconds = 1;
		} else {
			count = -1;
			ClanWars.getInstance().getBattle().endBattle();
			return;
		}

		if (count <= 15) {
			String message = ClanWars.getInstance().getMessagesManager().getPickupCountdown(count);
			ClanWars.getInstance().getBattle().broadcastAll(message);
		}

		count -= seconds;

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(ClanWars.getInstance(), new Runnable() {
			@Override
			public void run() {
				Timers.launchPickupTimer();
			}
		}, seconds * 20);
	}
}
