package me.jupdyke01.mokcore.commands.item;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class RenameCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You must be a player to use this command!");
			return true;
		}
		
		Player p = (Player) sender;
		if (!(p.hasPermission("mokcore.staff"))) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You do not have access to this command!");
			return true;
		}
		
		String name = "";
		for (String str : args) {
			name = name + str + " ";
		}
		
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You must have an item in hand to use this!");
			return true;
		}
		
		ItemStack hand = p.getInventory().getItemInMainHand();
		ItemMeta meta = hand.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		hand.setItemMeta(meta);
		p.sendMessage(ChatColor.GRAY + "Your item has been renamed!");
		return true;
	}

}
