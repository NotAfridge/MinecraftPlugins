package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketLanguage.RB_JACKET_SAVE_ERROR;

public class JacketClose implements Listener {

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        CommonString commonString = new CommonString();

        Inventory fuelInventory = event.getInventory();

        if (fuelInventory.getName().matches("§4§lRocket Boot Fuel Jacket")) {

            Player player = (Player) event.getPlayer();

            File fuelFile = new File(getPlugin().getDataFolder() + File.separator + "fuel", player.getUniqueId().toString() + ".yml");

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

                    commonString.messageSend(getPlugin(), player, true, RB_JACKET_SAVE_ERROR);
                    e.printStackTrace();

                }

            } else commonString.messageSend(getPlugin(), player, true, RB_JACKET_SAVE_ERROR);

        }

    }

}
