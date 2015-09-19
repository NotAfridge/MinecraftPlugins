package com.ullarah.ulib.function;

import org.bukkit.entity.Player;

public class PermissionCheck {

    public static boolean check(Player player, String... permissions) {

        boolean hasPermission = false;
        for (String permission : permissions) if (player.hasPermission(permission)) hasPermission = true;
        return hasPermission;

    }

}
