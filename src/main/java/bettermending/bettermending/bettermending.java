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
import org.bstats.bukkit.Metrics;
import java.util.List;


    public class bettermending extends JavaPlugin implements Listener {
        public void stats() {
            // Initialize bStats metrics
            int pluginId = 17330; // Replace with your plugin id
            Metrics metrics = new Metrics(this, pluginId);
        }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        // Print message to console when plugin is enabled
        getLogger().info("BetterMending has been enabled!");
        // Initialize bStats metrics
    }

    @Override
    public void onDisable() {
        // Print message to console when plugin is disabled
        getLogger().info("BetterMending has been disabled!");
    }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            Action action = event.getAction();

            Player player = event.getPlayer();

            if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) || !player.isSneaking()) {
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            // Check if the item has the MENDING enchantment
            if (!item.getEnchantments().containsKey(Enchantment.MENDING)) {
                return;
            }


            // Check if the MENDING enchantment is in the list of allowed enchantments
            List<String> allowedEnchantments = getConfig().getStringList("allowedEnchantments");
            if (!allowedEnchantments.contains(Enchantment.MENDING.getName())) {
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
