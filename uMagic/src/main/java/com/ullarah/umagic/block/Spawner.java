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

        Location blockLocation = block.getLocation();
        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();

        Block newSpawn = block.getWorld().getBlockAt(blockLocation);

        switch (face) {

            case NORTH:
                newSpawn = block.getWorld().getBlockAt(blockX, blockY, blockZ + 1);
                break;

            case EAST:
                newSpawn = block.getWorld().getBlockAt(blockX - 1, blockY, blockZ);
                break;

            case SOUTH:
                newSpawn = block.getWorld().getBlockAt(blockX, blockY, blockZ - 1);
                break;

            case WEST:
                newSpawn = block.getWorld().getBlockAt(blockX + 1, blockY, blockZ);
                break;

            case UP:
                newSpawn = block.getWorld().getBlockAt(blockX, blockY - 1, blockZ);
                break;

            case DOWN:
                newSpawn = block.getWorld().getBlockAt(blockX, blockY + 1, blockZ);
                break;

        }

        if (newSpawn.getType().equals(Material.AIR)) {

            block.setType(Material.AIR);
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
