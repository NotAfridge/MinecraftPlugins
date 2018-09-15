package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Ice extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        block.setType(Material.FROSTED_ICE, true);

        Ageable data = (Ageable) block.getBlockData();
        data.setAge(data.getMaximumAge());
        block.setBlockData(data);

        block.setMetadata(metaCice, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaCice);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.ICE);
    }

}
