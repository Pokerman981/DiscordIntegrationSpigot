/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 1:57 AM
 * File: DCMessageListener.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.listeners;

import me.pokerman981.DiscordBotSpigot.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DCMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (!event.isFromType(ChannelType.TEXT)) return;

        TextChannel textChannel = event.getTextChannel();

        if (!Main.textChannels.contains(textChannel)) return;

        System.out.println("Someone wrote in a server chat");
    }

}
