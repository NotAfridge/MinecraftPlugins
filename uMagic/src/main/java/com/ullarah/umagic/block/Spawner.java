package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class Spawner extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();
        BlockFace face = meta.getFace();

        CreatureSpawner originalSpawner = (CreatureSpawner) block.getState();
        EntityType originalEntityType = originalSpawner.getSpawnedType();
        int originalEntityDelay = originalSpawner.getDelay();
        Block newSpawn = block.getRelative(face.getOppositeFace());
        Material destination = newSpawn.getType();
        if (destination == Material.AIR || destination == Material.CAVE_AIR) {

            block.setType(destination);
            newSpawn.setType(Material.SPAWNER);

            BlockState blockState = newSpawn.getState();

            ((CreatureSpawner) blockState).setSpawnedType(originalEntityType);
            ((CreatureSpawner) blockState).setDelay(originalEntityDelay);

            blockState.update(true);

        }

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.SPAWNER);
    }

}
