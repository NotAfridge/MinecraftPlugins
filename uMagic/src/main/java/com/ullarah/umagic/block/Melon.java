package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Melon extends MagicFunctions {

    public Melon(Block block) {

        super(false);

        block.setType(Material.CACTUS, true);

        block.setMetadata(metaCact, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaCact);

    }

}
