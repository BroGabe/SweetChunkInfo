package com.thedev.sweetchunkinfo;

import co.aikar.commands.PaperCommandManager;
import com.golfing8.kore.feature.RoamFeature;
import com.thedev.sweetchunkinfo.commands.ChunkInfoCommand;
import com.thedev.sweetchunkinfo.listeners.CommandRedirect;
import com.thedev.sweetchunkinfo.module.ChunkManager;
import com.thedev.sweetchunkinfo.module.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SweetChunkInfo extends JavaPlugin {

    private ChunkManager chunkManager;

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        chunkManager = new ChunkManager(this);
        cooldownManager = new CooldownManager(this);

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new ChunkInfoCommand(this));

        Bukkit.getPluginManager().registerEvents(new CommandRedirect(this), this);
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
