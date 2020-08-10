package com.github.avexiie.simplebroadcast;

import java.io.File;  

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.avexiie.simplebroadcast.broadcasts.Broadcast;
import com.github.avexiie.simplebroadcast.commands.BroadcastCommand;
import com.github.avexiie.simplebroadcast.commands.BroadcastTabCompleter;
import com.github.avexiie.simplebroadcast.listener.PlayerJoinListener;
import com.github.avexiie.simplebroadcast.listener.PlayerQuitListener;
import com.github.avexiie.simplebroadcast.util.IgnoreManager;
import com.github.avexiie.simplebroadcast.util.MessageManager;

public class Main extends JavaPlugin {
	
	/*
	 * (non-Javadoc)
	 * @see net.simplebroadcast.broadcasts.Broadcast
	 * @see net.simpleboradcast.util.IgnoreManager
	 * @see net.simplebroadcast.util.MessageManager
	 * @see net.simplebroadcast.util.MetricsData
	 * @see net.simplebroadcast.util.UpdateManager
	 */
	private Broadcast broadcast = new Broadcast();
	private IgnoreManager ignoreManager = new IgnoreManager();
	private MessageManager messageManager = new MessageManager();
	//private MetricsUtil metricsUtil = new MetricsUtil();
	
	/**
	 * Instance of Main class.
	 */
	private static Main instance = null;
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		instance = this;
		
		/* Saves all required resources. */
		saveDefaultConfig();
		if (!new File(getDataFolder(), "bossbar.yml").exists()) {
			saveResource("bossbar.yml", false);			
		}
		if (!new File(getDataFolder(), "ignore.yml").exists()) {
			saveResource("ignore.yml", false);			
		}
		saveResource("readme.txt", true);
		
		/* Sets command executor and tab completer. */
		getCommand("simplebroadcast").setExecutor(new BroadcastCommand());
		getCommand("simplebroadcast").setTabCompleter(new BroadcastTabCompleter());
		
		/* Registers event listeners. */
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		
		/* Loads chat and boss bar messages and permissions as well as chat prefix and suffix. */
		messageManager.loadAll();
		
		/* Loads chat and boss bar ignore list. */
		ignoreManager.loadChatIgnoreList();
		ignoreManager.loadBossBarIgnoreList();
		
		/* Starts broadcast(s) after checking their status. */
		broadcast.broadcast();
		
		/* Generates data for http://mcstats.org/plugin/SimpleBroadcast. */
		// TODO Activate later to prevent plugin from creating any unwanted data.
		//metricsUtil.generateData();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		/* Cancels broadcasts. */
		broadcast.cancelChatBroadcast();
		broadcast.cancelBossBarBroadcast();
	}
	
	/**
	 * Returns initialized instance of Main class.
	 * 
	 * @return the instance
	 */
	public static Main getInstance() {
		return instance;
	}
	
	/**
	 * Returns boss bar config file.
	 * 
	 * @return the bossBarConfig
	 */
	public FileConfiguration getBossBarConfig() {
		File bossBarConfigFile = new File(this.getDataFolder(), "bossbar.yml");
		FileConfiguration bossBarConfig = YamlConfiguration.loadConfiguration(bossBarConfigFile);
		return bossBarConfig;
	}
	
	/**
	 * Returns ignore config file.
	 * 
	 * @return the ignoreConfig
	 */
	public FileConfiguration getIgnoreConfig() {
		File ignoreConfigFile = new File(this.getDataFolder(), "ignore.yml");
		FileConfiguration ignoreConfig = YamlConfiguration.loadConfiguration(ignoreConfigFile);
		return ignoreConfig;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#getFile()
	 */
	@Override
	public File getFile() {
		return super.getFile();
	}
}