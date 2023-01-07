package bettermending.bettermending;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class CommandHandler implements CommandExecutor {
    private final JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            String fullyRepairedMessage = plugin.getConfig().getString("fullyRepairedMessage");
            if (fullyRepairedMessage != null) {
                player.sendMessage(fullyRepairedMessage);
            }
            return true;
        }


        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("betterment.reload")) {
                String noPermissionMessage = plugin.getConfig().getString("noPermissionMessage");
                if (noPermissionMessage != null) {
                    player.sendMessage(noPermissionMessage);
                }
                return true;
            }

            plugin.reloadConfig();
            String reloadSuccessMessage = plugin.getConfig().getString("reloadSuccessMessage");
            if (reloadSuccessMessage != null) {
                player.sendMessage(reloadSuccessMessage);
            }
            return true;
        }

        // Add the code for the "status" command here
        if (args[0].equalsIgnoreCase("status")) {
            // Get the item that the player is holding
            ItemStack item = player.getInventory().getItemInMainHand();

            // Check if the item is repairable
            if (item.getType().getMaxDurability() == 0) {
                String notRepairableMessage = plugin.getConfig().getString("notRepairableMessage");
                if (notRepairableMessage != null) {
                    player.sendMessage(notRepairableMessage);
                }
                return true;
            }

            // Calculate the durability left
            int durabilityLeft = item.getType().getMaxDurability() - item.getDurability();
            String durabilityLeftMessage = plugin.getConfig().getString("durabilityLeftMessage");
            if (durabilityLeftMessage != null) {
                durabilityLeftMessage = durabilityLeftMessage.replace("%d", Integer.toString(durabilityLeft));
                player.sendMessage(durabilityLeftMessage);
            }
            return true;
        }

        return false;
    }
}