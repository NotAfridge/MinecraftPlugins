package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Ladder {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();

        if (b.hasMetadata(f.metaLadd)) {
            b.setType(Material.WOOD);
            b.removeMetadata(f.metaLadd, MagicInit.getPlugin());
            f.removeMetadata(b.getLocation());
        }

    }

}
