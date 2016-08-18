package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Sand extends MagicFunctions {

    public Sand(Block block) {

        super(false);

        block.setMetadata(metaSand, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSand);

    }

}
