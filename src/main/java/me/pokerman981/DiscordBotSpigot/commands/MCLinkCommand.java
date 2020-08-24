/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 4:59 PM
 * File: MCLinkCommand.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.commands;

import me.pokerman981.DiscordBotSpigot.Main;
import me.pokerman981.DiscordBotSpigot.Utils;
import net.dv8tion.jda.api.entities.User;
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

        if (strings.length <= 0) {
            return false;
        }

        if (strings[0].equalsIgnoreCase("link")) {
            if (!(commandSender instanceof Player)) {
                Utils.msg(commandSender, "&cThis a player only command!");
                return true;
            }

            Player player = (Player) commandSender;

            boolean isCurrentlyLinked = Main.accounts.getConfig()
                    .getConfigurationSection("accounts")
                    .getValues(false)
                    .containsValue(player.getUniqueId().toString());

            if (isCurrentlyLinked) {
                String discordID = Main.accounts.getConfig()
                        .getConfigurationSection("accounts")
                        .getValues(false).entrySet().stream()
                        .filter(stringObjectEntry -> stringObjectEntry.getValue().equals(player.getUniqueId().toString()))
                        .findFirst().get().getKey();

                User user = Main.jda.retrieveUserById(discordID).complete();

                Utils.msg(commandSender, ((String) Main.messages
                        .getOrDefault("already-linked", "Config Error!"))
                        .replaceAll("%discordname%", user.getName() + "#" + user.getDiscriminator()));
                return true;
            }

            boolean isCurrentlyLinking = Main.linkData.getConfig().contains("linkData." + player.getUniqueId().toString());
            if (isCurrentlyLinking) {
                String pin = (String) Main.linkData.getConfig().get("linkData." + player.getUniqueId().toString() + ".pin");
                Utils.msg(commandSender, ((String) Main.messages.getOrDefault("already-linking", "Config Error!")).replaceAll("%pin%", pin));
                return true;
            }

            String pin = String.format("%06d", new Random().nextInt(1000000)).replaceAll("^0+(?!$)", "");

            ConfigurationSection userLinkingData = Main.linkData.getConfig().createSection("linkData." + player.getUniqueId().toString());
            userLinkingData.set("MC-username", player.getName());
            userLinkingData.set("pin", pin);
            userLinkingData.set("request-time", Instant.now().getEpochSecond());

            Main.linkData.getConfig().set("hash." + pin, player.getUniqueId().toString());
            Main.linkData.save();

            Utils.msg(player, ((String) Main.messages.getOrDefault("linking", "Config Error!")).replaceAll("%pin%", pin));
        }

        return false;
    }
}
