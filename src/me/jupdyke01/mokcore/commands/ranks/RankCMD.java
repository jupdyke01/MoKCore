package me.jupdyke01.mokcore.commands.ranks;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Rank;
import me.jupdyke01.mokcore.user.User;
import net.md_5.bungee.api.ChatColor;

public class RankCMD implements CommandExecutor {

	private Main main;

	public RankCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be a player to use this command!!");
			return true;
		}
		Player p = (Player) sender;
		User user = main.getUserManager().getUser(p);
		if (!main.getUserManager().hasUser(p)) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Unknown Error!");
			return true;
		}
		if (!user.isRankOrHigher(Rank.SRADMIN)) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You do not have access to this command!");
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter [set, add, remove, list]");
			return true;
		}
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length < 3) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			User target = main.getUserManager().getUser(args[1]);
			if (target == null) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid player!");
				return true;
			}
			ArrayList<Rank> ranks = new ArrayList<>();
			try {
				ranks.add(Rank.valueOf(args[2].toUpperCase()));
			} catch(Exception e) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Rank does not exist!");
				return true;
			}
			target.setRanks(ranks);
			target.setPermissions();
			p.sendMessage(ChatColor.GRAY + target.getOfflinePlayer().getName() + "'s rank is updated!");
		} else if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 3) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			User target = main.getUserManager().getUser(args[1]);
			if (target == null) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid player!");
				return true;
			}
			ArrayList<Rank> ranks = target.getRanks();
			try {
				ranks.add(Rank.valueOf(args[2].toUpperCase()));
			} catch(Exception e) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Rank does not exist!");
				return true;
			}
			target.setRanks(ranks);
			target.setPermissions();
			p.sendMessage(ChatColor.GRAY + target.getOfflinePlayer().getName() + "'s ranks are updated!");
		}

		return true;
	}



}
