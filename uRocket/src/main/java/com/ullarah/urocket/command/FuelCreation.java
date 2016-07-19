package com.ullarah.urocket.command;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FuelCreation {

    public static void create(CommandSender sender) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        Player player = (Player) sender;
        File fuelFile = rocketFunctions.getFuelFile(player);

        if (!fuelFile.exists()) {

            boolean fuelFileCreation = false;

            File dataDir = RocketInit.getPlugin().getDataFolder();
            if (!dataDir.exists()) fuelFileCreation = dataDir.mkdir();

            File fuelDir = new File(dataDir + File.separator + "fuel");
            if (!fuelDir.exists()) fuelFileCreation = fuelDir.mkdir();

            File fuelFileNew = new File(fuelDir, player.getUniqueId().toString() + ".yml");
            if (!fuelFileNew.exists()) try {
                fuelFileCreation = fuelFileNew.createNewFile();
            } catch (IOException e) {
                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_CREATE_ERROR);
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
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_SAVE_ERROR);
                    e.printStackTrace();
                }

            } else {
                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_CREATE_ERROR);
                player.closeInventory();
            }

        }

    }

}
