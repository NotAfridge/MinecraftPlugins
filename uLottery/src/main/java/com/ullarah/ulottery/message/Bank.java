package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.LotteryMessageInit;
import com.ullarah.ulottery.function.RemoveInventoryItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        LotteryInit.getPlugin().getConfig().set("current.bank", getAmount());
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

        if (getAmount() > 0) {

            if (LotteryInit.getEconomy() != null) {
                return getAmount() > 1
                        ? heading + getAmount() + " " + LotteryInit.getEconomy().currencyNamePlural()
                        : heading + getAmount() + " " + LotteryInit.getEconomy().currencyNameSingular();
            } else return heading + getAmount() + item + (getAmount() > 1 ? "s" : "");

        } else {

            if (LotteryInit.getEconomy() != null) return heading + "0 "
                    + LotteryInit.getEconomy().currencyNameSingular();
            else return heading + "0" + item + "s";

        }

    }

    public String modify(Player player, String amount, boolean donation) {

        int intAmount;

        try {
            intAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            return ChatColor.RED + "Not a valid amount!";
        }

        if (donation) {

            if (LotteryInit.getEconomy() != null) {

                String currency = " ";
                Double balance = LotteryInit.getEconomy().getBalance(player);

                if (intAmount >= balance.intValue()) {

                    currency += intAmount > 1
                            ? LotteryInit.getEconomy().currencyNamePlural()
                            : LotteryInit.getEconomy().currencyNameSingular();

                    return ChatColor.RED + "You cannot afford to donate "
                            + ChatColor.YELLOW + amount + currency + ChatColor.RED + "!";

                } else LotteryInit.getEconomy().withdrawPlayer(player, intAmount);

            } else {

                if (player.getInventory().contains(getItemMaterial(), intAmount)) {

                    new RemoveInventoryItems().remove(player, getItemMaterial(), intAmount);

                } else {

                    return ChatColor.RED + "You cannot afford to donate "
                            + ChatColor.YELLOW + amount
                            + " " + getItemMaterial().name().replace("_", " ").toLowerCase()
                            + (intAmount > 1 ? "s" : "")
                            + ChatColor.RED + "!";

                }

            }

        }

        if (amount.matches(donation ? "\\d+" : "-?\\d+")) {

            setAmount(intAmount + getAmount());

            if (getAmount() <= 0) setAmount(0);
            if (getAmount() >= Integer.MAX_VALUE) setAmount(Integer.MAX_VALUE);

            return message().replaceFirst("  ", "");

        } else return ChatColor.RED + "Not a valid amount!";

    }

    public void reset() {

        setAmount(0);

    }

}
