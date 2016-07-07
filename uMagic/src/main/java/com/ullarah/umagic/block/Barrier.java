package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Barrier {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();

        if (b.hasMetadata(f.metaEmBr)) {
            b.setType(Material.EMERALD_BLOCK);
            b.removeMetadata(f.metaEmBr, MagicInit.getPlugin());
            f.removeMetadata(b.getLocation());
        }

    }

}
