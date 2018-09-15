package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TripwireHook;

import java.util.Arrays;
import java.util.List;

public class Triphook extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        TripwireHook data = (TripwireHook) block.getBlockData();
        boolean activated = data.isAttached();
        boolean connected = data.isPowered();

        data.setAttached(!activated);
        if (activated) {
            data.setPowered(!connected);
        }

        block.setBlockData(data);
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.TRIPWIRE_HOOK);
    }

}
