package com.ullarah.umagic.blockdata;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MultipleFacingData {

    private final boolean allowNone;
    private final HashMap<BlockFace, Integer> faces;

    public MultipleFacingData(boolean allowNone, BlockFace... permitted) {
        this.allowNone = allowNone;
        this.faces = new LinkedHashMap<>();

        int val = 1;
        // Add each successive face with values 1, 2, 4, 8...
        for (BlockFace face : permitted) {
            faces.put(face, val);
            val *= 2;
        }
    }

    public void process(Block block) {
        MultipleFacing data = (MultipleFacing) block.getBlockData();

        int sum = 0;
        for (BlockFace face : faces.keySet()) {
            if (data.hasFace(face))
                sum += faces.get(face);
        }

        // Increment, wrap-around 2^n - 1
        int max = (int) Math.pow(2, faces.size()) - 1;
        sum += 1;
        if (sum > max) {
            sum = allowNone ? 0 : 1;
        }

        for (BlockFace face : faces.keySet()) {
            data.setFace(face, sum % 2 == 1);
            sum /= 2;
        }

        block.setBlockData(data);
    }
}
