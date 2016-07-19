package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class JacketClose implements Listener {

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        Inventory fuelInventory = event.getInventory();

        if (fuelInventory.getName().matches("" + ChatColor.DARK_RED + ChatColor.BOLD + "Rocket Boot Fuel Jacket")) {

            Player player = (Player) event.getPlayer();
            File fuelFile = rocketFunctions.getFuelFile(player);

            if (fuelFile.exists()) {

                FileConfiguration fuelConfig = YamlConfiguration.loadConfiguration(fuelFile);

                switch (fuelInventory.getSize()) {

                    case 9:
                        fuelConfig.set("leather", fuelInventory.getContents());
                        break;

                    case 18:
                        fuelConfig.set("iron", fuelInventory.getContents());
                        break;

                    case 27:
                        fuelConfig.set("gold", fuelInventory.getContents());
                        break;

                    case 36:
                        fuelConfig.set("diamond", fuelInventory.getContents());
                        break;

                }

                try {

                    fuelConfig.save(fuelFile);

                } catch (IOException e) {

                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_SAVE_ERROR);
                    e.printStackTrace();

                }

            } else commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_SAVE_ERROR);

        }

    }

}
