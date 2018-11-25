package me.jupdyke01.mokcore.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		//if (!e.getPlayer().hasPermission("mokcore.build"))
			if (e.getBlock() != null && badBlock(e.getBlock().getType())) {
				e.setCancelled(true);
			}
	}

	public boolean badBlock(Material mat) {
		if (mat.equals(Material.BEETROOT_BLOCK) || mat.equals(Material.SUGAR_CANE_BLOCK) || mat.equals(Material.WHEAT) || mat.equals(Material.CROPS) || mat.equals(Material.PUMPKIN) || mat.equals(Material.MELON_BLOCK) || mat.equals(Material.POTATO) || mat.equals(Material.CARROT))
			return true;
		return false;
	}

}
