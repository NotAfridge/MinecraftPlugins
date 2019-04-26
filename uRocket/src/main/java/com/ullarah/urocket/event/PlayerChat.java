package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.Array;
import java.util.Random;

public class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void variantChangeSpeak(AsyncPlayerChatEvent event) {

        CommonString commonString = new CommonString();

        Player player = event.getPlayer();
        RocketPlayer rp = RocketInit.getPlayer(player);
        String message = event.getMessage();

        if (player.isFlying() && rp.isUsingBoots()) {

            switch (rp.getBootData().getVariant()) {

                case ZERO:
                    event.setMessage(ChatColor.MAGIC + message);
                    break;

                case STEALTH:
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HIDDEN);
                    event.setCancelled(true);
                    break;

                case DRUNK:
                    StringBuilder drunkMessage = new StringBuilder();
                    for (String letter : message.split("")) {
                        int makeItalic = new Random().nextInt(2);
                        String letterItalic = ChatColor.ITALIC + letter + ChatColor.RESET;
                        drunkMessage.append(makeItalic == 1 ? letterItalic : letter);
                    }
                    event.setMessage(drunkMessage.toString());
                    break;

                case RAINBOW:
                    StringBuilder rainbowMessage = new StringBuilder();
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
