package com.p3ng00.abbacaving.util;

import com.p3ng00.abbacaving.Main;
import com.p3ng00.abbacaving.item.ValidItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ABBAUtil {
    public static Player getWinner(List<Player> party) {
        Player player = party.get(0);
        int p1;
        int p2;

        for (Player p : party) {
            p1 = MathUtil.calculateScore(player);
            p2 = MathUtil.calculateScore(p);

            if (p2 > p1)
                player = p;
        }

        return player;
    }
}
