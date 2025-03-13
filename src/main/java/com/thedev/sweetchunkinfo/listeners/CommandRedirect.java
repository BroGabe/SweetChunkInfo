package com.thedev.sweetchunkinfo.listeners;

import com.thedev.sweetchunkinfo.SweetChunkInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandRedirect implements Listener {

    private final SweetChunkInfo plugin;

    private final boolean enabled;

    public CommandRedirect(SweetChunkInfo plugin) {
        this.plugin = plugin;

        enabled = plugin.getConfig().getBoolean("redirect-command");
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(!enabled) return;

        if(!event.getMessage().equalsIgnoreCase("/f chunk info")
        && !event.getMessage().equalsIgnoreCase("/f chunkinfo")) return;

        event.setMessage("/fchunkinfo");
    }
}
