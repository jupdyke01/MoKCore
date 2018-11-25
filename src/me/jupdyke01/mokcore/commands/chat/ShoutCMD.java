package me.jupdyke01.mokcore.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class ShoutCMD implements CommandExecutor {

	Main main;

	public ShoutCMD(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("mokcore.user")) {
			if (args.length < 1) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must put in a message!");
				return true;
			}
			String msg = "";
			for (String str : args) {
				msg = msg + str + " ";
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (p.getPlayer().getWorld().equals(p.getWorld()))
					if (player.getLocation().distance(p.getLocation()) <= 25) {
						String prefix = p.getDisplayName().split("]")[0] + "]";
						//String prefix = main.getUserManager().getUser(p).getHighestRank().getPrefix();
						msg = msg.replaceAll("\\*", ChatColor.YELLOW + "");
						msg = msg.replaceAll("\"", ChatColor.WHITE + "\"");
						player.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "S" + ChatColor.GRAY + "] " + prefix + " " + main.getCharacterManager().getCharacter(p.getName()).getTown().getColor() + main.getCharacterManager().getCharacter(p.getName()).getName() + ChatColor.RESET + ": " + ChatColor.GRAY + msg);
					}
			}
			return true;
		}
		return true;
	}
}
