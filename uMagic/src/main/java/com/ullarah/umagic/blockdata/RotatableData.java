package com.ullarah.umagic.blockdata;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

import java.util.Arrays;
import java.util.List;

public class RotatableData {

    private static final List<BlockFace> directions = Arrays.asList(
            BlockFace.NORTH, BlockFace.NORTH_NORTH_EAST, BlockFace.NORTH_EAST, BlockFace.EAST_NORTH_EAST,
            BlockFace.EAST, BlockFace.EAST_SOUTH_EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_SOUTH_EAST,
            BlockFace.SOUTH, BlockFace.SOUTH_SOUTH_WEST, BlockFace.SOUTH_WEST, BlockFace.WEST_SOUTH_WEST,
            BlockFace.WEST, BlockFace.WEST_NORTH_WEST, BlockFace.NORTH_WEST, BlockFace.NORTH_NORTH_WEST
    );

    public void process(Block block) {
        Rotatable data = (Rotatable) block.getBlockData();
        BlockFace facing = data.getRotation();

        int index = (directions.indexOf(facing) + 1) % directions.size();
        data.setRotation(directions.get(index));

        block.setBlockData(data);
    }
}
