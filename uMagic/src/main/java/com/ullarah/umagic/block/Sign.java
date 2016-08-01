package com.ullarah.umagic.block;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class Sign {

    public Sign(Block block) {

        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) block.getState();

        String[] lines = sign.getLines();
        byte data = block.getData();

        block.setData(data < 15 ? (byte) (data + 1) : (byte) 0);
        for (int n = 0; n < 3; n++) sign.setLine(n, ChatColor.translateAlternateColorCodes('&', lines[n]));

    }

}
