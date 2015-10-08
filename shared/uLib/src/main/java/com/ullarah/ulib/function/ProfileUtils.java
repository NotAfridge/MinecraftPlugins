package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import java.util.*;

public class ProfileUtils {

    private ProfileUtils() {
    }

    @SuppressWarnings("deprecation")
    public static PlayerProfile lookup(String name) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return (player.hasPlayedBefore()) ?
                fromPlayer(player) : null;
    }

    private static PlayerProfile fromPlayer(OfflinePlayer player) {

        return new PlayerProfile(player.getUniqueId(), player.getName());

    }

    public static class PlayerProfile {

        private final UUID id;
        private final String name;

        public PlayerProfile(UUID id, String name) {

            this.id = id;
            this.name = name;

        }

        public UUID getId() {

            return id;

        }

        public String getName() {

            return name;

        }

    }
}
