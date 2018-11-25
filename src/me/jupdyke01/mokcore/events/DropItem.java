package me.jupdyke01.mokcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class DropItem implements Listener {

	private Main main;
	
	public DropItem(Main main) {
		this.main = main;
	}
	
	@EventHandler 
	public void onItemDrop(PlayerDropItemEvent e) {
		if (main.getUserManager().getBuildMode().containsKey(main.getUserManager().getUser(e.getPlayer()))) {
			e.getPlayer().sendMessage(ChatColor.RED + "You can not drop items in build mode!");
			e.setCancelled(true);
		}
	}
}
