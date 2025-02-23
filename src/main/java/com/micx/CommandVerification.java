package com.micx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class CommandVerification implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ekipa.info")) {
            sender.sendMessage(ChatColor.RED + "Nie masz uprawnień do tej komendy!");
            return true;
        }

        HashMap<UUID, PlayerActivity> activityData = PexActivityMonitor.getInstance().getActivityData();
        sender.sendMessage(ChatColor.GOLD + "=== Aktywność Ekipy ===");

        for (UUID uuid : activityData.keySet()) {
            PlayerActivity activity = activityData.get(uuid);
            Player player = Bukkit.getPlayer(uuid);
            String playerName = (player != null) ? player.getName() : Bukkit.getOfflinePlayer(uuid).getName();

            sender.sendMessage(ChatColor.YELLOW + playerName + ":");
            sender.sendMessage(ChatColor.GREEN + "  - Czas online: " + formatTime(activity.getTotalPlayTime()));
            sender.sendMessage(ChatColor.RED + "  - Czas AFK: " + formatTime(activity.getTotalAfkTime()));
            sender.sendMessage(ChatColor.AQUA + "  - Aktualnie AFK: " + (activity.isAfk() ? "Tak" : "Nie"));
        }

        return true;
    }

    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        return String.format("%02dh %02dm", hours, minutes);
    }
}
