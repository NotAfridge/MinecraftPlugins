package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Rails extends MagicFunctions {

    public Rails(Block block) {

        super(false);

        block.setMetadata(metaRail, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaRail);

    }

}
