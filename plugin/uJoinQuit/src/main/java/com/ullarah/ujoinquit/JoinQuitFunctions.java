package com.ullarah.ujoinquit;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static com.ullarah.ujoinquit.JoinQuitInit.*;

public class JoinQuitFunctions {

    public static void listMessages(Player player, messageType type) {

        String typeString = type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase();

        Inventory chestGUI = getPlugin().getServer().createInventory(
                null, 54, "" + ChatColor.DARK_RED + ChatColor.BOLD + "Choose a " + typeString + " Message");

        for (int i = 0; i < type.getList().size(); i++) {

            if (i == 54) continue;

            String[] messageArray = ChatColor.translateAlternateColorCodes('&',
                    type.getList().get(i).replaceAll("\\{player\\}", player.getPlayerListName())).split("\n");

            List<String> messages = new ArrayList<>();

            messages.add("");
            for (String message : messageArray) messages.add(ChatColor.WHITE + message);
            messages.add("");

            ItemStack paperItem = new ItemStack(Material.PAPER, 1);
            ItemMeta paperMeta = paperItem.getItemMeta();

            paperMeta.setDisplayName(ChatColor.YELLOW + "Click to set " + typeString + " Message.");

            paperMeta.setLore(messages);

            paperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            paperItem.setItemMeta(paperMeta);

            paperItem.addUnsafeEnchantment(Enchantment.LUCK, 0);
            chestGUI.addItem(paperItem);

        }

        player.openInventory(chestGUI);

    }

    public static void updateMessageHashMap() {

        for (String list : Arrays.asList("joinMessages", "quitMessages"))
            for (String message : getPlugin().getConfig().getStringList(list)) {
                messageType.valueOf(messageType.getEnum(list).name()).getList().add(message);
            }

        getPlugin().getLogger().log(Level.INFO,
                "Joins: " + joinMessages.size() + " | " + "Quits: " + quitMessages.size());

    }

    public static File updatePlayerConfigFile() {

        File file = new File(getPlugin().getDataFolder() + File.separator + "player.yml");

        if (!file.exists()) {

            try {
                if (file.createNewFile()) return file;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;

    }

    public static void updatePlayerMessageIndex() {

        for (String uuid : getPlayerConfig().getKeys(false)) {

            if (getPlayerConfig().contains(uuid + ".join"))
                playerJoinMessage.put(UUID.fromString(uuid), getPlayerConfig().getInt(uuid + ".join"));

            if (getPlayerConfig().contains(uuid + ".quit"))
                playerQuitMessage.put(UUID.fromString(uuid), getPlayerConfig().getInt(uuid + ".quit"));

        }

    }

    public static String getMessage(Player player, messageType type) {

        int messageIndex = getPlayerConfig().getInt(player.getUniqueId().toString()
                + "." + type.toString().toLowerCase());

        return type.getList().get(messageIndex);

    }

    public static void setMessage(Player player, messageType type, Integer index) {

        try {

            UUID playerUUID = player.getUniqueId();
            YamlConfiguration config = getPlayerConfig();

            config.set(playerUUID.toString() + "." + type.toString().toLowerCase(), index);
            config.save(getPlayerConfigFile());

            switch (type) {

                case JOIN:
                    playerJoinMessage.put(playerUUID, index);
                    break;

                case QUIT:
                    playerQuitMessage.put(playerUUID, index);
                    break;

            }

            String messageType = type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase();
            player.sendMessage(getMsgPrefix() + messageType + " message changed!");

        } catch (IOException e) {

            player.sendMessage(getMsgPrefix() + ChatColor.RED + "Error saving changes!");
            e.printStackTrace();

        }

    }

    public static void clearMessage(Player player) {

        try {

            UUID playerUUID = player.getUniqueId();
            YamlConfiguration config = getPlayerConfig();

            config.set(playerUUID.toString(), null);
            config.save(getPlayerConfigFile());

            playerJoinMessage.remove(playerUUID);
            playerQuitMessage.remove(playerUUID);

            player.sendMessage(getMsgPrefix() + "Join and Quit messages cleared!");

        } catch (IOException e) {

            player.sendMessage(getMsgPrefix() + ChatColor.RED + "Error saving changes!");
            e.printStackTrace();

        }

    }

    public static void displayHelp(Player player) {

        player.sendMessage(getMsgPrefix() + "Custom Join and Quit Messages");

        TextComponent allOptions = new TextComponent(getMsgPrefix() + "Click an option: ");

        TextComponent joinOption = new TextComponent(
                ChatColor.LIGHT_PURPLE + "[" + ChatColor.AQUA + "Set Join" + ChatColor.LIGHT_PURPLE + "] ");

        TextComponent quitOption = new TextComponent(
                ChatColor.LIGHT_PURPLE + "[" + ChatColor.AQUA + "Set Quit" + ChatColor.LIGHT_PURPLE + "] ");

        TextComponent clearOption = new TextComponent(
                ChatColor.RED + "[" + ChatColor.DARK_RED + "Clear" + ChatColor.RED + "] ");

        joinOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq join"));
        quitOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq quit"));
        clearOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq clear"));

        joinOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to view and set a Join Message\n"
                        + ChatColor.GOLD + "/jq join").create()));

        quitOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to view and set a Quit Message\n"
                        + ChatColor.GOLD + "/jq quit").create()));

        clearOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to clear both Join and Quit Messages\n"
                        + ChatColor.GOLD + "/jq clear").create()));

        allOptions.addExtra(joinOption);
        allOptions.addExtra(quitOption);
        allOptions.addExtra(clearOption);

        player.spigot().sendMessage(allOptions);

    }

    public enum messageType {

        JOIN(joinMessages, "joinMessages"), QUIT(quitMessages, "quitMessages");

        private final List<String> list;
        private final String type;

        private messageType(List<String> getList, String getType) {
            list = getList;
            type = getType;
        }

        public static messageType getEnum(String messageString) {
            for (messageType m : messageType.values()) if (messageString.equals(m.type)) return m;
            return null;
        }

        public List<String> getList() {
            return list;
        }

    }

}