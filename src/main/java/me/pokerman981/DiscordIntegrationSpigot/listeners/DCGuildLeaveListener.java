/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/30/20, 2:30 AM
 * File: DCGuildLeaveListener.java
 * Project: DiscordIntegrationSpigot
 */

package me.pokerman981.DiscordIntegrationSpigot.listeners;

import me.pokerman981.DiscordIntegrationSpigot.Main;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DCGuildLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println("Guild leave");

        if (event.getUser().isBot()) return;
        System.out.println("Is not bot");
        if (!Main.accounts.getConfig().contains("accounts." + event.getUser().getId())) return;
        System.out.println("Has a account");

        String uuid = (String) Main.accounts.getConfig().get("accounts." + event.getUser().getId());
        Main.accounts.getConfig().set("accounts." + event.getUser().getId(), null);
        Main.accounts.save();

        Bukkit.getScheduler()
                .runTask(Main.instance, () -> Main.commandsToExecuteOnUnLink
                        .forEach(command -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", uuid))));
    }

}
