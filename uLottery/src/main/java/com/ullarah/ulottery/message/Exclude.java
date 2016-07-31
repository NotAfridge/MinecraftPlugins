package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.LotteryMessageInit;
import com.ullarah.ulottery.function.PlayerProfile;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class Exclude extends LotteryMessageInit {

    private HashSet<UUID> set;

    public Exclude() {

        setSet(new HashSet<>());

        getSet().addAll(LotteryInit.getPlugin().getConfig().getStringList("exclude")
                .stream().map(UUID::fromString).collect(Collectors.toList()));

    }

    private HashSet<UUID> getSet() {
        return this.set;
    }

    private void setSet(HashSet<UUID> h) {
        this.set = h;
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

        if (getSet().contains(uuid)) {

            return ChatColor.RED + player
                    + ChatColor.YELLOW + " already excluded from Lottery.";

        } else {

            getSet().add(uuid);
            LotteryInit.getPlugin().getConfig().set("exclude",
                    set.stream().map(UUID::toString).collect(Collectors.toCollection(ArrayList::new)));
            LotteryInit.getPlugin().saveConfig();

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

        if (getSet().contains(uuid)) {

            getSet().remove(uuid);
            LotteryInit.getPlugin().getConfig().set("exclude",
                    set.stream().map(UUID::toString).collect(Collectors.toCollection(ArrayList::new)));
            LotteryInit.getPlugin().saveConfig();

            return ChatColor.YELLOW + "Included "
                    + ChatColor.RED + player
                    + ChatColor.YELLOW + " into Lottery";

        } else return ChatColor.RED + player
                + ChatColor.YELLOW + " already included into Lottery.";

    }

}
