package bettermending.bettermending;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

    public class bettermending extends JavaPlugin implements Listener {
        public void RepairPlugin() {
            // This is the public constructor of the plugin class
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        // Print message to console when plugin is enabled
        getLogger().info("BetterMending has been enabled!");
    }

    @Override
    public void onDisable() {
        // Print message to console when plugin is disabled
        getLogger().info("BetterMending has been disabled!");
    }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {

            Action action = event.getAction();
            // rest of the event handler code

        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) || !player.isSneaking()) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (!meta.hasEnchant(Enchantment.MENDING)) {
            return;
        }

        int xpCost = getConfig().getInt("xpCost");
        int repairAmount = getConfig().getInt("repairAmount");

        int playerExperience = expcu.getPlayerExp(player); // pass player object as argument


        if (item.getDurability() == 0) {
            // Item is already fully repaired, do nothing
            return;
        }

        // Check whether player has bettermending.use permission
        if (!player.hasPermission("bettermending.use")) {
            player.sendMessage("You do not have permission to use the repair feature!");
            return;
        }

        if (playerExperience >= xpCost) {
            player.giveExp(-xpCost);
            item.setDurability((short) Math.max(item.getDurability() - repairAmount, 0));
            player.updateInventory();
        }
    }
}
