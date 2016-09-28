package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Obsidian extends MagicFunctions {

    public Obsidian(Block block) {

        super(false);

        block.setType(Material.STRUCTURE_BLOCK, true);

    }

}
