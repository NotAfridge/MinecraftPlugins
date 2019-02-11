package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Torch extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        Directional data = (Directional) block.getBlockData();
        BlockFace facing = data.getFacing();
        int index = (facing.getOppositeFace().ordinal() + 1) % 4;
        data.setFacing(BlockFace.values()[index]);

        block.setMetadata(metaTrch, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaTrch);

        block.setBlockData(data);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.WALL_TORCH);
    }

}
