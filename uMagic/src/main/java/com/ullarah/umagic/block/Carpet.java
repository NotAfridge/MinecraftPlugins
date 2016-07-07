package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Carpet {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();

        if (b.hasMetadata(f.metaWool)) {
            byte carpetData = b.getData();
            b.setType(Material.WOOL);
            b.setData(carpetData);
            b.removeMetadata(f.metaWool, MagicInit.getPlugin());
            f.removeMetadata(b.getLocation());
        }

    }

}
