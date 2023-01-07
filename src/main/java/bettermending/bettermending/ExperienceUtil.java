package bettermending.bettermending;

import org.bukkit.entity.Player;

public class ExperienceUtil {
    public static int getPlayerExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        // Get the amount of XP in the current level
        exp += getExpAtLevel(level);

        // Get the amount of XP towards the next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    private static int getExpAtLevel(int level) {
        if (level <= 15) {
            return level * level + 6 * level;
        } else if (level <= 30) {
            return 2 * level * level - 29 * level + 360;
        } else {
            return 5 * level * level - 151 * level + 2220;
        }
    }

    private static float getExpToLevelUp(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }
}
