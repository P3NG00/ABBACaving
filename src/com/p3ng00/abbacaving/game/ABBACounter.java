package com.p3ng00.abbacaving.game;

import com.p3ng00.abbacaving.util.MathUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ABBACounter extends BukkitRunnable {

    public ABBAGame game;
    private JavaPlugin plugin;
    private List<Player> party;
    private ABBASettings settings;
    private int timer;

    public ABBACounter(JavaPlugin plugin, List<Player> party, ABBASettings settings) {
        this.plugin = plugin;
        this.party = party;
        this.settings = settings;
        timer = 5;
    }

    private final String[] TITLES = {
            ChatColor.GREEN + "5",
            ChatColor.GREEN + "4",
            ChatColor.YELLOW + "3",
            ChatColor.YELLOW + "2",
            ChatColor.RED + "1",
            ChatColor.GREEN + "" + ChatColor.BOLD + "GO!",
    };

    @Override
    public void run() {
        if (timer > 0) {
            for (Player player : party)
                player.sendTitle(TITLES[5 - timer], "", 0, MathUtil.getTicksS(1), MathUtil.getTicksS(1));
            timer --;
        } else {
            game = new ABBAGame(plugin, party, settings);
            game.runTaskTimer(plugin, 0, MathUtil.getTicksM(1));
            cancel();
        }
    }

    public void kill() {
        timer = 0;
    }
}
