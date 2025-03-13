package com.thedev.sweetchunkinfo.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.thedev.sweetchunkinfo.SweetChunkInfo;
import com.thedev.sweetchunkinfo.module.CooldownManager;
import com.thedev.sweetchunkinfo.utils.FactionsUtil;
import com.thedev.sweetchunkinfo.utils.MessageManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@CommandAlias("fchunkinfo")
public class ChunkInfoCommand extends BaseCommand {

    private final SweetChunkInfo plugin;

    public ChunkInfoCommand(SweetChunkInfo plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("sweetchunkinfo.use")
    @Description("shows all spawners inside the chunk")
    public void onCommand(Player player) {
        if(!FactionsUtil.isFactionLand(player.getLocation())) {
            MessageManager.noFactionMessage(player, plugin);
            return;
        }

        CooldownManager cooldownManager = plugin.getCooldownManager();

        UUID uuid = player.getUniqueId();

        if(cooldownManager.hasCooldown(uuid)) {
            MessageManager.cooldownMessage(player, plugin);
            return;
        }

        FileConfiguration config = plugin.getConfig();

        int cooldown = config.getInt("command-cooldown");

        cooldownManager.addCooldown(uuid, cooldown);

        Map<String, Integer> spawnerMap = plugin.getChunkManager().getSpawnerMap(player);

        MessageManager.chunkInfoMessage(player, FactionsUtil.getFactionAtLand(player.getLocation()),  spawnerMap, plugin);
    }
}
