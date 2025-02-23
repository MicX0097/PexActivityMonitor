package com.micx;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.UUID;

public class ActivityListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        PexActivityMonitor plugin = PexActivityMonitor.getInstance();

        plugin.getActivityData().putIfAbsent(uuid, new PlayerActivity(uuid));
        plugin.getActivityData().get(uuid).playerJoined();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        PexActivityMonitor plugin = PexActivityMonitor.getInstance();

        if (plugin.getActivityData().containsKey(uuid)) {
            plugin.getActivityData().get(uuid).playerLeft();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        markActive(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        markActive(event.getPlayer().getUniqueId());
    }

    private void markActive(UUID uuid) {
        PexActivityMonitor plugin = PexActivityMonitor.getInstance();
        if (plugin.getActivityData().containsKey(uuid)) {
            plugin.getActivityData().get(uuid).setActive();
        }
    }
}
