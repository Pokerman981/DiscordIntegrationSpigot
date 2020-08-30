/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/30/20, 2:30 AM
 * File: MCDeluxeChatListener.java
 * Project: DiscordIntegrationSpigot
 */

package me.pokerman981.DiscordIntegrationSpigot.listeners;

import me.clip.deluxechat.events.DeluxeChatEvent;
import me.pokerman981.DiscordIntegrationSpigot.Main;
import me.pokerman981.DiscordIntegrationSpigot.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class MCDeluxeChatListener implements Listener {

    @EventHandler
    public void onChat(DeluxeChatEvent event) {

        String messageToSend = ((String) Main.messages.getOrDefault("mc-to-discord", "Config Error!"))
                .replaceAll("%prefix%", Objects.requireNonNull(Utils.getPrefix(event.getPlayer())))
                .replaceAll("%name%", event.getPlayer().getName())
                .replaceAll("%message%", event.getChatMessage())
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");

        Main.textChannels.forEach(textChannel -> textChannel.sendMessage(messageToSend).queue());

    }

}
