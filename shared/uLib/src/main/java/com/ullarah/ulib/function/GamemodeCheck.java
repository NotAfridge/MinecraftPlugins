package com.ullarah.ulib.function;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCheck {

    /**
     * Check if a player is in the correct gamemode.
     * Can only check one gamemode of course.
     *
     * @param player   the player object
     * @param gamemode the gamemode(s) to check
     * @return whether or not the players gamemode is valid
     */
    public boolean check(Player player, GameMode... gamemode) {

        for (GameMode mode : gamemode) if (player.getGameMode().equals(mode)) return true;
        return false;

    }

}