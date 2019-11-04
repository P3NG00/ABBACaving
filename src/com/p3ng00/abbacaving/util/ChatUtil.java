package com.p3ng00.abbacaving.util;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtil {
    public final TextComponent GAME_JOIN;
    public final TextComponent GAME_LEAVE;
    public final TextComponent GAME_START;
    public final TextComponent GAME_END;
    public final TextComponent GAME_UPDATE;
    public final TextComponent GAME_WINNER;
    public final TextComponent GAME_TIME_WARN;
    public final TextComponent PARTY_JOIN;
    public final TextComponent PARTY_LEAVE;
    public final TextComponent PARTY_EMPTY;
    public final TextComponent SETTING_LENGTH;
    public final TextComponent SETTING_SCOREBOARD_FREQUENCY;
    public final TextComponent ERROR_ACTIVE;
    public final TextComponent ERROR_INACTIVE;
    public final TextComponent ERROR_JOINED;
    public final TextComponent ERROR_NOT_JOINED;
    public final TextComponent ERROR_NOT_ENOUGH_PLAYERS;
    public final TextComponent ERROR_EXECUTOR;
    public final TextComponent ERROR_NUMBER;
    public final TextComponent ERROR;

    public ChatUtil() {
        GAME_JOIN = new TextComponent(ChatColor.GREEN + "You have joined the party!");
        GAME_LEAVE = new TextComponent(ChatColor.YELLOW + "You have left the party.");
        GAME_START = new TextComponent(ChatColor.GREEN + "Game of %d minute(s) has started!");
        GAME_END = new TextComponent(ChatColor.YELLOW + "Game has ended!");
        GAME_UPDATE = new TextComponent(ChatColor.AQUA + "The scoreboard has been updated!");
        GAME_WINNER = new TextComponent(ChatColor.GREEN + "%s has won with %d points!"); // Use String.format() to change '%s' and '%d'
        GAME_TIME_WARN = new TextComponent(ChatColor.RED + "%d minute%s remaining!"); // Use String.format() to change '%d' and '%s'
        PARTY_JOIN = new TextComponent(ChatColor.GREEN + "%s has joined the party!"); // Use String.format() to change '%s'
        PARTY_LEAVE = new TextComponent(ChatColor.YELLOW + "%s has left the party."); // Use String.format() to change '%s'
        PARTY_EMPTY = new TextComponent(ChatColor.RED + "No one in party.");
        SETTING_LENGTH = new TextComponent(ChatColor.AQUA + "Games are currently set to %d minutes."); // Use String.format() to change '%d'
        SETTING_SCOREBOARD_FREQUENCY = new TextComponent(ChatColor.AQUA + "Scoreboard is set to show every %d minutes."); // Use String.format() to change '%d'
        ERROR_ACTIVE = new TextComponent(ChatColor.RED + "Game is currently in progress.");
        ERROR_INACTIVE = new TextComponent(ChatColor.RED + "There is no running game.");
        ERROR_JOINED = new TextComponent(ChatColor.RED + "Already in party.");
        ERROR_NOT_JOINED = new TextComponent(ChatColor.RED + "You aren't in the party.");
        ERROR_NOT_ENOUGH_PLAYERS = new TextComponent(ChatColor.RED + "Not enough players in party.");
        ERROR_EXECUTOR = new TextComponent(ChatColor.RED + "Command must be executed by player in party.");
        ERROR_NUMBER = new TextComponent(ChatColor.RED + "Number must be greater than 0.");
        ERROR = new TextComponent(ChatColor.DARK_RED + "Error");
    }

    public void printComponentSyntax(CommandSender sender, String syntax) {
        printComponent(sender, new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "Syntax: " + ChatColor.RESET + ChatColor.RED + "/ABBA " + syntax));
    }

    public void printComponent(CommandSender sender, BaseComponent component) {
        sender.spigot().sendMessage(component);
    }

    public void broadcastComponentToList(List<Player> playerList, BaseComponent component) {
        for (Player p : playerList)
            p.spigot().sendMessage(component);
    }

    public void broadcastComponentGlobally(BaseComponent component) {
        broadcastComponentToList((List<Player>)Bukkit.getOnlinePlayers(), component);
    }

    public void printPlayerList(CommandSender sender, List<Player> party) {
        if (party.size() == 0)
            printComponent(sender, PARTY_EMPTY);
        else if (party.size() == 1)
            printComponent(sender, new TextComponent(ChatColor.YELLOW + party.get(0).getDisplayName()));
        else {
            String playerList = party.get(0).getDisplayName();
            for (int i = 1; i < party.size(); i++)
                playerList += ", " + party.get(i).getDisplayName();
            printComponent(sender, new TextComponent(ChatColor.GREEN + playerList));
        }
    }
}
