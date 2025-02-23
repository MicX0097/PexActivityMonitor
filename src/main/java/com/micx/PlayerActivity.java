package com.micx;

import java.util.UUID;

public class PlayerActivity {
    private final UUID uuid;
    private long totalPlayTime;
    private long totalAfkTime;
    private long lastJoin;
    private long lastActivity;
    private boolean isAfk;

    public PlayerActivity(UUID uuid) {
        this.uuid = uuid;
        this.totalPlayTime = 0;
        this.totalAfkTime = 0;
        this.lastJoin = System.currentTimeMillis();
        this.lastActivity = lastJoin;
        this.isAfk = false;
    }

    public void playerJoined() {
        lastJoin = System.currentTimeMillis();
        lastActivity = lastJoin;
    }

    public void playerLeft() {
        totalPlayTime += (System.currentTimeMillis() - lastJoin) / 1000;
    }

    public void setActive() {
        if (isAfk) {
            addAfkTime((System.currentTimeMillis() - lastActivity) / 1000);
        }
        lastActivity = System.currentTimeMillis();
        isAfk = false;
    }

    public void setAfk() {
        isAfk = true;
        lastActivity = System.currentTimeMillis();
    }

    public void addAfkTime(long afkSeconds) {
        totalAfkTime += afkSeconds;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public long getTotalAfkTime() {
        return totalAfkTime;
    }

    public boolean isAfk() {
        return isAfk;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void saveCurrentSession(long currentTime) {
        totalPlayTime += (currentTime - lastJoin) / 1000;
        lastJoin = currentTime;
    }
}
