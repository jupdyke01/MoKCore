package me.jupdyke01.mokcore.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {

	@EventHandler
	public void onFishingInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onBlockInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
			if (e.getClickedBlock() != null && (e.getClickedBlock().getType().equals(Material.ANVIL) || e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE))) {
				e.setCancelled(true);
			}
	}

}
