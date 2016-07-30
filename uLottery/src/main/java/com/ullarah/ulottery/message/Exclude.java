package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class Exclude {

    private Plugin plugin = LotteryInit.getPlugin();

    private HashSet<UUID> set;

    public Exclude() {

        this.set = new HashSet<>();

        set.addAll(plugin.getConfig().getStringList("exclude")
                .stream().map(UUID::fromString).collect(Collectors.toList()));

    }

    public String addPlayer(String player) {
        return this.add(player);
    }

    public String removePlayer(String player) {
        return this.remove(player);
    }

    public boolean hasPlayer(String player) {

        try {
            return set.contains(new PlayerProfile().lookup(player).getId());
        } catch (Exception e) {
            return false;
        }

    }

    private String add(String player) {

        UUID uuid;

        try {
            uuid = new PlayerProfile().lookup(player).getId();
        } catch (Exception e) {
            return ChatColor.YELLOW + "Player cannot be resolved.";
        }

        if (set.contains(uuid)) {

            return ChatColor.RED + player
                    + ChatColor.YELLOW + " already excluded from Lottery.";

        } else {

            set.add(uuid);
            plugin.getConfig().set("exclude",
                    set.stream().map(UUID::toString).collect(Collectors.toCollection(ArrayList::new)));
            plugin.saveConfig();

            return ChatColor.YELLOW + "Excluded "
                    + ChatColor.RED + player
                    + ChatColor.YELLOW + " from Lottery.";

        }

    }

    private String remove(String player) {

        UUID uuid;

        try {
            uuid = new PlayerProfile().lookup(player).getId();
        } catch (Exception e) {
            return ChatColor.YELLOW + "Player cannot be resolved.";
        }

        if (set.contains(uuid)) {

            set.remove(uuid);
            plugin.getConfig().set("exclude",
                    set.stream().map(UUID::toString).collect(Collectors.toCollection(ArrayList::new)));
            plugin.saveConfig();

            return ChatColor.YELLOW + "Included "
                    + ChatColor.RED + player
                    + ChatColor.YELLOW + " into Lottery";

        } else return ChatColor.RED + player
                + ChatColor.YELLOW + " already included into Lottery.";

    }

}
