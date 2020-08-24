/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/23/20, 10:04 PM
 * File: MCLinkCommand.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.commands;

import me.pokerman981.DiscordBotSpigot.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCLinkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            Utils.msg(commandSender, "&cThis a player only command!");
            return true;
        }


        return false;
    }
}
