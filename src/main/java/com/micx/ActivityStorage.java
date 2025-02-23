package com.micx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ActivityStorage {
    private static final String FILE_PATH = "plugins/PexActivityMonitor/danenormy.txt";

    public static void saveAll(HashMap<UUID, PlayerActivity> data) {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();

        if (!directory.exists()) {
            directory.mkdirs(); // Tworzy katalog, jeśli nie istnieje
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            for (UUID uuid : data.keySet()) {
                PlayerActivity activity = data.get(uuid);
                writer.write(uuid + "," + activity.getTotalPlayTime() + "," + activity.getTotalAfkTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void archiveAndReset(HashMap<UUID, PlayerActivity> data) {
        saveAll(data);
        data.clear();
    }
}
