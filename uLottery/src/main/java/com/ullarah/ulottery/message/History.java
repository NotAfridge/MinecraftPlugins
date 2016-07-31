package com.ullarah.ulottery.message;

import com.google.common.collect.Lists;
import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class History extends Bank {

    private ArrayList<String> names;
    private ArrayList<Integer> amounts;

    public History() {
        setNameArray(new ArrayList<>());
        setAmountArray(new ArrayList<>());
    }

    public ArrayList<String> getNameArray() {
        return names;
    }

    private void setNameArray(ArrayList<String> a) {
        this.names = a;
    }

    private ArrayList<Integer> getAmountArray() {
        return amounts;
    }

    private void setAmountArray(ArrayList<Integer> a) {
        this.amounts = a;
    }

    public void showHistory(Player player) {
        this.show(player);
    }

    public void addHistory(String name, Integer amount) {
        this.add(name, amount);
    }

    private void show(Player player) {

        CommonString commonString = new CommonString();

        if (getNameArray().isEmpty())
            commonString.messageSend(LotteryInit.getPlugin(), player, "No Winning History Found.");
        else {

            commonString.messageSend(LotteryInit.getPlugin(), player, "Previous Lottery Winners");

            List nr = Lists.reverse(getNameArray());
            List ar = Lists.reverse(getAmountArray());

            boolean isRecent = true;

            for (int i = 0; i < nr.size(); i++) {

                String wn = (String) nr.get(i);
                Integer wa = (Integer) ar.get(i);

                String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

                String win = LotteryInit.getEconomy() != null
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

        getNameArray().add(n);
        getAmountArray().add(a);

        if (getNameArray().size() > 10) {
            getNameArray().remove(0);
            getAmountArray().remove(0);
        }

    }

}
