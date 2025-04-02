package com.thedev.sweetchunkinfo.module;

import com.thedev.sweetchunkinfo.SweetChunkInfo;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final SweetChunkInfo plugin;

    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    private BukkitTask cooldownTask;

    public CooldownManager(SweetChunkInfo plugin) {
        this.plugin = plugin;
    }

    public void addCooldown(UUID uuid, int seconds) {
        if(cooldownMap.containsKey(uuid)) return;

        cooldownMap.put(uuid, Instant.now().plusSeconds(seconds));

        startTask();
    }

    public boolean hasCooldown(UUID uuid) {
        return cooldownMap.containsKey(uuid);
    }

    public void removeCooldown(UUID uuid) {
        cooldownMap.remove(uuid);
    }

    /**
     * Gets the amount of seconds a player is in cooldown!
     */
    public long getCooldownSeconds(UUID uuid) {
        Instant instant = cooldownMap.getOrDefault(uuid, Instant.now());

        return Duration.between(Instant.now(), instant).getSeconds();
    }

    public void startTask() {
        if(cooldownTask != null &&
                (Bukkit.getScheduler().isCurrentlyRunning(cooldownTask.getTaskId())
                || Bukkit.getScheduler().isQueued(cooldownTask.getTaskId()))) return;

        cooldownTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if(cooldownMap.isEmpty()) cooldownTask.cancel();

            Iterator<Map.Entry<UUID, Instant>> iterator = cooldownMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, Instant> entry = iterator.next();

                if(!Instant.now().isAfter(entry.getValue())) continue;

                iterator.remove();
            }

        }, 20L, 20L);
    }
}
