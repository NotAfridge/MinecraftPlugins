package com.ullarah.umagic.block;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class Sign {

    public void block(Block b) {

        org.bukkit.block.Sign s = (org.bukkit.block.Sign) b.getState();

        String[] l = s.getLines();
        byte d = b.getData();

        b.setData(d < 15 ? (byte) (d + 1) : (byte) 0);
        for (int n = 0; n < 3; n++) s.setLine(n, ChatColor.translateAlternateColorCodes('&', l[n]));

    }

}
