package me.jupdyke01.mokcore.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class ToggleGCMD implements CommandExecutor {

	private Main main;
	
	public ToggleGCMD(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " You must be a player to use this command!");
			return true;
		}
		
		Player p = (Player) sender;
		if (!main.disabledGlobal.contains(p)) {
			main.disabledGlobal.add(p);
			p.sendMessage(ChatColor.GRAY + "Global chat is now disabled, Use /toggleg again to enable it!");
			return true;
		}
			main.disabledGlobal.remove(p);
			p.sendMessage(ChatColor.GRAY + "Global chat is enabled again, Use /toggleg to disable it!");
			return true;
	}
}
