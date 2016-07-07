package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Sand {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        b.setMetadata(f.metaSand, m);
        f.saveMetadata(b.getLocation(), f.metaSand);

    }

}
