package me.jupdyke01.mokcore.commands.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Rank;
import me.jupdyke01.mokcore.user.User;
import net.md_5.bungee.api.ChatColor;

public class VanishCMD implements CommandExecutor {

	Main main;

	public VanishCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		User user = main.getUserManager().getUser(p);
		if (user == null) {
			p.sendMessage(ChatColor.RED + "Unknown Error");
			return true;
		}
		if (!user.isRankOrHigher(Rank.MOD) && !user.hasRank(Rank.BUILDER) && !user.hasRank(Rank.EVENT)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are not allowed to use this command!");
			return true;
		}
		
		if (args.length == 0) {
			if (main.getUserManager().getVanished().contains(p)) {
				main.getUserManager().getUser(p).setVanish(false);
				p.sendMessage(ChatColor.GRAY + "You are no longer vanished!");
			} else {
				main.getUserManager().getUser(p).setVanish(true);
				p.sendMessage(ChatColor.GRAY + "You are now vanished!");
			}
		}
		return true;
	}
}
