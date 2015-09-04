package com.ullarah.urocket.event;

import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.regex.Pattern;

public class AnvilCreate implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory instanceof AnvilInventory) {

            AnvilInventory anvil = (AnvilInventory) inventory;

            if (event.getRawSlot() == 0 || event.getRawSlot() == 1 || event.getRawSlot() == 2) {

                ItemStack[] anvilContents = anvil.getContents();
                ItemStack itemOne = anvilContents[0];
                ItemStack itemTwo = anvilContents[1];

                if (itemOne != null && itemTwo != null) if (itemOne.hasItemMeta() && itemTwo.hasItemMeta())
                    if (itemOne.getItemMeta().hasLore() && itemTwo.getItemMeta().hasLore())
                        if (itemOne.getItemMeta().hasDisplayName() && itemTwo.getItemMeta().hasDisplayName()) {

                            String boosterNameMatch = ChatColor.RED + "Rocket Booster";
                            String boosterLoreMatch = ChatColor.YELLOW + "Rocket Level V";

                            String boosterSlotOneName = itemOne.getItemMeta().getDisplayName();
                            String boosterSlotOneLore = itemOne.getItemMeta().getLore().get(0);

                            String boosterSlotTwoName = itemTwo.getItemMeta().getDisplayName();
                            String boosterSlotTwoLore = itemTwo.getItemMeta().getLore().get(0);

                            if (Pattern.matches(boosterNameMatch, boosterSlotOneName) &&
                                    Pattern.matches(boosterLoreMatch, boosterSlotOneLore))
                                if (Pattern.matches(boosterNameMatch, boosterSlotTwoName) &&
                                        Pattern.matches(boosterLoreMatch, boosterSlotTwoLore)) {

                                    ItemStack boosterTen = new ItemStack(Material.TNT, 1);
                                    ItemMeta boosterTenMeta = boosterTen.getItemMeta();

                                    boosterTenMeta.setDisplayName(ChatColor.RED + "Rocket Booster");
                                    boosterTenMeta.setLore(Arrays.asList(
                                            ChatColor.YELLOW + "Rocket Level X"));
                                    boosterTenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                    boosterTen.setItemMeta(boosterTenMeta);
                                    boosterTen.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

                                    if (event.getRawSlot() == 2)
                                        if (itemOne.getAmount() == 1 && itemTwo.getAmount() == 1) {

                                            ItemStack airStack = new ItemStack(Material.AIR);
                                            event.getInventory().setContents(new ItemStack[]{airStack, airStack});
                                            player.getWorld().dropItemNaturally(player.getEyeLocation(), boosterTen);
                                            player.closeInventory();

                                            TitleSubtitle.subtitle(player, 3,
                                                    "" + ChatColor.RED + ChatColor.BOLD + "Booster Level X created...");

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.BLAZE_DEATH, 1.0f, 0.3f);

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.WITHER_DEATH, 0.6f, 0.6f);

                                        }

                                }

                        }

            }

        }

    }

}
