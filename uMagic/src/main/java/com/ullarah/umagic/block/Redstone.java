package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Redstone {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        Block u = b.getRelative(BlockFace.DOWN);
        Material o = u.getType();

        b.setMetadata(f.metaLamp, m);
        u.setType(Material.REDSTONE_BLOCK, true);
        b.getRelative(BlockFace.DOWN).setType(o, true);
        f.saveMetadata(b.getLocation(), f.metaLamp);

    }

}
