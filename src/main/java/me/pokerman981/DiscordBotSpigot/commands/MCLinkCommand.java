/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 12:31 AM
 * File: MCLinkCommand.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.commands;

import me.pokerman981.DiscordBotSpigot.Main;
import me.pokerman981.DiscordBotSpigot.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Random;

public class MCLinkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            Utils.msg(commandSender, "&cThis a player only command!");
            return true;
        }

        Player player = (Player) commandSender;

        boolean isCurrentlyLinked = Main.accounts.getConfig().isConfigurationSection(player.getUniqueId().toString());
        if (isCurrentlyLinked) {
            Utils.msg(commandSender, "");
            return true;
        }

        boolean isCurrentlyLinking = Main.linkData.getConfig().isConfigurationSection(player.getUniqueId().toString());
        if (isCurrentlyLinking) {
            Utils.msg(commandSender, (String) Main.messages.getOrDefault("already-linking", "Config Error!"));
            return true;
        }

        String pin = String.format("%06d", new Random().nextInt(1000000));

        ConfigurationSection userLinkingData = Main.linkData.getConfig().getConfigurationSection("linkData." + player.getUniqueId().toString());
        userLinkingData.set("MC-username", player.getName());
        userLinkingData.set("pin", pin);
        userLinkingData.set("request-time", Instant.now());

        Main.linkData.getConfig().getConfigurationSection("hash").set(pin, player.getUniqueId().toString());

        Utils.msg(player, (String) Main.messages.getOrDefault("linking", "Config Error!"));

        return false;
    }
}
