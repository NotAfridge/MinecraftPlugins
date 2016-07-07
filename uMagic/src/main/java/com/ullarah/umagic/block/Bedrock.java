package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Bedrock {

    public void block(Block b, Player p) {

        CommonString c = new CommonString();
        MagicFunctions f = new MagicFunctions();

        if (b.hasMetadata(f.metaEmBr)) {
            c.messageSend(MagicInit.getPlugin(), p, "Block converted to Barrier. Be careful!");
            b.setType(Material.BARRIER);
        }

    }

}
