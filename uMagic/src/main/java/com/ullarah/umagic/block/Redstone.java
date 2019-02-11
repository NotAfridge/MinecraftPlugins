package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Redstone extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        block.setMetadata(metaReds, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaReds);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.REDSTONE);
    }


}
