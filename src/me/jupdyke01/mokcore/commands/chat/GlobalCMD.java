package me.jupdyke01.mokcore.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class GlobalCMD implements CommandExecutor {

	Main main;

	public GlobalCMD(Main main) {
		this.main = main;
	}

	@Override
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
				String prefix = p.getDisplayName().split("]")[0] + "]";
				//String prefix = main.getUserManager().getUser(p).getHighestRank().getPrefix();
				if (!main.disabledGlobal.contains(player))
					player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "G" + ChatColor.GRAY + "] " + prefix + " " + main.getCharacterManager().getCharacter(p.getName()).getTown().getColor() + p.getName() + ChatColor.RESET + ": " + msg);
			}
			return true;
		}
		return false;
	}
}
