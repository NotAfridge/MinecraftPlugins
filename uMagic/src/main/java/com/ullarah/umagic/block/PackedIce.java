package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class PackedIce extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        block.setType(Material.WATER, true);

        Levelled data = (Levelled) block.getBlockData();
        data.setLevel(15);
		
        block.setMetadata(metaWate, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaWate);

        block.setBlockData(data);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.PACKED_ICE);
    }

}
