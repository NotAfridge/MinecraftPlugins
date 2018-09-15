package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.types.MushroomBlockTexture;

import java.util.Arrays;
import java.util.List;

public class Mushroom extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        org.bukkit.material.Mushroom mushroom = (org.bukkit.material.Mushroom) block;
        MushroomBlockTexture[] values = MushroomBlockTexture.values();
        int next = (mushroom.getBlockTexture().ordinal() + 1) % values.length;
        mushroom.setBlockTexture(values[next]);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.RED_MUSHROOM_BLOCK, Material.BROWN_MUSHROOM_BLOCK);
    }

}
