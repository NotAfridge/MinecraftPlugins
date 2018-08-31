package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;

public class Stairs extends MagicFunctions {

    public Stairs(Block block) {

        super(false);

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

}
