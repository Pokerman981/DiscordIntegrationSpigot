/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 5:46 PM
 * File: DCMessageListener.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.listeners;

import me.pokerman981.DiscordBotSpigot.Main;
import me.pokerman981.DiscordBotSpigot.Utils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.UUID;

public class DCMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (!event.isFromType(ChannelType.TEXT)) return;
        if (event.getAuthor().isBot()) return;

        TextChannel textChannel = event.getTextChannel();

        if (!Main.textChannels.contains(textChannel)) return;
        String messageToSend;

        if (Main.accounts.getConfig().contains("accounts." + event.getAuthor().getId())) {
            messageToSend = ((String) Main.messages.getOrDefault("discord-to-mc", "Config Error!"))
                    .replaceAll("%username%", Bukkit.getServer().getPlayer(UUID.fromString((String) Main.accounts.getConfig().get("accounts." + event.getAuthor().getId()))).getName())
                    .replaceAll("%message%", event.getMessage().getContentDisplay());
        } else {
            messageToSend = ((String) Main.messages.getOrDefault("discord-to-mc", "Config Error!"))
                    .replaceAll("%username%", event.getAuthor().getName())
                    .replaceAll("%message%", event.getMessage().getContentDisplay());
        }

        Bukkit.broadcastMessage(Utils.getText(messageToSend));
    }

}
