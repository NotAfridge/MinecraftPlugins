package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;

import java.util.Arrays;
import java.util.List;

public class Button extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        Powerable data = (Powerable) block.getBlockData();
        data.setPowered(!data.isPowered());
        block.setBlockData(data);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.STONE_BUTTON,
                Material.ACACIA_BUTTON, Material.BIRCH_BUTTON, Material.DARK_OAK_BUTTON,
                Material.JUNGLE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON);
    }

}
