package com.ullarah.ulib.function;

import org.bukkit.entity.Player;

public class PermissionCheck {

    /**
     * Check if a player has the required permissions
     *
     * @param player      the player object
     * @param permissions the permission(s) to check
     * @return whether or not the player has the permission(s)
     */
    public static boolean check(Player player, String... permissions) {

        for (String permission : permissions) if (!player.hasPermission(permission)) return false;
        return true;

    }

}
