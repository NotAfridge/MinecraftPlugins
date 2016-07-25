package com.ullarah.uchest.function;

import org.bukkit.ChatColor;

public class HiddenLore {

    public String encode(String s) {

        s = s.replaceAll("-", "");
        String e = "";
        for (char c : s.toCharArray()) e += ChatColor.COLOR_CHAR + "" + c;
        return e;

    }

    public String decode(String s) {

        s = s.replaceAll(ChatColor.COLOR_CHAR + "", "");
        return s.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");

    }

}
