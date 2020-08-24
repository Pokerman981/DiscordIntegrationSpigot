/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 5:42 PM
 * File: Utils.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static void msg(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static String getText(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void consoleCmd(String cmd) {
        getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
    }

    public static Player getPlayer(String target) {
        return target == null || Bukkit.getPlayer(target) == null || !Bukkit.getPlayer(target).isOnline() ? null : Bukkit.getPlayer(target);
    }

    public static Player getPlayer(UUID uuid) {
        return uuid == null || Bukkit.getPlayer(uuid) == null || !Bukkit.getPlayer(uuid).isOnline() ? null : Bukkit.getPlayer(uuid);
    }

    public static void broadcast(String message) {
        String formatted = getText(message);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(formatted);
        }
    }

    public static String getMeta(Player player, String key) {
        if (!player.isOnline()) return null;

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        ContextManager cm = LuckPermsProvider.get().getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);

        return metaData.getMetaValue(key);
    }

    public static String getPrefix(Player player) {
        if (!player.isOnline()) return null;

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        return LuckPermsProvider.get().getGroupManager().getGroup(user.getPrimaryGroup()).getDisplayName();
    }

}
