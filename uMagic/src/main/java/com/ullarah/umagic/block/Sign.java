package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import com.ullarah.umagic.blockdata.RotatableData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Sign extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        new RotatableData(block);

        block.setMetadata(metaSign, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSign);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.SIGN);
    }

}
