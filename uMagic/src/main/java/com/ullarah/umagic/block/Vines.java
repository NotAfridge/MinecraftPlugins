package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import com.ullarah.umagic.blockdata.MultipleFacingData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Vines extends BaseBlock {

    private static final MultipleFacingData multipleFacingData;

    static {
        multipleFacingData = new MultipleFacingData(false, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP);
    }

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        block.setMetadata(metaVine, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaVine);

        multipleFacingData.process(block);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.VINE);
    }

}
