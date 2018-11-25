package me.jupdyke01.mokcore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Town;

public class Respawn implements Listener {

	private Main main;
	
	public Respawn(Main main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e){
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				if (main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().equals(Town.FELRATH)) {
					e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 1873, 44, 2152, -88.8F, 0.0F));
				} else if (main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().equals(Town.HUSVENSTAD)) {
					e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -1802, 40, 3595, -178F, 0.0F));
				}
			}
		}, 5);
    }
	
}
