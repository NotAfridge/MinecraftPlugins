package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.function.CommonString;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static com.ullarah.ujoinquit.JoinQuitInit.*;

public class JoinQuitFunctions {

    private CommonString commonString = new CommonString();

    public void listMessages(Player player, Message type) {

        String typeString = type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase();

        Inventory chestGUI = getPlugin().getServer().createInventory(
                null, 54, "" + ChatColor.DARK_AQUA + ChatColor.BOLD + typeString + " Message");

        for (int i = 0; i < type.getList().size(); i++) {

            if (i == 54) continue;

            String message = replacePlayerString(player, type.getList().get(i));
            String messageArray = ChatColor.translateAlternateColorCodes('&', ChatColor.WHITE + message);

            ItemStack paperItem = message.substring(0, 1).equals("&")
                    ? translateChatToPane(ChatColor.getByChar(message.substring(1, 2)))
                    : new ItemStack(Material.THIN_GLASS, 1);

            ItemMeta paperMeta = paperItem.getItemMeta();

            paperMeta.setDisplayName(messageArray);

            paperMeta.setLore(Collections.singletonList("" + ChatColor.YELLOW + ChatColor.ITALIC
                    + "Click to set " + typeString + " Message"));
            paperItem.setItemMeta(paperMeta);

            chestGUI.addItem(paperItem);

        }

        player.openInventory(chestGUI);

    }

    public String replacePlayerString(Player player, String message) {

        if (lastPlayer.equals(player.getPlayerListName())) lastPlayer = "nobody";

        if (message.contains("{player}"))
            message = message.replaceAll("\\{player\\}", player.getPlayerListName());

        if (message.contains("{l_player}"))
            message = message.replaceAll("\\{l_player\\}", player.getPlayerListName().toLowerCase());

        if (message.contains("{u_player}"))
            message = message.replaceAll("\\{u_player\\}", player.getPlayerListName().toUpperCase());

        if (message.contains("{p_player}"))
            message = message.replaceAll("\\{p_player\\}", lastPlayer);

        if (message.contains("{r_player}")) {
            String playerName = "nobody";
            Collection onlinePlayers = Bukkit.getOnlinePlayers();
            if (onlinePlayers.size() > 1) {
                Player randomPlayer = (Player) onlinePlayers.toArray()[new Random().nextInt(onlinePlayers.size() - 1)];
                playerName = randomPlayer.getPlayerListName();
            }
            message = message.replaceAll("\\{r_player\\}", playerName);
        }

        return message;

    }

    public void updateMessageHashMap() {

        for (Message type : Message.values())
            for (String message : getPlugin().getConfig().getStringList(type.getType()))
                type.getList().add(message);

        getPlugin().getLogger().log(Level.INFO,
                "Joins: " + joinMessages.size() + " | " + "Quits: " + quitMessages.size());

    }

    public File updatePlayerConfigFile() {

        File file = new File(getPlugin().getDataFolder() + File.separator + "player.yml");

        if (!file.exists()) try {

            if (file.createNewFile()) return file;

        } catch (IOException e) {

            e.printStackTrace();

        }

        return file;

    }

    public void updatePlayerMessageIndex() {

        for (String uuid : getPlayerConfig().getKeys(false)) {

            if (getPlayerConfig().contains(uuid + ".join"))
                playerJoinMessage.put(UUID.fromString(uuid), getPlayerConfig().getInt(uuid + ".join"));

            if (getPlayerConfig().contains(uuid + ".quit"))
                playerQuitMessage.put(UUID.fromString(uuid), getPlayerConfig().getInt(uuid + ".quit"));

            if (getPlayerConfig().contains(uuid + ".location"))
                playerJoinLocation.put(UUID.fromString(uuid), (Location) getPlayerConfig().get(uuid + ".location"));

        }

    }

    public String getMessage(Player player, Message type) {

        return type.getList().get(getPlayerConfig().getInt(player.getUniqueId().toString() + "." + type.toString().toLowerCase()));

    }

    public void setMessage(Player player, Message type, Integer index) {

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
            commonString.messageSend(getPlugin(), player, true, messageType + " message changed!");

        } catch (IOException e) {

            commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Error saving changes!");
            e.printStackTrace();

        }

    }

    public void setLocation(Player player) {

        try {

            UUID playerUUID = player.getUniqueId();
            YamlConfiguration config = getPlayerConfig();

            config.set(playerUUID.toString() + ".location", player.getEyeLocation());
            config.save(getPlayerConfigFile());

            playerJoinLocation.put(playerUUID, player.getEyeLocation());

            commonString.messageSend(getPlugin(), player, true, "Join location changed!");

        } catch (IOException e) {

            commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Error saving changes!");
            e.printStackTrace();

        }

    }

    public void showExtra(Player player) {

        Inventory options = Bukkit.createInventory(null, InventoryType.HOPPER,
                "" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Extra Options");

        ItemStack locationItem = new ItemStack(Material.COMPASS, 1);
        ItemMeta locationItemMeta = locationItem.getItemMeta();

        locationItemMeta.setDisplayName(ChatColor.WHITE + "Set Join Location");
        locationItemMeta.setLore(Arrays.asList(
                        ChatColor.YELLOW + "Set your current location as",
                        ChatColor.YELLOW + "your join location.")
        );

        locationItem.setItemMeta(locationItemMeta);

        options.addItem(
                locationItem
        );

        player.openInventory(options);

    }

    public void clearMessage(Player player) {

        try {

            UUID playerUUID = player.getUniqueId();
            YamlConfiguration config = getPlayerConfig();

            config.set(playerUUID.toString(), null);
            config.save(getPlayerConfigFile());

            playerJoinMessage.remove(playerUUID);
            playerQuitMessage.remove(playerUUID);
            playerJoinLocation.remove(playerUUID);

            commonString.messageSend(getPlugin(), player, true, "All settings cleared!");

        } catch (IOException e) {

            commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Error saving changes!");
            e.printStackTrace();

        }

    }

    public void displayHelp(Player player) {

        commonString.messageSend(getPlugin(), player, true, "Custom Join and Quit Messages");

        TextComponent allOptions = new TextComponent(new CommonString().pluginPrefix(getPlugin()) + "Click an option: ");

        TextComponent joinOption = new TextComponent(ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "Set Join" + ChatColor.AQUA + "] ");
        TextComponent quitOption = new TextComponent(ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "Set Quit" + ChatColor.AQUA + "] ");
        TextComponent extraOption = new TextComponent(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "Extra" + ChatColor.GREEN + "] ");
        TextComponent clearOption = new TextComponent(ChatColor.RED + "[" + ChatColor.DARK_RED + "Clear" + ChatColor.RED + "] ");

        joinOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq join"));
        quitOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq quit"));
        extraOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq extra"));
        clearOption.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jq clear"));

        joinOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to view and set a Join Message\n"
                        + ChatColor.GOLD + "/jq join").create()));

        quitOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to view and set a Quit Message\n"
                        + ChatColor.GOLD + "/jq quit").create()));

        extraOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to view Extra Options\n"
                        + ChatColor.GOLD + "/jq extra").create()));

        clearOption.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to clear everything\n"
                        + ChatColor.GOLD + "/jq clear").create()));

        allOptions.addExtra(joinOption);
        allOptions.addExtra(quitOption);
        allOptions.addExtra(extraOption);
        allOptions.addExtra(clearOption);

        player.spigot().sendMessage(allOptions);

    }

    private ItemStack translateChatToPane(ChatColor chatColor) {

        short paneColour = 0;

        switch (chatColor) {

            case BLACK:
                paneColour = 15;
                break;

            case DARK_BLUE:
                paneColour = 11;
                break;

            case DARK_GREEN:
                paneColour = 13;
                break;

            case DARK_AQUA:
                paneColour = 9;
                break;

            case DARK_RED:
                paneColour = 14;
                break;

            case DARK_PURPLE:
                paneColour = 10;
                break;

            case GOLD:
                paneColour = 1;
                break;

            case GRAY:
                paneColour = 8;
                break;

            case DARK_GRAY:
                paneColour = 7;
                break;

            case BLUE:
                paneColour = 3;
                break;

            case GREEN:
                paneColour = 5;
                break;

            case AQUA:
                paneColour = 3;
                break;

            case RED:
                paneColour = 14;
                break;

            case LIGHT_PURPLE:
                paneColour = 6;
                break;

            case YELLOW:
                paneColour = 4;
                break;

            case WHITE:
                paneColour = 0;
                break;

        }

        return new ItemStack(Material.STAINED_GLASS_PANE, 1, paneColour);

    }

    public enum Message {

        JOIN(joinMessages, "joinMessages"),
        QUIT(quitMessages, "quitMessages");

        private final List<String> list;
        private final String type;

        Message(List<String> getList, String getType) {
            this.list = getList;
            this.type = getType;
        }

        public List<String> getList() {
            return list;
        }

        public String getType() {
            return type;
        }

    }

}