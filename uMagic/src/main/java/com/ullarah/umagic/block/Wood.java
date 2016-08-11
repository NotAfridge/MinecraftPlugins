package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class Wood extends MagicFunctions {

    public Wood(Block block) {

        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
            Block blockNext = block.getRelative(face);

            if (blockNext.getType() == Material.GLASS || blockNext.getType() == Material.STAINED_GLASS) {
                block.setType(Material.LADDER);

                switch (face) {
                    case NORTH:
                        block.setData((byte) 3);
                        break;

                    case EAST:
                        block.setData((byte) 4);
                        break;

                    case SOUTH:
                        block.setData((byte) 2);
                        break;

                    case WEST:
                        block.setData((byte) 5);
                        break;
                }

                block.setMetadata(metaLadd, new FixedMetadataValue(MagicInit.getPlugin(), true));
                saveMetadata(block.getLocation(), metaLadd);

                displayParticles(block);

            }
        }

    }

}
