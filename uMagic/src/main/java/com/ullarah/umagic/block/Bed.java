package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bed extends BaseBlock {

    private static final List<Material> beds = Arrays.asList(
            Material.RED_BED, Material.ORANGE_BED, Material.YELLOW_BED, Material.LIME_BED,
            Material.GREEN_BED, Material.BLUE_BED, Material.LIGHT_BLUE_BED, Material.CYAN_BED,
            Material.LIGHT_GRAY_BED, Material.GRAY_BED, Material.BLACK_BED, Material.BROWN_BED,
            Material.MAGENTA_BED, Material.PURPLE_BED, Material.PINK_BED, Material.WHITE_BED
    );

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        if (block.getType() == Material.HAY_BLOCK) {

            block.setType(Material.RED_BED);

            block.setMetadata(metaBeds, new FixedMetadataValue(getPlugin(), true));
            saveMetadata(block.getLocation(), metaBeds);
            return;
        }

        if (!block.hasMetadata(metaBeds)) {
            return;
        }

        org.bukkit.block.data.type.Bed data = (org.bukkit.block.data.type.Bed) block.getBlockData();
        BlockFace facing = data.getFacing();
        org.bukkit.block.data.type.Bed.Part part = data.getPart();

        facing = BlockFace.values()[(facing.ordinal() + 1) % 4];
        if (facing == BlockFace.NORTH) {
            part = part == org.bukkit.block.data.type.Bed.Part.HEAD ?
            org.bukkit.block.data.type.Bed.Part.FOOT : org.bukkit.block.data.type.Bed.Part.HEAD;

            if (part == org.bukkit.block.data.type.Bed.Part.FOOT) {
                Material next = beds.get((beds.indexOf(block.getType()) + 1) % beds.size());
                block.setType(next);
            }
        }

        data = (org.bukkit.block.data.type.Bed) block.getBlockData();
        data.setFacing(facing);
        data.setPart(part);

        block.setBlockData(data);
    }

    @Override
    public List<Material> getPermittedBlocks() {
        List<Material> list = new ArrayList<>(beds);
        list.add(Material.HAY_BLOCK);
        return list;
    }
}
