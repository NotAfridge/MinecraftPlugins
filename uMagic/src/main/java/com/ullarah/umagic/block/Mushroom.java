package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import com.ullarah.umagic.blockdata.MultipleFacingData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.types.MushroomBlockTexture;

import java.util.Arrays;
import java.util.List;

public class Mushroom extends BaseBlock {

    private static final MultipleFacingData multipleFacingData;

    static {
        multipleFacingData = new MultipleFacingData(true, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
    }

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();
        multipleFacingData.process(block);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.RED_MUSHROOM_BLOCK, Material.BROWN_MUSHROOM_BLOCK, Material.MUSHROOM_STEM);
    }

}
