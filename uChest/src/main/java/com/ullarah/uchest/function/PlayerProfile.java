package com.ullarah.uchest.function;

import com.ullarah.uchest.ChestInit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerProfile {

    @SuppressWarnings("deprecation")
    public profile lookup(String name) {

        OfflinePlayer player = ChestInit.getPlugin().getServer().getOfflinePlayer(name);
        return (player.hasPlayedBefore()) ? fromPlayer(player) : null;

    }

    private profile fromPlayer(OfflinePlayer player) {
        return new profile(player.getUniqueId());
    }

    public class profile {

        private final UUID id;

        public profile(UUID id) {
            this.id = id;
        }

        public UUID getId() {
            return id;
        }

    }
}
