package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Sand extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        block.setMetadata(metaSand, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaSand);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.SAND, Material.RED_SAND, Material.GRAVEL,
                Material.BLACK_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER, Material.BROWN_CONCRETE_POWDER, Material.CYAN_CONCRETE_POWDER,
                Material.GRAY_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE_POWDER,
                Material.LIME_CONCRETE_POWDER, Material.MAGENTA_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, Material.PINK_CONCRETE_POWDER,
                Material.PURPLE_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER, Material.WHITE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER);
    }


}
