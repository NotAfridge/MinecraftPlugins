package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Vines extends MagicFunctions {

    private static final HashMap<BlockFace, Integer> faces = new HashMap<>();

    static {
        faces.put(BlockFace.NORTH, 1);
        faces.put(BlockFace.EAST, 2);
        faces.put(BlockFace.SOUTH, 4);
        faces.put(BlockFace.WEST, 8);
        faces.put(BlockFace.UP, 16);
    }

    public Vines(Block block) {

        super(false);

        MultipleFacing data = (MultipleFacing) block.getBlockData();

        block.setMetadata(metaVine, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaVine);

        int sum = 0;
        for (BlockFace face : faces.keySet()) {
            if (data.hasFace(face))
                sum += faces.get(face);
        }

        // Increment, but don't allow 31 (aka no sides)
        sum += 1;
        sum %= 31;

        for (BlockFace face : faces.keySet()) {
            data.setFace(face, sum % 2 == 0);
            sum /= 2;
        }

        block.setBlockData(data);
    }

}
