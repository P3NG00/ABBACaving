package com.p3ng00.abbacaving;

import com.p3ng00.abbacaving.item.ValidItem;
import com.p3ng00.abbacaving.item.ValidItems;
import com.p3ng00.abbacaving.listener.ABBAPickupListener;
import com.p3ng00.abbacaving.util.ChatUtil;
import com.p3ng00.abbacaving.util.MathUtil;
import com.p3ng00.abbacaving.game.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements TabCompleter {
    public static ChatUtil chatUtil;
    public static ValidItems validItems;
    private static ABBASettings settings;
    private ABBACounter counter;
    public static List<Player> party;
    private static boolean gameActive;

    @Override
    public void onEnable() {
        chatUtil = new ChatUtil();
        validItems = new ValidItems();
        settings = new ABBASettings();
        party = new ArrayList<>();
        gameActive = false;
        getCommand("abba").setTabCompleter(this);
        getServer().getPluginManager().registerEvents(new ABBAPickupListener(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.toLowerCase().equals("abba")) {
            for (int i = 0; i < args.length; i++)
                args[i] = args[i].toLowerCase();
            if (args.length > 0) {
                if (args[0].equals("party")) {
                    if (args.length > 1) {
                        if (args[1].equals("join")) {
                            if (!gameActive) {
                                try {
                                    join(Bukkit.getPlayer(args[2].toLowerCase()));
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    if (sender instanceof Player)
                                        join((Player)sender);
                                    else
                                        chatUtil.printComponent(sender, chatUtil.ERROR_EXECUTOR);
                                }
                            }
                            else
                                chatUtil.printComponent(sender, chatUtil.ERROR_ACTIVE);
                        } else if (args[1].equals("leave")) {
                            try {
                                leave(Bukkit.getPlayer(args[2].toLowerCase()));
                            } catch (ArrayIndexOutOfBoundsException e) {
                                if (sender instanceof Player)
                                    leave((Player)sender);
                                else
                                    chatUtil.printComponent(sender, chatUtil.ERROR_EXECUTOR);
                            }
                        } else if (args[1].equals("list"))
                            chatUtil.printPlayerList(sender, party);
                        else
                            chatUtil.printComponentSyntax(sender, "party [join|leave|list]");
                    }
                }
                else if (args[0].equals("game")) {
                    if (sender instanceof Player && party.contains(sender)) {
                        if (args[1].equals("start")) {
                            if (gameActive)
                                chatUtil.printComponent(sender, chatUtil.ERROR_ACTIVE);
                            else if (party.size() < 2)
                                chatUtil.printComponent(sender, chatUtil.ERROR_NOT_ENOUGH_PLAYERS);
                            else {
                                gameActive = true;
                                counter = new ABBACounter(this, party, settings);
                                counter.runTaskTimer(this, 0, MathUtil.getTicksS(1));
                            }
                        } else if (args[1].equals("stop")) {
                            if (gameActive) {
                                counter.kill();
                                counter.game.kill();
                            } else
                                chatUtil.printComponent(sender, chatUtil.ERROR_INACTIVE);
                        } else
                            chatUtil.printComponentSyntax(sender, "game [start|stop]");
                    } else
                        chatUtil.printComponent(sender, chatUtil.ERROR_EXECUTOR);
                } else if (args[0].equals("set")) {
                    String syntax = "set %s <number>";
                    try {
                        if (args[1].equals("minutes")) {
                            int m;
                            try {
                                m = Integer.parseInt(args[2]);
                                if (!(m < 1))
                                    settings.minutes = m;
                                else
                                    chatUtil.printComponent(sender, chatUtil.ERROR_NUMBER);
                            } catch (NullPointerException | NumberFormatException e) {
                                chatUtil.printComponentSyntax(sender, String.format(syntax, "minutes"));
                            } catch (ArrayIndexOutOfBoundsException ignored) {}
                            chatUtil.printComponent(sender, new TextComponent(String.format(chatUtil.SETTING_LENGTH.getText(), settings.minutes)));
                        } else if (args[1].equals("displayfreq")) {
                            int f;
                            try {
                                f = Integer.parseInt(args[2]);
                                if (!(f < -1))
                                    settings.displayFreq = f;
                                else
                                    chatUtil.printComponent(sender, chatUtil.ERROR_NUMBER);
                            } catch (NullPointerException | NumberFormatException e) {
                                chatUtil.printComponentSyntax(sender, String.format(syntax, "displayFreq"));
                            }
                            catch (ArrayIndexOutOfBoundsException ignored) {}
                            chatUtil.printComponent(sender, new TextComponent(String.format(chatUtil.SETTING_SCOREBOARD_FREQUENCY.getText(), settings.displayFreq)));
                        } else
                            chatUtil.printComponentSyntax(sender, "set [minutes|displayFreq] <number>");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        chatUtil.printComponentSyntax(sender, "set [minutes|displayFreq] <number>");
                    }
                } else
                    chatUtil.printComponentSyntax(sender, "[party|game|set]");
            } else
                chatUtil.printComponentSyntax(sender, "[party|game|set]");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < args.length; i++)
            args[i] = args[i].toLowerCase();
        if (cmd.getName().toLowerCase().equals("abba")) {
            switch (args.length) {
                case 1:
                    list.add("party");
                    list.add("game");
                    list.add("set");
                case 2:
                    switch (args[0]) {
                        case "party":
                            list.add("join");
                            list.add("leave");
                            list.add("list");
                            break;
                        case "game":
                            list.add("start");
                            list.add("stop");
                            break;
                        case "set":
                            list.add("minutes");
                            list.add("displayFreq");
                            break;
                    }
            }
        }
        return list;
    }

    private void join(Player player) {
        if (!party.contains(player)) {
            party.add(player);
            chatUtil.printComponent(player, chatUtil.GAME_JOIN);
            chatUtil.broadcastComponentGlobally(new TextComponent(String.format(chatUtil.PARTY_JOIN.getText(), player.getDisplayName())));
        } else
            chatUtil.printComponent(player, chatUtil.ERROR_JOINED);
    }

    private void leave(Player player) {
        if (party.contains(player)) {
            party.remove(player);
            chatUtil.printComponent(player, chatUtil.GAME_LEAVE);
            chatUtil.broadcastComponentGlobally(new TextComponent(String.format(chatUtil.PARTY_LEAVE.getText(), player.getDisplayName())));
        } else
            chatUtil.printComponent(player, chatUtil.ERROR_NOT_JOINED);
    }

    public static void endGame() {
        gameActive = false;
        party.clear();
    }

    public static boolean isValidItem(Material material)  {
        for (ValidItem vi : Main.validItems.getValidItems())
            for (Material m : vi.materials)
                if (material == m)
                    return true;
        return false;
    }
}
