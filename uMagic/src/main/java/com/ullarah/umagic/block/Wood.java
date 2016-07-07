package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Wood {

    public void block(Block b) {

        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
            Block blockNext = b.getRelative(face);

            if (blockNext.getType() == Material.GLASS || blockNext.getType() == Material.STAINED_GLASS) {
                b.setType(Material.LADDER);

                switch (face) {
                    case NORTH:
                        b.setData((byte) 3);
                        break;

                    case EAST:
                        b.setData((byte) 4);
                        break;

                    case SOUTH:
                        b.setData((byte) 2);
                        break;

                    case WEST:
                        b.setData((byte) 5);
                        break;
                }

                b.setMetadata(f.metaLadd, m);
                f.saveMetadata(b.getLocation(), f.metaLadd);

            }
        }

    }

}
