package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.blockdata.DirectionalData;
import com.ullarah.umagic.blockdata.RotatableData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class SignWall extends MagicFunctions {

    public SignWall(Block block) {

        super(false);

        new DirectionalData(block);

        block.setMetadata(metaSign, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSign);
    }

}
