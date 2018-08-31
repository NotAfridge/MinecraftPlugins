package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;

public class Trapdoor extends MagicFunctions {

    public Trapdoor(Block block) {

        super(false);

        TrapDoor data = (TrapDoor) block.getBlockData();
        data.setOpen(!data.isOpen());
        block.setBlockData(data);

    }

}
