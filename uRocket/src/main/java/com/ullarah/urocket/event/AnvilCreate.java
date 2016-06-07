package com.ullarah.urocket.event;

import com.ullarah.urocket.function.TitleSubtitle;
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

import java.util.Collections;
import java.util.regex.Pattern;

public class AnvilCreate implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        TitleSubtitle titleSubtitle = new TitleSubtitle();

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;

        if (inventory instanceof AnvilInventory) {

            AnvilInventory anvil = (AnvilInventory) inventory;

            if (event.getRawSlot() == 0 || event.getRawSlot() == 1 || event.getRawSlot() == 2) {

                ItemStack[] anvilContents = anvil.getContents();
                ItemStack itemOne = anvilContents[0];
                ItemStack itemTwo = anvilContents[1];

                if (itemOne != null && itemTwo != null) if (itemOne.hasItemMeta() && itemTwo.hasItemMeta())
                    if (itemOne.getItemMeta().hasLore() && itemTwo.getItemMeta().hasLore())
                        if (itemOne.getItemMeta().hasDisplayName() && itemTwo.getItemMeta().hasDisplayName()) {

                            String boosterNameMatch = ChatColor.RED + "Rocket Boot Booster";
                            String boosterLoreMatch = ChatColor.YELLOW + "Rocket Level V";

                            String boosterSlotOneName = itemOne.getItemMeta().getDisplayName();
                            String boosterSlotOneLore = itemOne.getItemMeta().getLore().get(0);

                            String boosterSlotTwoName = itemTwo.getItemMeta().getDisplayName();
                            String boosterSlotTwoLore = itemTwo.getItemMeta().getLore().get(0);

                            if (Pattern.matches(boosterNameMatch, boosterSlotOneName) && Pattern.matches(boosterLoreMatch, boosterSlotOneLore))
                                if (Pattern.matches(boosterNameMatch, boosterSlotTwoName) && Pattern.matches(boosterLoreMatch, boosterSlotTwoLore)) {

                                    ItemStack boosterTen = new ItemStack(Material.TNT, 1);
                                    ItemMeta boosterTenMeta = boosterTen.getItemMeta();

                                    boosterTenMeta.setDisplayName(boosterNameMatch);
                                    boosterTenMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Rocket Level X"));
                                    boosterTenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                    boosterTen.setItemMeta(boosterTenMeta);
                                    boosterTen.addUnsafeEnchantment(Enchantment.LUCK, 1);

                                    if (event.getRawSlot() == 2)
                                        if (itemOne.getAmount() == 1 && itemTwo.getAmount() == 1) {

                                            Player player = (Player) event.getWhoClicked();

                                            ItemStack airStack = new ItemStack(Material.AIR);
                                            event.getClickedInventory().setContents(new ItemStack[]{airStack, airStack});
                                            player.getWorld().dropItemNaturally(player.getEyeLocation(), boosterTen);
                                            player.closeInventory();

                                            titleSubtitle.subtitle(player, 3, "" + ChatColor.RED + ChatColor.BOLD + "Booster Level X created...");

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.ENTITY_BLAZE_DEATH, 0.8f, 0.8f);

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.ENTITY_WITHER_DEATH, 0.5f, 0.6f);

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.ENTITY_GHAST_HURT, 0.8f, 0.8f);

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.playSound(onlinePlayer.getEyeLocation(),
                                                        Sound.ENTITY_LIGHTNING_THUNDER, 0.8f, 0.8f);

                                        }

                                }

                        }

            }

        }

    }

}
