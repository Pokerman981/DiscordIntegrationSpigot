/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/23/20, 1:56 PM
 * File: Main.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    public static Main instance;
    public static ConfigAccessor config;

    protected static JDA jda;

    @Override
    public void onEnable() {
        Main.instance = this;

        loadConfigurationFiles();
        Bukkit.getLogger().info("Loaded configuration files.");

        try {
            loadDiscordBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("Loaded Discord Bot.");

    }

    private void loadDiscordBot() throws LoginException {
        String token = Main.config.getConfig().getString("token");
        Validate.notNull(token);

        Main.jda = JDABuilder.createDefault(token).build();


    }

    private void loadConfigurationFiles() {
        Main.config = new ConfigAccessor(Main.instance, "config.yml");
    }


    @Override
    public void onDisable() {
        Main.jda.shutdown();
        Bukkit.getLogger().info("Shutting down discord bot");

    }

}

