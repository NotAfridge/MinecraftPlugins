package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import com.ullarah.umagic.blockdata.DirectionalData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class SignWall extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        new DirectionalData().process(block);

        block.setMetadata(metaSign, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSign);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.DARK_OAK_WALL_SIGN,
                Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN);
    }

}
