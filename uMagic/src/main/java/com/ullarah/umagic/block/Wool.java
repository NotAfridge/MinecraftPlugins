package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Wool extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        String type = block.getType().name().replaceAll("WOOL", "CARPET");
        block.setType(Material.valueOf(type));

        block.setMetadata(metaWool, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaWool);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL,
                Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL,
                Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL,
                Material.PURPLE_WOOL, Material.RED_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL);
    }

}
