package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class Carpet extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        if (block.hasMetadata(metaWool)) {

            String type = block.getType().name().replaceAll("CARPET", "WOOL");
            block.setType(Material.valueOf(type));

            block.removeMetadata(metaWool, getPlugin());
            removeMetadata(block.getLocation());

        }

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.BLACK_CARPET, Material.BLUE_CARPET, Material.BROWN_CARPET, Material.CYAN_CARPET,
                Material.GRAY_CARPET, Material.GREEN_CARPET, Material.LIGHT_BLUE_CARPET, Material.LIGHT_GRAY_CARPET,
                Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET,
                Material.PURPLE_CARPET, Material.RED_CARPET, Material.WHITE_CARPET, Material.YELLOW_CARPET);
    }
}
