package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Lamp extends MagicFunctions {

    public Lamp(Block block) {

        super(false);

        for (BlockFace face : new BlockFace[]{
                BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
                BlockFace.UP, BlockFace.DOWN
        }) {

            Block blockNext = block.getRelative(face);

            if (blockNext.getType().equals(Material.AIR)) {

                blockNext.setType(Material.REDSTONE_BLOCK, true);

                block.setMetadata(metaLamp, new FixedMetadataValue(getPlugin(), true));
                saveMetadata(block.getLocation(), metaLamp);

                blockNext.setType(Material.AIR);

            }

        }

    }

}
