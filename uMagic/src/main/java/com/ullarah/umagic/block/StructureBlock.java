package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class StructureBlock extends MagicFunctions {

    public StructureBlock(Block block) {

        super(false);

        block.setType(Material.OBSIDIAN, true);

    }

}
