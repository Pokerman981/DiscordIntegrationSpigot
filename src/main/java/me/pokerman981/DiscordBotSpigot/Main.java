/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/23/20, 10:04 PM
 * File: Main.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot;

import me.pokerman981.DiscordBotSpigot.listeners.DCMessageListener;
import me.pokerman981.DiscordBotSpigot.listeners.MCDeluxeChatListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    public static Main instance;
    public static ConfigAccessor config, linkData, accounts;


    protected static JDA jda;
    public static List<TextChannel> textChannels = new ArrayList<>();
    public static Map<String, Object> messages;
    protected static Guild guild;

    @Override
    public void onEnable() {
        Main.instance = this;

        loadConfigurationFiles();
        Bukkit.getLogger().info("Loaded configuration files.");

        loadConfigurationValues();
        Bukkit.getLogger().info("Loaded configuration values.");

        registerListeners();
        Bukkit.getLogger().info("Registered Listeners.");

        try {
            loadDiscordBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("Loaded Discord Bot.");

        loadChannels();


    }

    private void loadChannels() { // TODO Make this more dynamic
        String guild = Validate.notNull(Main.config.getConfig().getString("guilds.guild"), "Unable to find guild id"),
                global = Validate.notNull(Main.config.getConfig().getString("channels.global-channel"), "Unable to find global channel id"),
                staff = Validate.notNull(Main.config.getConfig().getString("channels.staff-channel"), "Unable to find staff channel id");

        Guild loadGuild = Validate.notNull(Main.jda.getGuildById(guild), "Failed to find specified guild");
        TextChannel loadGlobalChannel = Validate.notNull(loadGuild.getTextChannelById(staff), "Failed to find specified global channel");
        TextChannel loadStaffChannel = Validate.notNull(loadGuild.getTextChannelById(staff), "Failed to find specified staff channel");

        Bukkit.getLogger().info("Loaded guild " + loadGuild.getName());
        Bukkit.getLogger().info("Loaded global channel: " + loadGlobalChannel.getName() + " (" + global + ")");
        Bukkit.getLogger().info("Loaded staff channel: " + loadStaffChannel.getName() + " (" + staff + ")");

        Main.guild = loadGuild;
        Main.textChannels.add(loadGlobalChannel);
        Main.textChannels.add(loadStaffChannel);
    }

    private void loadDiscordBot() throws LoginException {
        String token = Main.config.getConfig().getString("token");
        Validate.notNull(token);

        Main.jda = JDABuilder.createDefault(token)
                .addEventListeners(new DCMessageListener())
                .build();
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new MCDeluxeChatListener(), instance);
    }

    private void loadConfigurationFiles() {
        Main.config = new ConfigAccessor(Main.instance, "config.yml");
        Main.linkData = new ConfigAccessor(Main.instance, "linkData.yml");
        Main.accounts = new ConfigAccessor(Main.instance, "accounts.yml");
    }

    private void loadConfigurationValues() {
        messages = Validate.notNull(Main.config.getConfig().getConfigurationSection("messages").getValues(false));
    }

    @Override
    public void onDisable() {
        Main.jda.shutdown();
        Bukkit.getLogger().info("Shutting down discord bot");

    }

}

