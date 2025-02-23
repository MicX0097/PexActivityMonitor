package com.micx;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CommandWeeklyVerification implements CommandExecutor {
    private static final String FILE_PATH = "plugins/PexActivityMonitor/danenormy.txt";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ekipa.info")) {
            sender.sendMessage(ChatColor.RED + "Nie masz uprawnień do tej komendy!");
            return true;
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            sender.sendMessage(ChatColor.RED + "Brak danych z ostatniego tygodnia!");
            return true;
        }

        sender.sendMessage(ChatColor.GOLD + "=== Aktywność Ekipy (Ostatni Tydzień) ===");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sender.sendMessage(ChatColor.YELLOW + line);
            }
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Wystąpił błąd podczas odczytu danych.");
            e.printStackTrace();
        }

        return true;
    }
}
