package me.jupdyke01.mokcore.commands.util;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ScanCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("admin.admin")) {
			return true;
		}
		if (!(sender instanceof Player)) {
			return true;
		}

		Player p = (Player) sender;
		for (Chunk chunk : p.getWorld().getLoadedChunks()) {
			int bx = chunk.getX()<<4;
			int bz = chunk.getZ()<<4;
			for(int xx = bx; xx < bx+16; xx++) {
				for(int zz = bz; zz < bz+16; zz++) {
					for(int yy = 0; yy < 128; yy++) {
						if (p.getWorld().getBlockAt(xx,yy,zz).getType() == Material.CHEST) {
							Chest chest = (Chest) p.getWorld().getBlockAt(xx, yy, zz).getState();
							if (chest.getBlockInventory().contains(Material.WHEAT) || chest.getBlockInventory().contains(Material.CARROT_ITEM) || chest.getBlockInventory().contains(Material.POTATO_ITEM)) {
								int food = 0;
								for (ItemStack item : chest.getBlockInventory().getContents()) {
									if (item != null && item.getType() != null && item.getType() != Material.AIR)
										if (item.getType().equals(Material.WHEAT) || item.getType().equals(Material.BREAD) || item.getType().equals(Material.POTATO_ITEM) || item.getType().equals(Material.CARROT_ITEM) || item.getType().equals(Material.RAW_CHICKEN) || item.getType().equals(Material.RAW_FISH) || item.getType().equals(Material.RAW_BEEF) || item.getType().equals(Material.PORK))
											food += item.getAmount();
								}
								if (food >= 256) {
									p.sendMessage(ChatColor.RED + "Food chest Found at: " + xx + "/" + yy + "/" + zz);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

}
