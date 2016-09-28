package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Cactus extends MagicFunctions {

    public Cactus(Block block) {

        super(false);

        block.setType(Material.MELON_BLOCK, true);

        block.removeMetadata(metaCact, getPlugin());
        removeMetadata(block.getLocation());

    }

}
