package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Lamp extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        Lightable data = (Lightable) block.getBlockData();
        data.setLit(!data.isLit());

        block.setMetadata(metaLamp, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaLamp);

        block.setBlockData(data);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.REDSTONE_LAMP);
    }

}
