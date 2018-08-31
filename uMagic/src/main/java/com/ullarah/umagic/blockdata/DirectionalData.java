package com.ullarah.umagic.blockdata;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

import java.util.Arrays;
import java.util.List;

public class DirectionalData {

    private static final List<BlockFace> directions = Arrays.asList(
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
    );

    public DirectionalData(Block block) {
        Directional data = (Directional) block.getBlockData();
        BlockFace facing = data.getFacing();

        int index = (directions.indexOf(facing) + 1) % directions.size();
        data.setFacing(directions.get(index));

        block.setBlockData(data);
    }
}
