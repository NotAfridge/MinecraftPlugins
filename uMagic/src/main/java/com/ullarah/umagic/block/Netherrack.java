package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Netherrack extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        Block blockUnder = block.getRelative(BlockFace.DOWN);

        if (blockUnder.getType() != Material.AIR)
            block.setType(Material.FIRE);

        block.setMetadata(metaFire, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaFire);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.NETHERRACK);
    }

}
