package me.jupdyke01.mokcore.commands.character;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jupdyke01.mokcore.enums.Race;
import net.md_5.bungee.api.ChatColor;

public class RacesCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		for (Race race : Race.values()) {
			sender.sendMessage(ChatColor.GRAY + race.toString());
		}
		
		return true;
	}

}
