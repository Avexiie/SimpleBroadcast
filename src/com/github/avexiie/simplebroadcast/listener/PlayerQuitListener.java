package com.github.avexiie.simplebroadcast.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.avexiie.simplebroadcast.Main;
import com.github.avexiie.simplebroadcast.broadcasts.Broadcast;
import com.github.avexiie.simplebroadcast.broadcasts.BroadcastStatus;

public class PlayerQuitListener implements Listener {
	
	/*
	 * (non-Javadoc)
	 * @see net.simplebroadcast.broadcasts.Broadcast
	 */
	private Broadcast broadcast = new Broadcast();
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		/* Checks if chat broadcast status is not disabled. */
		if (Broadcast.getChatBroadcastStatus() == BroadcastStatus.DISABLED) {
			return;
		}
		/* TODO Comment */
		if (Main.getInstance().getConfig().getBoolean("chat.requireOnlinePlayers") && Bukkit.getServer().getOnlinePlayers().size() == 1) {
			broadcast.cancelChatBroadcast();
			Broadcast.setChatBroadcastStatus(BroadcastStatus.WAITING);
		}
	}
}