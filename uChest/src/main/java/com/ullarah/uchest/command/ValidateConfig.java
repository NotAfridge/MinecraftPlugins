package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ValidateConfig {

    public void validateMaterialConfig(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        // Console only command, given its verbosity
        if (sender instanceof Player) {
            commonString.messagePermDeny(ChestInit.getPlugin(), sender);
            return;
        }

        // Add every material in the game
        Set<Material> types = new HashSet<>();
        for (Material material : Material.values()) {
            if (material.isItem() && material != Material.AIR)
                types.add(material);
        }

        // Validate the set against every material in the game
        for (ItemStack stack : ChestInit.materialMap.keySet()) {
            types.remove(stack.getType());
        }

        if (types.isEmpty()) {
            commonString.messageSend(ChestInit.getPlugin(), sender,
                    ChatColor.GREEN + "You have all possible materials in your data files!");
        } else {
            commonString.messageSend(ChestInit.getPlugin(), sender, true, new String[]{
                    ChatColor.WHITE + "You are missing the following materials from your data files:",
                    ChatColor.WHITE + StringUtils.join(types, ", ")
            });
        }

    }

}
