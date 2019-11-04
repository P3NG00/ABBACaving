package com.p3ng00.abbacaving.game;

import com.p3ng00.abbacaving.Main;
import com.p3ng00.abbacaving.util.ABBAUtil;
import com.p3ng00.abbacaving.util.MathUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static com.p3ng00.abbacaving.Main.chatUtil;

public class ABBAGame extends BukkitRunnable {

    private JavaPlugin plugin;
    private List<Player> party;
    private int displayFreq;
    private int timerGame;

    public ABBAGame(JavaPlugin plugin, List<Player> party, ABBASettings settings) {
        this.plugin = plugin;
        this.party = party;
        displayFreq = settings.displayFreq;
        timerGame = settings.minutes;
        chatUtil.broadcastComponentGlobally(new TextComponent(String.format(chatUtil.GAME_START.getText(), settings.minutes)));
        showScoreboard();
    }

    @Override
    public void run() {
        if (timerGame % displayFreq == 0) {
            for (Player player : party)
                chatUtil.printComponent(player, chatUtil.GAME_UPDATE);

            showScoreboard();
        }

        if (timerGame == 5 || timerGame == 3 || timerGame == 1) {
            String s;

            if (timerGame != 1)
                s = "s";
            else
                s = "";

            chatUtil.broadcastComponentToList(party, new TextComponent(String.format(chatUtil.GAME_TIME_WARN.getText(), timerGame, s)));
        }

        if (timerGame > 0)
            timerGame--;
        else {
            Player winner = ABBAUtil.getWinner(party);
            chatUtil.broadcastComponentGlobally(chatUtil.GAME_END);
            chatUtil.broadcastComponentGlobally(new TextComponent(String.format(chatUtil.GAME_WINNER.getText(), winner.getDisplayName(), MathUtil.calculateScore(winner))));
            showScoreboard();
            Main.endGame();
            cancel();
        }
    }

    private void showScoreboard() {
        ABBAScoreboard scoreboard = new ABBAScoreboard(party);
        scoreboard.runTaskTimer(plugin, 0, MathUtil.getTicksS(10));
    }

    public void kill() {
        timerGame = 0;
    }
}
