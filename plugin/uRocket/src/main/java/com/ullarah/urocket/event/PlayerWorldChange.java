package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketInit.*;

public class PlayerWorldChange implements Listener {

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {

        final Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (rocketPower.containsKey(player.getUniqueId())) {

                if (rocketUsage.contains(player.getUniqueId())) {
                    String changeMessage = ChatColor.RED + "Rocket Boots do not like world changes!";
                    TitleSubtitle.subtitle(player, 5, ChatColor.RED + changeMessage);
                    player.sendMessage(getMsgPrefix() + changeMessage);
                }

                disableRocketBoots(player, false, false, false, false, false, false);

            }

        }

    }

}
