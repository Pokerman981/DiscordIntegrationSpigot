/*
 * Copyright (c) 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 8/23/20, 1:08 PM
 * File: Main.java
 * Project: DiscordBotSpigot
 */

package me.pokerman981.DiscordBotSpigot;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;
    public static ConfigAccessor config;

    @Override
    public void onEnable() {
        Main.instance = this;

        loadConfigurationFiles();

    }

    private void loadConfigurationFiles() {
        Main.config = new ConfigAccessor(Main.instance, "config.yml");
    }


    @Override
    public void onDisable() {

    }

}

