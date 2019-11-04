package com.p3ng00.abbacaving.game;

import com.p3ng00.abbacaving.util.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ABBAScoreboard extends BukkitRunnable {

    private Scoreboard scoreboard;
    private Objective objective;

    private List<Player> party;
    private boolean shown;

    public ABBAScoreboard(List<Player> party) {
        this.party = party;
        shown = false;
    }

    @Override
    public void run() {
        if (!shown) {
            shown = true;
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            scoreboard = manager.getMainScoreboard();
            objective = scoreboard.registerNewObjective("abbacaving", "dummy", "AbbaCaving");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            for (Player player : party) {
                Score score = objective.getScore(player.getDisplayName());
                score.setScore(MathUtil.calculateScore(player));
            }

            for (Player player : party)
                player.setScoreboard(scoreboard);
        } else {
            objective.setDisplaySlot(null);

            for (Player player : party)
                player.setScoreboard(scoreboard);

            cancel();
        }
    }
}
