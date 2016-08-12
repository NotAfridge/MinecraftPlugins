package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Ladder extends MagicFunctions {

    public Ladder(Block block) {

        if (block.hasMetadata(metaLadd)) {

            block.setType(Material.WOOD);

            block.removeMetadata(metaLadd, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

}
