package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Redstone extends MagicFunctions {

    public Redstone(Block block) {

        super(false);

        block.setMetadata(metaReds, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaReds);

    }

}
