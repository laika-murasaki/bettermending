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
# The amount of experience points required to repair 1 durability point
xpCost: 1

# The amount of durability points repaired per use
repairAmount: 2

# List of enchantments that are allowed to be used with the repair feature.
# The plugin will only use enchantments from this list for repair.
# Enchantments should be specified using their Minecraft ID (e.g. MENDING, PROTECTION).
# To allow all enchantments, specify "any" (without quotes).
allowedEnchantments:
 - MENDING

# Message displayed when the player does not have permission to use the repair feature
noPermissionMessage: "You do not have permission to use the repair feature!"

# Message displayed when the player's item is already fully repaired
itemAlreadyRepaired: "Your item is already fully repaired."

# Message displayed when the player's item does not have the MENDING enchantment
itemDoesNotHaveMending: "Your item does not have the MENDING enchantment."

# Message displayed when the MENDING enchantment is not allowed on the server
mendingNotAllowed: "The MENDING enchantment is not allowed on this server."

# Message displayed when the player does not have enough XP to repair their item
insufficientXP: "You do not have enough XP to repair your item."

# Message displayed when showing the player how much durability is left until their item is fully repaired
durabilityLeftMessage: "Your item has %d durability left until it is fully repaired."

# Message displayed when showing the player how many repairs are needed and how much XP it will cost to fully repair their item
repairsNeededMessage: "It will take %d repair(s) to fully repair your item, costing a total of %d exp points."

```



### Permissions
bettermending.use: Allow a player to use the repair feature


### bStats
[![bstats](https://bstats.org/signatures/bukkit/Better%20Mending.svg)](https://bstats.org/plugin/bukkit/Better%20Mending/)
