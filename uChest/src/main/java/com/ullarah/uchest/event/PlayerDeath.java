package com.ullarah.uchest.event;

import org.bukkit.Location;
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

import static com.ullarah.uchest.ChestInit.getPlugin;

public class PlayerDeath implements Listener {

    @EventHandler
    public void event(final PlayerDeathEvent event) throws IOException {

        Player chestPlayer = event.getEntity();
        Location chestPlayerLocation = chestPlayer.getEyeLocation();

        File holdingFile = new File(getPlugin().getDataFolder() + File.separator + "hold",
                chestPlayer.getUniqueId().toString() + ".yml");

        if (holdingFile.exists()) {

            FileConfiguration holdingChest = YamlConfiguration.loadConfiguration(holdingFile);

            if (holdingChest.get("item") != null)
                holdingChest.getList("item").stream().filter(holdingCurrentItem -> holdingCurrentItem != null)
                        .forEach(holdingCurrentItem -> chestPlayer.getWorld()
                                .dropItemNaturally(chestPlayerLocation, (ItemStack) holdingCurrentItem));

            holdingChest.set("slots", 9);
            holdingChest.set("item", new ArrayList<>());

            holdingChest.save(holdingFile);
            chestPlayer.closeInventory();

        }

    }

}
