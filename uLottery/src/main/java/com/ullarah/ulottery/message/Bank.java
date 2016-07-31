package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.LotteryMessageInit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Bank extends LotteryMessageInit {

    private Integer amount;
    private Material itemMaterial;

    public Bank() {
        setAmount(0);
        setItemMaterial(Material.EMERALD);
    }

    public String getMessage() {
        return message();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int i) {
        amount = i;
    }

    public Material getItemMaterial() {
        return itemMaterial;
    }

    @SuppressWarnings("SameParameterValue")
    private void setItemMaterial(Material m) {
        itemMaterial = m;
    }

    public void setItemMaterial(String m) {
        itemMaterial = Material.getMaterial(m);
    }

    private String message() {

        String heading = ChatColor.YELLOW + "  Lottery Bank Balance: " + ChatColor.GREEN;
        String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

        if (amount > 0) {

            return LotteryInit.getEconomy() != null
                    ? heading + "$" + getAmount()
                    : heading + getAmount() + item + (getAmount() > 1 ? "s" : "");

        } else {

            return LotteryInit.getEconomy() != null
                    ? heading + "$" + "0"
                    : heading + "0" + item + "s";

        }

    }

    public String modify(String amount) {

        if (amount.matches("-?\\d+")) {

            setAmount(Integer.valueOf(amount) + getAmount());

            if (getAmount() <= 0) setAmount(0);
            if (getAmount() >= Integer.MAX_VALUE) setAmount(Integer.MAX_VALUE);

            return message().replaceFirst("  ", "");

        } else return ChatColor.RED + "Not a valid number!";

    }

    public void reset() {

        setAmount(0);

    }

}
