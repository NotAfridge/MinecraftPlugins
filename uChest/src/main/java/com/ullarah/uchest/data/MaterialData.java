package com.ullarah.uchest.data;

import org.bukkit.configuration.file.FileConfiguration;

public class MaterialData {

    private boolean shuffle;
    private boolean donation;
    private boolean random;
    private double money;
    private double xp;

    public MaterialData(FileConfiguration config, String material) {
        shuffle  = config.getBoolean(material + ".s");
        donation = config.getBoolean(material + ".d");
        random   = config.getBoolean(material + ".r");
        money    = config.getDouble(material + ".e");
        xp       = config.getDouble(material + ".x");
    }

    public boolean isShuffleEnabled() {
        return shuffle;
    }

    public boolean isDonationEnabled() {
        return donation;
    }

    public boolean isRandomEnabled() {
        return random;
    }

    public double getMoney() {
        return money;
    }

    public double getXp() {
        return xp;
    }
}
