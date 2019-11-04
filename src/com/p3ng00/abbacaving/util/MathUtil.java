package com.p3ng00.abbacaving.util;

import com.p3ng00.abbacaving.Main;
import com.p3ng00.abbacaving.item.ValidItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MathUtil {

    public static int getTicksM(double minutes) {
        return (int)(getTicksS(minutes) * 60.0);
    }

    public static int getTicksS(double seconds) {
        return (int)(seconds * 20.0);
    }

    public static int calculateScore(Player player) {
        int score = 0;
        ItemStack[] itemStacks = player.getInventory().getContents();

        for (ValidItem vi : Main.validItems.getValidItems())
            for (Material material : vi.materials)
                for (ItemStack itemstack : itemStacks)
                    if (itemstack != null && material == itemstack.getType())
                        score += itemstack.getAmount() * vi.multiplier;

        return score;
    }
}
