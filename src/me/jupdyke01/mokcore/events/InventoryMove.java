package me.jupdyke01.mokcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

import me.jupdyke01.mokcore.Main;

public class InventoryMove implements Listener {

	private Main main;

	public InventoryMove(Main main) {
		this.main = main;
	}

	@EventHandler
	public void inventoryMove(InventoryPickupItemEvent e) {
		Player p = (Player) e.getInventory().getHolder();
		if (p != null && main.getUserManager().getBuildMode().containsKey(main.getUserManager().getUser(p)))
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void inventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (p != null && main.getUserManager().getBuildMode().containsKey(main.getUserManager().getUser(p))) {
			if (e.getClickedInventory() != p.getInventory())
				e.setCancelled(true);
			if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
				e.setCancelled(true);
		}
	}

}
