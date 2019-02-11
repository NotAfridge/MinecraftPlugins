package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;

import java.util.Arrays;
import java.util.List;

public class Trapdoor extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        TrapDoor data = (TrapDoor) block.getBlockData();
        data.setOpen(!data.isOpen());
        block.setBlockData(data);

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(
                Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.DARK_OAK_TRAPDOOR,
                Material.JUNGLE_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR,
                Material.IRON_TRAPDOOR);
    }

}
