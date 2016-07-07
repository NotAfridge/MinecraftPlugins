package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Wool {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        byte woolData = b.getData();
        b.setType(Material.CARPET);
        b.setData(woolData);
        b.setMetadata(f.metaWool, m);
        f.saveMetadata(b.getLocation(), f.metaWool);

    }

}
