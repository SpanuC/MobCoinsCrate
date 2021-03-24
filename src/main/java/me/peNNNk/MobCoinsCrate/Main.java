package me.peNNNk.MobCoinsCrate;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import me.peNNNk.MobCoinsCrate.Events.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import java.io.*;

public class Main extends JavaPlugin
{
    private static Main instance;
    
    public void onEnable() {
        if (Main.instance == null) {
            Main.instance = this;
        }
        this.getConfig().options().copyDefaults(false);
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents((Listener)new ClickEvent(), (Plugin)this);
        if (Bukkit.getPluginManager().getPlugin("MobCoinsPlus") == null) {
            System.out.println("[MobCoinsCrate] Couldn't find MobCoinsPlus. This plugin depends on MobCoinsPlus!");
            System.out.println("[MobCoinsCrate] Plugin disabled successfully!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    
    public void onDisable() {
        this.getConfig().options().copyDefaults(false);
        if (this.exists("config.yml")) {
            this.saveDefaultConfig();
        }
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    private boolean exists(final String file) {
        final File file2 = new File(File.separator + this.getDescription().getName() + File.separator + file);
        return file2.exists();
    }
}
