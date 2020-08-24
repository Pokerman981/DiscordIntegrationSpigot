/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/23/20, 3:30 PM
 * File: DCMessageListener.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.listeners;

import me.pokerman981.DiscordBotSpigot.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class DCMessageListener extends ListenerAdapter {

    @SubscribeEvent
    public void onMessageReceived(GenericGuildMessageEvent event) {
        TextChannel sentTextChannel = event.getChannel();

        if (!Main.textChannels.contains(sentTextChannel)) return;

        System.out.println("Who sent a message?");


    }

}
