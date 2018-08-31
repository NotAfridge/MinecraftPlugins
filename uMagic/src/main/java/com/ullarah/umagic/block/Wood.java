package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Wood extends MagicFunctions {

    private static final List<Material> backingBlocks = Arrays.asList(
            Material.BLACK_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.CYAN_STAINED_GLASS,
            Material.GRAY_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS,
            Material.LIME_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.PINK_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS,
            Material.GLASS
    );

    public Wood(Block block) {

        super(false);

        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
            Block blockNext = block.getRelative(face);

            if (backingBlocks.contains(blockNext.getType())) {
                block.setType(Material.LADDER);

                org.bukkit.block.data.type.Ladder data = (org.bukkit.block.data.type.Ladder) block.getBlockData();
                data.setFacing(face.getOppositeFace());

                block.setMetadata(metaLadd, new FixedMetadataValue(getPlugin(), true));
                saveMetadata(block.getLocation(), metaLadd);

                block.setBlockData(data);
                return;

            }
        }

    }

}
