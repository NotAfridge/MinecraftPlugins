package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Carpet extends MagicFunctions {

    public Carpet(Block block) {

        super(false);

        if (block.hasMetadata(metaWool)) {

            String type = block.getType().name().replaceAll("CARPET", "WOOL");
            block.setType(Material.valueOf(type));

            block.removeMetadata(metaWool, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

}
