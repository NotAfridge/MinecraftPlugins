package com.ullarah.ulottery.message;

import com.google.common.collect.Lists;
import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class History {

    private CommonString commonString = new CommonString();
    private RecentWinner recentWinner = LotteryInit.recentWinner;

    private ArrayList<String> name;
    private ArrayList<Integer> amount;
    private Material itemMaterial;

    public History() {
        this.name = new ArrayList<>();
        this.amount = new ArrayList<>();
        this.itemMaterial = Material.EMERALD;
    }

    public void showHistory(Player player) {
        this.show(player);
    }

    public void addHistory(String name, Integer amount) {
        this.add(name, amount);
    }

    public Material getItemMaterial() {
        return itemMaterial;
    }

    public void setItemMaterial(Material m) {
        itemMaterial = m;
    }

    private void show(Player player) {

        Plugin plugin = LotteryInit.getPlugin();

        if (name.isEmpty()) commonString.messageSend(plugin, player, "No Winning History Found.");
        else {

            commonString.messageSend(plugin, player, "Previous Lottery Winners");

            List nr = Lists.reverse(name);
            List ar = Lists.reverse(amount);

            boolean isRecent = true;

            for (int i = 0; i < nr.size(); i++) {

                String wn = (String) nr.get(i);
                Integer wa = (Integer) ar.get(i);

                String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

                String win = LotteryInit.economy != null
                        ? ChatColor.GREEN + "$" + wa.toString()
                        : ChatColor.GREEN + wa.toString() + item + (wa > 1 ? "s" : "");

                String recent = "";

                if (isRecent) {

                    recent = ChatColor.DARK_PURPLE + " ["
                            + ChatColor.LIGHT_PURPLE + "Recent"
                            + ChatColor.DARK_PURPLE + "]";

                    isRecent = false;

                }

                player.sendMessage(ChatColor.YELLOW + "  " + wn + ChatColor.WHITE + " - " + win + recent);


            }

        }

    }

    private void add(String n, Integer a) {

        name.add(n);
        amount.add(a);

        if (name.size() > 10) {
            name.remove(0);
            amount.remove(0);
        }

    }

}
