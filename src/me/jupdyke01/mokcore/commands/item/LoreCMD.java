package me.jupdyke01.mokcore.commands.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class LoreCMD implements CommandExecutor  {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be a player to use this command!");
			return true;
		}

		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must do either add or remove!");
			return true;
		}
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be holding an item to use this command!");
			return true;
		}
		if (!p.hasPermission("mokcore.staff")) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to use this command!");
			return true;
		}
		if (args[0].equalsIgnoreCase("add")) {
			String loreString = ChatColor.translateAlternateColorCodes('&', "");
			for (int i = 1; i < args.length; i++) {
				loreString = loreString + args[i] + " ";
			}
			ItemStack item = p.getInventory().getItemInMainHand();
			ArrayList<String> lore = new ArrayList<>();
			ItemMeta meta = item.getItemMeta();
			if (item.hasItemMeta() && item.getItemMeta().hasLore())
				lore.addAll(item.getItemMeta().getLore());
			lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
			System.out.println(lore);
			meta.setLore(lore);
			item.setItemMeta(meta);
			p.sendMessage(ChatColor.GRAY + "Lore line added!");
			return true;
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length != 2) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a line number!");
				return true;
			}
			int line;
			try {
				line = Integer.valueOf(args[1]);
			} catch(NumberFormatException e) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a line number!");
				return true;
			}
			ItemStack item = p.getInventory().getItemInMainHand();
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();
			if (item.hasItemMeta() && item.getItemMeta().hasLore())
				lore.addAll(item.getItemMeta().getLore());
			if (lore.size() != 0 && lore.size() >= line && line > 0) {
				lore.remove(line - 1);
			} else {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid line number!");
				return true;
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			p.sendMessage(ChatColor.GRAY + "Lore line removed!");
			return true;
		}
		return true;
	}
}
