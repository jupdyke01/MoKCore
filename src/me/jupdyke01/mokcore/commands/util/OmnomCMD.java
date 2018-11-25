package me.jupdyke01.mokcore.commands.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class OmnomCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("mokcore.user")) {
			p.setFoodLevel(p.getFoodLevel() - 4);
			p.sendMessage(ChatColor.GRAY + "You feel hungry now!");

			return true;
		}
		return true;
	}

}
