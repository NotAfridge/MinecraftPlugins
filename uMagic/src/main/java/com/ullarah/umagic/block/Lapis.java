package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Lapis extends MagicFunctions {

    public Lapis(Block block) {

        super(false);

        block.setType(Material.STRUCTURE_VOID, true);

        block.setMetadata(metaVoid, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaVoid);

    }

}
