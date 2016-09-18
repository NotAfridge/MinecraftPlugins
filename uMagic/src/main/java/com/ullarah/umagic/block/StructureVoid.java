package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class StructureVoid extends MagicFunctions {

    public StructureVoid(Block block) {

        super(false);

        block.setType(Material.LAPIS_BLOCK, true);

        block.removeMetadata(metaVoid, getPlugin());
        removeMetadata(block.getLocation());

    }

}
