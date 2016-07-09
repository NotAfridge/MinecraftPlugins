package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestInit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerDeath implements Listener {

    @EventHandler
    public void event(final PlayerDeathEvent event) throws IOException {

        if (!ChestInit.getPlugin().getConfig().getBoolean("hchest.keepondeath")) {

            Player chestPlayer = event.getEntity();

            File holdFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + "hold",
                    chestPlayer.getUniqueId().toString() + ".yml");

            if (holdFile.exists()) {

                FileConfiguration holdChest = YamlConfiguration.loadConfiguration(holdFile);

                if (holdChest.get("item") != null)
                    holdChest.getList("item").stream().filter(holdCurrentItem -> holdCurrentItem != null)
                            .forEach(holdCurrentItem -> chestPlayer.getWorld()
                                    .dropItemNaturally(chestPlayer.getEyeLocation(), (ItemStack) holdCurrentItem));

                holdChest.set("slots", 9);
                holdChest.set("item", new ArrayList<>());

                holdChest.save(holdFile);
                chestPlayer.closeInventory();

            }

        }

    }

}
