package com.ullarah.urocket.init;

import org.bukkit.ChatColor;

public final class RocketLanguage {

    public static final String RB_NAME = ChatColor.RED + "Rocket Boots";

    public static final String RB_NOT_FOUND = ChatColor.RED + "Not Found";
    public static final String RB_VARIANT = "Variant: " + ChatColor.AQUA;
    public static final String RB_ENHANCE = "Enhancement: " + ChatColor.AQUA;

    public static final String RB_DURABILITY = ChatColor.YELLOW + "Rocket Boot Durability: ";
    public static final String RB_LEVEL = ChatColor.YELLOW + "Rocket Level ";
    public static final String RB_MALFUNCTION = ChatColor.RED + "Your boots have malfunctioned!";

    public static final String RB_ACTIVATE = "Rocket Boots Activated";
    public static final String RB_DEACTIVATE = "Rocket Boots Deactivated";
    public static final String RB_DISABLE = "Disabling Rocket Boots!";
    public static final String RB_FAIL_ATTACH = ChatColor.RED + "Rocket Boots failed to attach!";

    public static final String RB_FUEL_LOW = ChatColor.YELLOW + "Low Fuel";
    public static final String RB_FUEL_WARNING = ChatColor.ITALIC + "You might want to consider landing!";
    public static final String RB_HUNGRY = "You are too hungry to fly...";
    public static final String RB_MONEY = "You are too poor to fly...";

    public static final String RB_ATTACH = ChatColor.YELLOW + "You need to re-attach your Rocket Boots!";
    public static final String RB_EXPLODE = ChatColor.BOLD + "Rocket Boots have exploded!";
    public static final String RB_SPRINT = "You cannot run in your Rocket Boots!";

    public static final String RB_HIGH = "Rocket Boots don't work so well up high!";
    public static final String RB_LOW = "Rocket Boots don't work so in the void!";
    public static final String RB_NETHER = ChatColor.RED + "These Rocket Boots don't work in the Nether!";
    public static final String RB_STRIKE = ChatColor.RED + "Rocket Boots struck by lightning! Land quickly!";

    public static final String RB_SADDLE = ChatColor.RED + "Rocket Saddle";
    public static final String RB_WATER_WARNING = ChatColor.YELLOW + "These Rocket Boots do not work in water!";
    public static final String RB_WORLD_CHANGE = ChatColor.RED + "Rocket Boots do not like world changes!";

    public static final String RB_STATION_PLACE_ERROR = ChatColor.RED + "You can only place this on top of a Repair Tank!";
    public static final String RB_STATION_PLACE_SUCCESS = ChatColor.YELLOW + "Rocket Repair Station ready to use!";

    public static final String RB_STATION_START = ChatColor.YELLOW + "Rocket Boot Repair starting. Please stand still.";

    public static final String RB_RS_PLACE_ERROR = ChatColor.RED + "You can only place this on top of a Repair Station!";
    public static final String RB_RS_SNEAK_ERROR = ChatColor.RED + "You have to be sneaking to place this!";
    public static final String RB_RS_PLACE_SUCCESS = ChatColor.GREEN + "Repair Stand ready to use!";
    public static final String RB_RS_EXIST = ChatColor.YELLOW + "Repair Stand already exists at this location!";
    public static final String RB_RS_ENTITY = ChatColor.RED + "Entity in the way of Repair Stand placement!";
    public static final String RB_RS_CHANGE = "You can only place Rocket Boots on this stand!";

    public static final String RB_FZ_EXIST = ChatColor.YELLOW + "This is already a No-Fly Zone!";
    public static final String RB_FZ_REMOVE = ChatColor.GREEN + "Rocket Fly Zone Controller removed successfully!";
    public static final String RB_FZ_SUCCESS = ChatColor.YELLOW + "Rocket Fly Zone Controller is now activated!";
    public static final String RB_FZ_ENTRY = ChatColor.RED + "You have entered a No-Fly Zone!";
    public static final String RB_FZ_CURRENT = ChatColor.RED + "You are currently in a No-Fly Zone!";

    public static final String RB_JACKET_CREATE_ERROR = ChatColor.RED + "Error creating fuel contents.";
    public static final String RB_JACKET_SAVE_ERROR = ChatColor.RED + "Error saving fuel contents.";

    public static final String RB_MOD_ERROR = ChatColor.RED + "You cannot modify Rocket Equipment!";

    public static final String RB_GAMEMODE_ERROR = ChatColor.RED + "Rocket Boots do not work in this gamemode!";
    public static final String RB_EQUIP_ERROR = "These Rocket Boots cannot be equipped!";

    public static final String RB_COOLDOWN_HEAT = ChatColor.RED + "Your Rocket Boots have overheated!";
    public static final String RB_COOLDOWN_TOUCH = ChatColor.RED + "Ouch! You cannot take your boots of yet!";
    public static final String RB_COOLDOWN_LAND = "You need to land for them to cool down!";

    public static final String RB_USELESS = ChatColor.GRAY + "Useless Rocket Boots";
    public static final String RB_HIDDEN = ChatColor.ITALIC + "You are currently hidden!";

    public static final String RB_CONSUME_FLY = "You cannot eat and fly at the same time!";
    public static final String RB_CONSUME_HIDDEN = "You cannot eat while you are hidden!";

    public static String PlacementDeny(String type) {
        return ChatColor.YELLOW + type + " cannot be placed down!";
    }

    public static String WorldPlacementDeny(String type) {
        return ChatColor.RED + type + " cannot be placed here!";
    }

    public static String FuelRequired(String type) {
        return ChatColor.YELLOW + "You need more " + type + " to launch these boots!";
    }

    public static String FuelLow(String type) {
        return ChatColor.YELLOW + "You are running low on " + type + "!";
    }

    public static String FuelOutage(String type) {
        return ChatColor.YELLOW + "You ran out of " + type + " for your Rocket Boots!";
    }

}
