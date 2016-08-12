package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Carpet extends MagicFunctions {

    public Carpet(Block block) {

        if (block.hasMetadata(metaWool)) {

            byte carpetData = block.getData();

            block.setType(Material.WOOL);
            block.setData(carpetData);

            block.removeMetadata(metaWool, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

}
