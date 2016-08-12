package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Redstone extends MagicFunctions {

    public Redstone(Block block) {

        Block blockUnder = block.getRelative(BlockFace.DOWN);
        Material blockOriginal = blockUnder.getType();

        block.setMetadata(metaLamp, new FixedMetadataValue(getPlugin(), true));
        blockUnder.setType(Material.REDSTONE_BLOCK, true);

        block.getRelative(BlockFace.DOWN).setType(blockOriginal, true);
        saveMetadata(block.getLocation(), metaLamp);

    }

}
