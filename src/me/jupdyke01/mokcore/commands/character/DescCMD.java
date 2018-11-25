package me.jupdyke01.mokcore.commands.character;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class DescCMD implements CommandExecutor {

	Main main;

	public DescCMD(Main main) {
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
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a description!");
				return true;
			}
			String desc = "";
			for (String str : args) {
				desc = desc + str + " ";
			}
			main.getCharacterManager().getCharacter(p.getName()).setDesc(desc);
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Your character description has been updated!");
			return true;
		}
		return true;
	}
}
