package com.ullarah.urocket.event;

import org.apache.commons.io.output.StringBuilderWriter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Random;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_HIDDEN;

public class PlayerChat implements Listener {

    @EventHandler
    public void variantChangeSpeak(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (rocketEffects.contains(player.getUniqueId())) {

            String message = event.getMessage();

            switch (rocketVariant.get(player.getUniqueId())) {

                case ZERO:
                    event.setMessage(ChatColor.MAGIC + message);
                    break;

                case STEALTH:
                    messageSend(getPlugin(), player, true, RB_HIDDEN);
                    event.setCancelled(true);
                    break;

                case AGENDA:
                    event.setMessage(ChatColor.LIGHT_PURPLE + message);
                    break;

                case DRUNK:
                    StringBuilderWriter drunkMessage = new StringBuilderWriter();
                    for (String letter : message.split("")) {
                        int makeItalic = new Random().nextInt(2);
                        String letterItalic = ChatColor.ITALIC + letter + ChatColor.RESET;
                        drunkMessage.append(makeItalic == 1 ? letterItalic : letter);
                    }
                    event.setMessage(drunkMessage.toString());

                default:
                    break;

            }

        }

    }

}
