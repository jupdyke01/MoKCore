package me.jupdyke01.mokcore.commands.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CoinCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You must be a player to use this command!");
			return true;
		}

		Player p = (Player) sender;
		if (!(p.hasPermission("mokcore.coin"))) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You do not have access to this command!");
			return true;
		}

		if (args.length != 1) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " Syntax Error");
			return true;
		}
		int amount;
		try {
			amount = Integer.valueOf(args[0]);
		} catch(NumberFormatException e) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " Syntax Error");
			return true;
		}
		
		ItemStack item = new ItemStack(Material.IRON_NUGGET, amount);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		meta.setLocalizedName("Silver Coin");
		meta.setDisplayName(ChatColor.WHITE + "Munts");
		lore.add(ChatColor.GRAY + "A silver plated coin with a stamped image of three, four-pointed stars over a set of trees, resting behind a brook. This coin is the currency for all of Umamor.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		p.getInventory().addItem(item);
		p.updateInventory();
		return true;
	}

}
