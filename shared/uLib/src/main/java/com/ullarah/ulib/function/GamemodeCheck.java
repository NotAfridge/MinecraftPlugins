package com.ullarah.ulib.function;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCheck {

    public static boolean check(Player player, GameMode... gamemode) {

        boolean gameModeValid = false;
        for (GameMode mode : gamemode) if (player.getGameMode().equals(mode)) gameModeValid = true;
        return gameModeValid;

    }

}