package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.metadata.FixedMetadataValue;

public class PackedIce extends MagicFunctions {

    public PackedIce(Block block) {

        super(false);

        block.setType(Material.WATER, true);

        Levelled data = (Levelled) block.getBlockData();
        data.setLevel(15);
		
        block.setMetadata(metaWate, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaWate);

        block.setBlockData(data);
    }

}
