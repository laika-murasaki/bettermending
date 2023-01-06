package bettermending.bettermending;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
public final class Bettermending extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.isSneaking() && item.getType() != Material.AIR && item.hasItemMeta() && item.getEnchantmentLevel(Enchantment.MENDING) > 0) {
                int xp = player.getTotalExperience();

                // Calculate how much durability the player can repair
                int repairAmount = xp / 2;
                int itemDurability = item.getDurability();
                int newDurability = Math.max(0, itemDurability - repairAmount);

                // Repair the item and remove the necessary amount of xp
                item.setDurability((short) newDurability);
                int xpCost = (itemDurability - newDurability) / 2;
                int newXp = Math.max(0, xp - xpCost);
                int newLevel = 0;
                while (newLevel < player.getLevel() && newXp >= player.getExpToLevel()) {
                    newXp -= player.getExpToLevel();
                    newLevel++;
                }
                float newProgress = (float) newXp / (float) player.getExpToLevel();
                player.setExp(newProgress);
                player.setLevel(newLevel);


                // Send a message to the player
                player.sendMessage(ChatColor.GREEN + "Repaired item for " + (itemDurability - newDurability) + " durability using " + xpCost + " experience points.");
            }
        }
    }



}