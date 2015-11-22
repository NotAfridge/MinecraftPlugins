package com.ullarah.urocket.event;

import com.ullarah.urocket.function.CommonString;
import org.apache.commons.io.output.StringBuilderWriter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.Array;
import java.util.Random;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketLanguage.RB_HIDDEN;

public class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void variantChangeSpeak(AsyncPlayerChatEvent event) {

        CommonString commonString = new CommonString();

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (player.isFlying() && rocketUsage.contains(player.getUniqueId())) {

            switch (rocketVariant.get(player.getUniqueId())) {

                case ZERO:
                    event.setMessage(ChatColor.MAGIC + message);
                    break;

                case STEALTH:
                    commonString.messageSend(getPlugin(), player, true, RB_HIDDEN);
                    event.setCancelled(true);
                    break;

                case DRUNK:
                    StringBuilderWriter drunkMessage = new StringBuilderWriter();
                    for (String letter : message.split("")) {
                        int makeItalic = new Random().nextInt(2);
                        String letterItalic = ChatColor.ITALIC + letter + ChatColor.RESET;
                        drunkMessage.append(makeItalic == 1 ? letterItalic : letter);
                    }
                    event.setMessage(drunkMessage.toString());
                    break;

                case RAINBOW:
                    StringBuilderWriter rainbowMessage = new StringBuilderWriter();
                    int currentColour = 0;

                    ChatColor[] colors = new ChatColor[]{
                            ChatColor.RED,
                            ChatColor.GOLD,
                            ChatColor.YELLOW,
                            ChatColor.GREEN,
                            ChatColor.BLUE,
                            ChatColor.DARK_PURPLE,
                            ChatColor.LIGHT_PURPLE};

                    for (String letter : message.split("")) {

                        if (letter.matches("\\s")) {
                            rainbowMessage.append(letter);
                            continue;
                        }

                        String colourLetter = Array.get(colors, currentColour) + letter;
                        rainbowMessage.append(colourLetter);

                        currentColour++;
                        if (currentColour == colors.length) currentColour = 0;

                    }
                    event.setMessage(rainbowMessage.toString());
                    break;

                default:
                    break;

            }

        }

    }

}
