/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 8:18 PM
 * File: MCDiscordCommand.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.commands;

import me.pokerman981.DiscordBotSpigot.Main;
import me.pokerman981.DiscordBotSpigot.Utils;
import net.dv8tion.jda.api.entities.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Random;

public class MCDiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length <= 0) {
            Utils.msg(commandSender, (String) Main.messages.getOrDefault("usage", "Config Error!"));
            return true;
        }

        if (strings[0].equalsIgnoreCase("invite")) {
            String messageToSend = (String) Main.messages.getOrDefault("invite", "Config Error!");
            String urlToSend = (String) Main.messages.getOrDefault("invite-url", "Config Error!");

            TextComponent invite = new TextComponent(Utils.getText(
                    messageToSend.replaceAll("%invite%", urlToSend)));

            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, urlToSend);
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.getText("&bClick Join!")).create());

            invite.setClickEvent(clickEvent);
            invite.setHoverEvent(hoverEvent);

            commandSender.sendMessage(invite);
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

            // Lol could just not format the string to not have leading zeros oh well
            String pin = String.format("%06d", new Random().nextInt(1000000)).replaceAll("^0+(?!$)", "");

            ConfigurationSection userLinkingData = Main.linkData.getConfig().createSection("linkData." + player.getUniqueId().toString());
            userLinkingData.set("MC-username", player.getName());
            userLinkingData.set("pin", pin);
            userLinkingData.set("request-time", Instant.now().getEpochSecond());

            Main.linkData.getConfig().set("hash." + pin, player.getUniqueId().toString());
            Main.linkData.save();

            Utils.msg(player, ((String) Main.messages.getOrDefault("linking", "Config Error!")).replaceAll("%pin%", pin));
        }

        return true;
    }
}
