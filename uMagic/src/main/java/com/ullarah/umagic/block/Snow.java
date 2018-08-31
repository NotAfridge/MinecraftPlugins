package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Snow extends MagicFunctions {

    public Snow(Block block) {

        super(false);

        block.setMetadata(metaSnow, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSnow);

    }

}
