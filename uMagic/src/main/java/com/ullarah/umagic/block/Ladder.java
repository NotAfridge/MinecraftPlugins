package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class Ladder extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        if (block.hasMetadata(metaLadd)) {

            block.setType(Material.OAK_PLANKS);

            block.removeMetadata(metaLadd, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.LADDER);
    }

}
