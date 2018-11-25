package me.jupdyke01.mokcore.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Dismount implements Listener {

	@EventHandler
	public void dismount(EntityDismountEvent e) {
		if (e.getDismounted() instanceof ArmorStand) {
			if (e.getDismounted().getCustomName().equals("Armor Seat " + e.getEntity().getName())) {
				e.getDismounted().remove();
			}
		}
	}
}
