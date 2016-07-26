package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestPickup {

    public static final String toolName = ChatColor.YELLOW + "Chest Pickup Tool";

    public void runCommand(CommandSender sender, String[] args) {

        CommonString c = new CommonString();

        if (!(sender instanceof Player)) {
            c.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (!ChestInit.chestTypeEnabled.get("pchest")) {
            c.messageSend(ChestInit.getPlugin(), sender, "Pickup Chest Tool is currently unavailable.");
            return;
        }

        Player p = (Player) sender;

        if (args.length > 0) {

            ItemStack i = p.getInventory().getItemInMainHand();

            if (i.getType() == Material.CHEST) if (i.hasItemMeta())
                if (i.getItemMeta().hasDisplayName()) if (i.getItemMeta().getDisplayName().matches(
                        "" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest(.*)")) {

                    String n = args[0];

                    if (n.length() > 16) {
                        c.messageSend(ChestInit.getPlugin(), p,
                                ChatColor.RED + "Name cannot be longer than 16 characters.");
                        return;
                    }

                    ItemMeta m = i.getItemMeta();

                    m.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest ["
                            + ChatColor.GOLD + n + ChatColor.YELLOW + ChatColor.BOLD + "]");

                    i.setItemMeta(m);
                    return;

                }

            c.messageSend(ChestInit.getPlugin(), p, ChatColor.RED + "This is not a content chest.");
            return;

        }

        if (ChestInit.getWorldGuard() != null) {

            int accessLevel = ChestInit.getPlugin().getConfig().getInt("pchest.access");
            boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean("pchest.removelevel");

            if (p.getLevel() > accessLevel) {

                PlayerInventory v = p.getInventory();
                int f = v.firstEmpty();

                if (f >= 0) {
                    if (removeLevel) p.setLevel(p.getLevel() - accessLevel);
                    v.setItem(f, tool());
                } else c.messageSend(ChestInit.getPlugin(), p, "Your inventory is full.");

            } else {

                String s = accessLevel > 1 ? "s" : "";
                c.messageSend(ChestInit.getPlugin(), p, "You need more than "
                        + accessLevel + " level" + s + " to get this tool.");

            }

        } else c.messageSend(ChestInit.getPlugin(), sender, "Chest Pickup Tool is currently unavailable.");

    }

    private ItemStack tool() {

        ItemStack toolStack = new ItemStack(Material.SHEARS, 1);
        ItemMeta toolMeta = toolStack.getItemMeta();

        toolMeta.setDisplayName(toolName);

        toolMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        toolStack.setItemMeta(toolMeta);

        toolStack.addUnsafeEnchantment(Enchantment.MENDING, 1);

        return toolStack;

    }

}
