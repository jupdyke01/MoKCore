package me.jupdyke01.mokcore.commands.character;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class NameCMD implements CommandExecutor {

	Main main;
	
	public NameCMD(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a name!");
			return true;
		}
		String name = "";
		for (String str : args) {
			name = name + str + " ";
		}
		name = name.substring(0, name.length() - 1);
		main.getCharacterManager().getCharacter(p.getName()).setName(name);
		p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Your character name has been updated!");
		return true;
	}

}
