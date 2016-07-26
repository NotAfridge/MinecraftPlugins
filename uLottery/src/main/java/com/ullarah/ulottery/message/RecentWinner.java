package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RecentWinner {

    private String name;
    private Integer amount;
    private Integer vaultAmount;
    private Integer itemAmount;
    private Material itemMaterial;

    public RecentWinner() {
        this.name = "";
        this.amount = 0;
        this.vaultAmount = 5;
        this.itemAmount = 2;
        this.itemMaterial = Material.EMERALD;
    }

    public String getMessage() {
        return this.message();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer i) {
        this.amount = i;
    }

    public Integer getVaultAmount() {
        return this.vaultAmount;
    }

    public void setVaultAmount(Integer i) {
        this.vaultAmount = i;
    }

    public Integer getItemAmount() {
        return this.itemAmount;
    }

    public void setItemAmount(Integer i) {
        this.itemAmount = i;
    }

    public Material getItemMaterial() {
        return this.itemMaterial;
    }

    public void setItemMaterial(Material m) {
        this.itemMaterial = m;
    }

    private String message() {

        String sep = ChatColor.RESET + " - ";

        String heading = ChatColor.YELLOW + "  Recent Winner: " + ChatColor.RED + name + ChatColor.WHITE + " [";
        String item = " " + itemMaterial.name().replace("_", " ").toLowerCase();

        return LotteryInit.economy != null
                ? heading + ChatColor.GREEN + "$" + amount + ChatColor.WHITE + "]"
                : heading + ChatColor.GREEN + amount + item + (getAmount() > 1 ? "s" : "") + ChatColor.WHITE + "]";

    }

}
