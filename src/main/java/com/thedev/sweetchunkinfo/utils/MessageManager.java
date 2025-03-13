package com.thedev.sweetchunkinfo.utils;

import com.thedev.sweetchunkinfo.SweetChunkInfo;
import com.thedev.sweetchunkinfo.module.CooldownManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageManager {

    // All methods here are called inside ChunkInfoCommand class.

    /**
     * Sends the message to the player for the chunk information.
     * This method is called in ChunkInfoCommand class.
     * @param player The player to send the information to.
     * @param faction The faction land the player is standing inside.
     * @param spawnerMap The map of spawners with their corresponding stack sizes. [Obtained from ChunkManager]
     * @param plugin the plugin instance used to fetch the configuration file.
     */
    public static void chunkInfoMessage(Player player, String faction, Map<String, Integer> spawnerMap, SweetChunkInfo plugin) {
        FileConfiguration config = plugin.getConfig();

        if(spawnerMap.isEmpty()) {
            player.sendMessage(ColorUtil.color(config.getString("messages.no-spawners")));
            return;
        }

        List<String> configList = config.getStringList("message");

        if(!configList.contains("%spawnerlist%")) return;

        int index = configList.indexOf("%spawnerlist%");

        List<String> spawnerLoreList = new ArrayList<>();

        for(Map.Entry<String,Integer> entry : spawnerMap.entrySet()) {
            String spawnerString = config.getString("spawner-lore")
                    .replace("%spawner%", entry.getKey())
                    .replace("%amount%", String.valueOf(entry.getValue()));

            spawnerLoreList.add(spawnerString);
        }

        configList.remove(index);
        configList.addAll(index, spawnerLoreList);

        List<String> chunkInfoMessages = new ArrayList<>();

        configList.forEach(string -> chunkInfoMessages.add(string.replace("%faction%", faction)));

        chunkInfoMessages.forEach(s -> player.sendMessage(ColorUtil.color(s)));
    }

    /**
     * Simply sends the player the configurable message if the land they are
     * standing in is not faction land.
     * @param player
     * @param plugin
     */
    public static void noFactionMessage(Player player, SweetChunkInfo plugin) {
        FileConfiguration config = plugin.getConfig();

        player.sendMessage(ColorUtil.color(config.getString("messages.not-faction")));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10, 6);
    }

    /**
     * Sends the player a configurable message if they are on an active cooldown.
     * @param player
     * @param plugin
     */
    public static void cooldownMessage(Player player, SweetChunkInfo plugin) {
        FileConfiguration config = plugin.getConfig();
        CooldownManager cooldownManager = plugin.getCooldownManager();

        player.sendMessage(ColorUtil.color(config.getString("messages.cooldown-msg")
                .replace("%seconds%", String.valueOf(cooldownManager.getCooldownSeconds(player.getUniqueId())))));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10, 6);
    }
}
