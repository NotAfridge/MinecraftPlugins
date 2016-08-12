package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Barrier extends MagicFunctions {

    public Barrier(Block block) {

        if (block.hasMetadata(metaEmBr)) {

            block.setType(Material.EMERALD_BLOCK);
            block.removeMetadata(metaEmBr, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

}
