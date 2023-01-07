# BetterMending
A plugin that allows players to repair their items with experience points or a specified item, with customizable repair amounts and costs.

### Features
- Repair items using experience points or a specified item
- Specify a permission required to use the repair feature
- Repair items with the right click mouse button while holding shift
- Allow only certain enchantments to be repaired with the plugin

![BetterMending gif](https://hynse.xyz/downloadable/bettermending.gif)



### Configuration
The plugin's configuration can be found in the `plugins/BetterMending/config.yml` file.
```
xpCost: 1 # The amount of experience points required to repair 1 durability point
repairAmount: 2 # The amount of durability points repaired per use

# List of enchantments that are allowed to be used with the repair feature.
# The plugin will only use enchantments from this list for repair.
# Enchantments should be specified using their Minecraft ID (e.g. MENDING, PROTECTION).
# To allow all enchantments, specify "any" (without quotes).
allowedEnchantments:
 - MENDING
```



### Permissions
bettermending.use: Allow a player to use the repair feature


### bStats
[![bstats](https://bstats.org/signatures/bukkit/Better%20Mending.svg)](https://bstats.org/plugin/bukkit/Better%20Mending/)
