package com.ullarah.ulib.function;

import org.bukkit.entity.Player;

public class PermissionCheck {

    /**
     * Check if a player has a required given permission
     *
     * @param player      the player object
     * @param permission the permission to check
     * @return whether or not the player has the permission(s)
     */
    public static boolean check(Player player, String permission) {

        return player.hasPermission(permission);

    }

    /**
     * Check if a player has all required given permissions
     *
     * @param player      the player object
     * @param permissions the permission(s) to check
     * @return whether or not the player has the permission(s)
     */
    public static boolean check(Player player, String... permissions) {

        boolean isValid = false;
        for (String permission : permissions) isValid = player.hasPermission(permission);
        return isValid;

    }

}
