package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.init.RocketEnhancement;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class RocketRepair {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    for (Player player : RocketInit.getPlayers()) {
                        if (player.isFlying()) {
                            processSelfRepairCheck(player);
                        }
                    }

                }), 400, 400);

    }

    /**
     * Process self repair check for the given player
     * @param player Player who is flying
     */
    private void processSelfRepairCheck(Player player) {
        RocketPlayer rp = RocketInit.getPlayer(player);
        if (rp.getBootData() == null)
            return;

        RocketEnhancement.Enhancement enhancement = rp.getBootData().getEnhancement();
        if (enhancement != RocketEnhancement.Enhancement.REPAIR)
            return;

        ItemStack rocketBoots = player.getInventory().getBoots();
        if (rocketBoots == null)
            return;

        RocketFunctions rocketFunctions = new RocketFunctions();
        int repairRate = rocketFunctions.getBootRepairRate(rocketBoots.getType());
        if (repairRate > 0) {

            short currentDurability = rocketBoots.getDurability();
            if (currentDurability > 0) {
                rocketBoots.setDurability((short) (currentDurability - repairRate));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.35f, 1.75f);
            }

        }
    }

}
