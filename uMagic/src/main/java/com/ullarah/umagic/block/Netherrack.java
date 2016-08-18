package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Netherrack extends MagicFunctions {

    public Netherrack(Block block) {

        super(false);

        Block blockUnder = block.getRelative(BlockFace.DOWN);

        if (blockUnder.getType() != Material.AIR)
            block.setType(Material.FIRE);

        block.setMetadata(metaFire, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaFire);

    }

}
