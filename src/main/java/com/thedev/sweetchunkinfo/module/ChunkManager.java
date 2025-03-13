package com.thedev.sweetchunkinfo.module;

import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.expansionstacker.features.SpawnerStackingFeature;
import com.thedev.sweetchunkinfo.SweetChunkInfo;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    private final SweetChunkInfo plugin;

    private final SpawnerStackingFeature feature;

    public ChunkManager(SweetChunkInfo plugin) {
        this.plugin = plugin;

        feature = FactionsKore.get().getFeature(SpawnerStackingFeature.class);
    }

    /**
     * returns the map of all spawners inside a chunk
     */
    public Map<String, Integer> getSpawnerMap(Player player) {
        Map<String, Integer> spawnerMap = new HashMap<>();

        Chunk chunk = player.getLocation().getChunk();

        BlockState[] tileEntities = chunk.getTileEntities();

        for(BlockState tileEntity : tileEntities) {
            if(!(tileEntity instanceof CreatureSpawner)) continue;

            CreatureSpawner creatureSpawner = (CreatureSpawner) tileEntity;

            if(!feature.isStackedSpawner(creatureSpawner)) continue;

            String creatureName = creatureSpawner.getCreatureTypeName();

            int stackSize = feature.getStackedSpawner(creatureSpawner).getStackSize();

            spawnerMap.compute(creatureName, (k,v) -> (v != null) ? v + stackSize : stackSize);
        }

        return spawnerMap;
    }
}
