package com.ullarah.urocket.command;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketLanguage.RB_JACKET_CREATE_ERROR;
import static com.ullarah.urocket.RocketLanguage.RB_JACKET_SAVE_ERROR;

public class FuelCreation {

    public static void create(CommandSender sender) {

        CommonString commonString = new CommonString();

        Player player = (Player) sender;

        File fuelFile = new File(getPlugin().getDataFolder() + File.separator + "fuel", player.getUniqueId().toString() + ".yml");

        if (!fuelFile.exists()) {

            boolean fuelFileCreation = false;

            File dataDir = getPlugin().getDataFolder();
            if (!dataDir.exists()) fuelFileCreation = dataDir.mkdir();

            File fuelDir = new File(dataDir + File.separator + "fuel");
            if (!fuelDir.exists()) fuelFileCreation = fuelDir.mkdir();

            File fuelFileNew = new File(fuelDir, player.getUniqueId().toString() + ".yml");
            if (!fuelFileNew.exists()) try {
                fuelFileCreation = fuelFileNew.createNewFile();
            } catch (IOException e) {
                commonString.messageSend(getPlugin(), player, true, RB_JACKET_CREATE_ERROR);
                e.printStackTrace();
            }

            if (fuelFileCreation) {

                FileConfiguration fuelConfig = YamlConfiguration.loadConfiguration(fuelFileNew);
                fuelConfig.set("leather", new ArrayList<>());
                fuelConfig.set("iron", new ArrayList<>());
                fuelConfig.set("gold", new ArrayList<>());
                fuelConfig.set("diamond", new ArrayList<>());

                try {
                    fuelConfig.save(fuelFileNew);
                } catch (IOException e) {
                    commonString.messageSend(getPlugin(), player, true, RB_JACKET_SAVE_ERROR);
                    e.printStackTrace();
                }

            } else {

                commonString.messageSend(getPlugin(), player, true, RB_JACKET_CREATE_ERROR);
                player.closeInventory();

            }

        }

    }

}
