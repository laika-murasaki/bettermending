package bettermending.bettermending;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

class Bettermending extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking()) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (!meta.hasEnchant(Enchantment.MENDING)) {
            return;
        }

        int xpCost = 1;
        int repairAmount = 2;

        if (player.getTotalExperience() >= xpCost) {
            player.setTotalExperience(player.getTotalExperience() - xpCost);
            item.setDurability((short) Math.max(item.getDurability() - repairAmount, 0));
        }
    }
}
