package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TripwireHook;

public class Triphook extends MagicFunctions {

    public Triphook(Block block) {

        super(false);

        TripwireHook data = (TripwireHook) block.getBlockData();
        boolean activated = data.isAttached();
        boolean connected = data.isPowered();

        data.setAttached(!activated);
        if (activated) {
            data.setPowered(!connected);
        }

        block.setBlockData(data);
    }

}
