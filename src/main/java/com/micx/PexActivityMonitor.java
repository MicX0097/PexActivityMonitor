package com.micx;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.UUID;

public class PexActivityMonitor extends JavaPlugin {
    private static PexActivityMonitor instance;
    private final HashMap<UUID, PlayerActivity> activityData = new HashMap<>();
    private static final long AFK_THRESHOLD = 5 * 60 * 1000; // 5 minut w milisekundach

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("✅ PexActivityMonitor został włączony! Wersja: " + getDescription().getVersion());
        getServer().getPluginManager().registerEvents(new ActivityListener(), this);

        if (getCommand("weryfikacja") != null) {
            getCommand("weryfikacja").setExecutor(new CommandVerification());
        }

        if (getCommand("weryfikacjaweek") != null) {
            getCommand("weryfikacjaweek").setExecutor(new CommandWeeklyVerification());
        }

        startWeeklyResetTask();
        startAfkCheckTask();
    }

    @Override
    public void onDisable() {
        long currentTime = System.currentTimeMillis();
        for (UUID uuid : activityData.keySet()) {
            PlayerActivity activity = activityData.get(uuid);
            activity.saveCurrentSession(currentTime);
        }
        ActivityStorage.saveAll(activityData);
        getLogger().info("❗PexActivityMonitor został wyłączony!");
    }

    public static PexActivityMonitor getInstance() {
        return instance;
    }

    public HashMap<UUID, PlayerActivity> getActivityData() {
        return activityData;
    }

    private void startWeeklyResetTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ActivityStorage.archiveAndReset(activityData);
            }
        }.runTaskTimer(this, 0, 7 * 24 * 60 * 60 * 20L);
    }

    private void startAfkCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                for (UUID uuid : activityData.keySet()) {
                    PlayerActivity activity = activityData.get(uuid);
                    if (!activity.isAfk() && (currentTime - activity.getLastActivity()) > AFK_THRESHOLD) {
                        activity.setAfk();
                    }
                }
            }
        }.runTaskTimer(this, 0, 60 * 20L);
    }
}
