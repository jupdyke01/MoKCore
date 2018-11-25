package me.jupdyke01.mokcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class Crafting implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemCraft(CraftItemEvent e) {
		if (e.getRecipe().getResult() != null && badItem(e.getRecipe().getResult().getType().toString().toLowerCase()))
			e.setCancelled(true);
	}
	
	
	private boolean badItem(String mat) {
		System.out.println(mat);
		if (mat.contains("helmet") || mat.contains("chestplate") || mat.contains("leggings") || mat.contains("boots") || mat.contains("axe") || mat.contains("spade") || mat.contains("sword") || mat.contains("bow") || mat.contains("hoe") || mat.contains("shield") || mat.contains("boat"))
			if (!mat.contains("wood") && !mat.contains("stone"))
				return true;
		return false;
	}
}
