package bettermending.bettermending;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import javax.annotation.Nonnull;

import java.util.List;

public class bettermending extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        PluginCommand command = getCommand("bm");
        if (command != null) {
            command.setExecutor(new CommandHandler(this));
        }

        // Initialize bStats metrics
        int pluginId = 17330; // Replace with your plugin id
        Metrics metrics = new Metrics(this, pluginId);

        saveDefaultConfig();
        reloadConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) || !player.isSneaking()) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the item has the MENDING enchantment
        if (!item.getEnchantments().containsKey(Enchantment.MENDING)) {
            return;
        }

        // Check if the MENDING enchantment is in the list of allowed enchantments
        List <String> allowedEnchantments = getConfig().getStringList("allowedEnchantments");
        if (!allowedEnchantments.contains(Enchantment.MENDING.getName())) {
            return;
        }
        int xpCost = getConfig().getInt("xpCost");
        int repairAmount = getConfig().getInt("repairAmount");

        int playerExperience = ExperienceUtil.getPlayerExp(player); // pass player object as argument


        if (item.getDurability() == 0) {
            // Item is already fully repaired, do nothing
            return;
        }

        // Check whether player has bettermending.use permission
        if (!player.hasPermission("betterment.use")) {
            String noPermissionMessage = getConfig().getString("noPermissionMessage");
            if (noPermissionMessage != null) {
                player.sendMessage(noPermissionMessage);
            }
            return;
        }

        if (playerExperience >= xpCost) {
            player.giveExp(-xpCost);
            item.setDurability((short) Math.max(item.getDurability() - repairAmount, 0));
            player.updateInventory();
        }
    }
}
