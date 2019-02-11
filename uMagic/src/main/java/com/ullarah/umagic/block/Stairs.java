package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.List;

public class Stairs extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        org.bukkit.block.data.type.Stairs data = (org.bukkit.block.data.type.Stairs) block.getBlockData();
        BlockFace facing = data.getFacing();
        org.bukkit.block.data.type.Stairs.Shape shape = data.getShape();

        org.bukkit.block.data.type.Stairs.Shape[] shapes = org.bukkit.block.data.type.Stairs.Shape.values();

        data.setShape(shapes[(shape.ordinal() + 1) % shapes.length]);
        if (shape == org.bukkit.block.data.type.Stairs.Shape.OUTER_RIGHT) {
            data.setFacing(BlockFace.values()[(facing.ordinal() + 1) % 4]);
        }

        block.setBlockData(data);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.ACACIA_STAIRS, Material.BIRCH_STAIRS, Material.BRICK_STAIRS,
                Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.DARK_PRISMARINE_STAIRS,
                Material.JUNGLE_STAIRS, Material.NETHER_BRICK_STAIRS, Material.OAK_STAIRS,
                Material.PRISMARINE_BRICK_STAIRS, Material.PRISMARINE_STAIRS, Material.PURPUR_STAIRS,
                Material.QUARTZ_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS,
                Material.SPRUCE_STAIRS, Material.STONE_BRICK_STAIRS);
    }

}
