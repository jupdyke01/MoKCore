package me.jupdyke01.mokcore.commands.character;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import me.jupdyke01.mokcore.enums.Race;
import net.md_5.bungee.api.ChatColor;

public class RaceCMD implements CommandExecutor {

	Main main;

	public RaceCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (!(p.hasPermission("mokcore.staff"))) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be staff to use this command!");
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a player!");
			return true;
		} else if (args.length == 1) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a race!");
			return true;
		} else if (args.length >= 2) {
			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a valid player!");
				return true;
			}
			Character c = main.getCharacterManager().getCharacter(t.getName()); 
			if (Race.valueOf(args[1]) == null) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Invalid race!");
				return true;
			} else if (Race.valueOf(args[1]).equals(Race.Half_Elf)) {
				if (args.length < 4) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must enter a half elfs parents!");
					return true;
				} else {
					Race parent1;
					Race parent2;
					try {
						parent1 = Race.valueOf(args[2]);
						parent2 = Race.valueOf(args[3]);
					} catch(Exception e) {
						p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Parent's races are invalid!");
						return true;
					}
					c.setRace(Race.Half_Elf);
					c.fillStats(parent1, parent2);
				}
			} else {
				c.setRace(Race.valueOf(args[1]));
				c.fillStats();
			}
		}
		p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Race has been updated!");
		return true;
	}

}
