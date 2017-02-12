package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.HiddenLore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChestPlace implements Listener {

    @EventHandler
    public void event(BlockPlaceEvent event) {

        ChestFunctions f = new ChestFunctions();
        CommonString s = new CommonString();
        Player p = event.getPlayer();

        ItemStack inMainHand = p.getInventory().getItemInMainHand();
        ItemStack inOffHand = p.getInventory().getItemInOffHand();

        // This is to stop the graphical glitch.
        // Without this, the offhand slot will seem empty,
        // But it really isn't... Not sure why this is.
        if (f.checkContentChest(inOffHand)) {
            ItemMeta m = inOffHand.getItemMeta();
            inOffHand.setType(Material.CHEST);
            inOffHand.setItemMeta(m);
            event.setCancelled(true);
        }

        if (f.checkContentChest(inMainHand)) {

            if (event.getBlockPlaced().getState() instanceof Chest) {

                if (!f.checkBlock(p, event.getBlockPlaced())) if (!p.hasPermission("chest.bypass")) {
                    event.setCancelled(true);
                    return;
                }

                Chest c = (Chest) event.getBlockPlaced().getState();
                Inventory v = c.getInventory();

                for (BlockFace b : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
                    if (event.getBlockPlaced().getRelative(b).getType() == Material.CHEST) {
                        s.messageSend(ChestInit.getPlugin(), p,
                                ChatColor.RED + "Cannot be placed next to an existing chest!");
                        event.setCancelled(true);
                        return;
                    }
                }

                String l = inMainHand.getItemMeta().getLore().get(0).replace(
                        ChatColor.AQUA + "Chest can be named using " + ChatColor.GOLD + "/pchest [name]", "");
                UUID u = UUID.fromString(new HiddenLore().decode(l));

                File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + "chest",
                        u.toString() + ".yml");

                FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

                if (chestConfig.get("contents") != null) {

                    ArrayList<ItemStack> itemStack = chestConfig.getList("contents").stream().map(inboxCurrentItem
                            -> (ItemStack) inboxCurrentItem).collect(Collectors.toCollection(ArrayList::new));

                    v.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

                    ((CraftChest) ChestInit.getPlugin().getServer().getWorld("world")
                            .getBlockAt(event.getBlockPlaced().getLocation()).getState())
                            .getTileEntity().a("");

                }

                if (p.getGameMode() != GameMode.CREATIVE) if (!chestFile.delete()) {
                    event.setCancelled(true);
                    s.messageSend(ChestInit.getPlugin(), p, ChatColor.RED + "Chest placement error!");
                }

            }

        }

    }

}
