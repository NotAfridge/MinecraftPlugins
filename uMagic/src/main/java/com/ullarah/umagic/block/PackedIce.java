package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class PackedIce extends MagicFunctions {

    public PackedIce(Block block) {

        super(false);

        block.setType(Material.STATIONARY_WATER, true);

        block.setData((byte) 7);
		
        block.setMetadata(metaWate, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaWate);

    }

}
