/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/24/20, 5:33 PM
 * File: MCDeluxeChatListener.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot.listeners;

import me.clip.deluxechat.events.DeluxeChatEvent;
import me.pokerman981.DiscordBotSpigot.Main;
import me.pokerman981.DiscordBotSpigot.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class MCDeluxeChatListener implements Listener {

    @EventHandler
    public void onChat(DeluxeChatEvent event) {

        String messageToSend = ((String) Main.messages.getOrDefault("mc-to-discord", "Config Error!"))
                .replaceAll("%prefix%", Objects.requireNonNull(Utils.getPrefix(event.getPlayer())))
                .replaceAll("%name%", event.getPlayer().getName())
                .replaceAll("%message%", event.getChatMessage());

        Main.textChannels.forEach(textChannel -> textChannel.sendMessage(messageToSend).queue());

    }

}
