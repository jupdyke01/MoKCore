package me.jupdyke01.mokcore.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class MeCMD implements CommandExecutor {

	private Main main;

	public MeCMD(Main main) {
		this.main = main;
	} 

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("mokcore.user")) {
			String msg = "";
			for (String str : args) {
				msg = msg + str + " ";
			}

			for (Player local : Bukkit.getOnlinePlayers()) {
				String prefix = p.getDisplayName().split("]")[0] + "]";
				//String prefix = main.getUserManager().getUser(p).getHighestRank().getPrefix();
				if (local.getPlayer().getWorld().equals(p.getWorld()))
					if (local.getPlayer().getLocation().distance(p.getLocation()) <= 12) {
						local.sendMessage(prefix + " " + main.getCharacterManager().getCharacter(p.getName()).getTown().getColor() + main.getCharacterManager().getCharacter(p.getName()).getName() + ChatColor.RESET + " " + ChatColor.YELLOW +  msg);
					}
			}
			return true;
		}
		return true;
	}
}
