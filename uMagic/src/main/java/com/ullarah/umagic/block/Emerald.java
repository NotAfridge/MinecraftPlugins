package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Emerald {

    public void block(Block b, Player p) {

        CommonString c = new CommonString();
        MagicFunctions f = new MagicFunctions();
        FixedMetadataValue m = new FixedMetadataValue(MagicInit.getPlugin(), true);

        c.messageSend(MagicInit.getPlugin(), p, "Block converted to Bedrock. Be careful!");
        b.setType(Material.BEDROCK);
        b.setMetadata(f.metaEmBr, m);
        f.saveMetadata(b.getLocation(), f.metaEmBr);

    }

}
