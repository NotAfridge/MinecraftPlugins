package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import net.minecraft.server.v1_10_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftMagicNumbers;
import org.bukkit.metadata.FixedMetadataValue;

public class Chest {

    private static void playChestAnimation(Block c, int i) {

        Location l = c.getLocation();
        BlockPosition p = new BlockPosition(c.getX(), c.getY(), c.getZ());
        ((CraftWorld) l.getWorld()).getHandle().playBlockAction(p, CraftMagicNumbers.getBlock(c), 1, i);

    }

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        if (b.hasMetadata(f.metaChst)) {

            b.removeMetadata(f.metaChst, MagicInit.getPlugin());
            f.removeMetadata(b.getLocation());

            playChestAnimation(b, 0);

        } else {

            b.setMetadata(f.metaChst, m);
            f.saveMetadata(b.getLocation(), f.metaChst);

            playChestAnimation(b, 1);

        }

    }

}
