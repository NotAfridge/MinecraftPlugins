package com.ullarah.uchest.function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerProfile {

    @SuppressWarnings("deprecation")
    public profile lookup(String name) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return (player.hasPlayedBefore()) ?
                fromPlayer(player) : null;
    }

    private profile fromPlayer(OfflinePlayer player) {
        return new profile(player.getUniqueId(), player.getName());
    }

    public class profile {

        private final UUID id;
        private final String name;

        public profile(UUID id, String name) {
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
