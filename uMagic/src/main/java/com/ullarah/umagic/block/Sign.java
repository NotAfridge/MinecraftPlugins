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

        new RotatableData().process(block);

        block.setMetadata(metaSign, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSign);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.DARK_OAK_SIGN,
                Material.JUNGLE_SIGN, Material.OAK_SIGN, Material.SPRUCE_SIGN);
    }

}
