package bettermending.bettermending;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
    @Override
    public void onEnable() {
        // Initialize bStats metrics
        int pluginId = 17330; // Replace with your plugin id
        Metrics metrics = new Metrics(this, pluginId);

        saveDefaultConfig();
        reloadConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        getCommand("bm status").setExecutor(new RepairStatusCommand(this));
        getCommand("bm reload").setExecutor(new ReloadCommand(this));

    }

    @Override
    public void onDisable() {
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
                player.sendMessage(getConfig().getString("noPermissionMessage"));
                return;
            }

            if (playerExperience >= xpCost) {
                player.giveExp(-xpCost);
                item.setDurability((short) Math.max(item.getDurability() - repairAmount, 0));
                player.updateInventory();
            }
        }
        public class RepairStatusCommand implements CommandExecutor {
            private final JavaPlugin plugin;

            public RepairStatusCommand(JavaPlugin plugin) {
                this.plugin = plugin;
            }

            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be run by a player.");
                    return true;
                }

                Player player = (Player) sender;

                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getDurability() == 0) {
                    player.sendMessage(getConfig().getString("itemAlreadyRepaired"));
                    return true;
                }

                // Check if the item has the MENDING enchantment
                if (!item.getEnchantments().containsKey(Enchantment.MENDING)) {
                    player.sendMessage(getConfig().getString("itemDoesNotHaveMending"));
                    return true;
                }

                int xpCost = plugin.getConfig().getInt("xpCost");
                int repairAmount = plugin.getConfig().getInt("repairAmount");
                int playerExperience = expcu.getPlayerExp(player); // pass player object as argument

                // Calculate how much durability the item has left until it is fully repaired
                int durabilityLeft = item.getType().getMaxDurability() - item.getDurability();
                int repairsNeeded = (int) Math.ceil((double) durabilityLeft / repairAmount);
                int totalXpCost = repairsNeeded * xpCost;

                player.sendMessage(String.format(getConfig().getString("durabilityLeftMessage"), durabilityLeft));
                player.sendMessage(String.format(getConfig().getString("repairsNeededMessage"), repairsNeeded, totalXpCost));

                return true;
            }
        }
        public class ReloadCommand implements CommandExecutor {
            private final JavaPlugin plugin;

            public ReloadCommand(JavaPlugin plugin) {
                this.plugin = plugin;
            }

            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (!sender.hasPermission("bettermending.reload")) {
                    sender.sendMessage(getConfig().getString("noPermissionMessage"));
                    return true;
                }

                plugin.reloadConfig();
                sender.sendMessage("Config reloaded.");
                return true;
            }
        }


    }
