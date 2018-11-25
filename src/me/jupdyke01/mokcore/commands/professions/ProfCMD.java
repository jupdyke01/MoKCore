package me.jupdyke01.mokcore.commands.professions;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Skill;
import net.md_5.bungee.api.ChatColor;

public class ProfCMD implements CommandExecutor {

	Main main;

	public ProfCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a player!");
				return true;
			}
			if (!sender.hasPermission("mokcore.prof")) {
				return true;
			}
			Player p = (Player) sender;
			if (!main.getCharacterManager().hasCharacter(p.getName())) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player does not exist!");
				return true;
			}
			me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(p.getName());
			p.sendMessage(ChatColor.GRAY + "==================" + "PROFESSIONS" + "==================");
			if (target.getSkills() != null && target.getSkills().size() > 0)
			for (Skill skill : target.getSkills().keySet()) {
				p.sendMessage(ChatColor.AQUA + skill.toString() + ": " + ChatColor.GREEN + target.getSkills().get(skill));
			}
			p.sendMessage(ChatColor.GRAY + "===============================================");
			return true;
		}
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 3) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			if (!sender.hasPermission("mokcore.prof.edit")) {
				return true;
			}
			if (!main.getCharacterManager().hasCharacter(args[1])) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player does not exist!");
				return true;
			}
			me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(args[1]);
			if (target.getSkills().size() >= 3) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "That player has the max amount of professions!");
				return true;
			}
			Skill skill;
			try {
				skill = Skill.valueOf(args[2].toUpperCase());
			} catch(Exception e) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Profession does not exist!");
				return true;
			}
			HashMap<Integer, Integer> level = new HashMap<>();
			level.put(1, 0);
			target.getSkills().put(skill, level);	
			sender.sendMessage(ChatColor.GRAY + target.getPname() + "'s " + skill.toString() + " profession has been added");
		}
		if (args[0].equalsIgnoreCase("remove")) {
			if (args.length < 3) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			if (!sender.hasPermission("mokcore.prof.edit")) {
				return true;
			}
			if (!main.getCharacterManager().hasCharacter(args[1])) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player does not exist!");
				return true;
			}
			me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(args[1]);
			if (target.getSkills().size() <= 0) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The player does not have any professions!");
				return true;
			}
			Skill skill;
			try {
				skill = Skill.valueOf(args[2].toUpperCase());
			} catch(Exception e) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Profession does not exist!");
				return true;
			}
			if (!target.getSkills().containsKey(skill)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The player does not have that profession!");
				return true;
			}
			target.getSkills().remove(skill);
			sender.sendMessage(ChatColor.GRAY + target.getPname() + "'s " + skill.toString() + " profession has been removed");
		}
		if (args[0].equalsIgnoreCase("level")) {
			if (args.length < 4) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			if (!sender.hasPermission("mokcore.prof.edit")) {
				return true;
			}
			if (!main.getCharacterManager().hasCharacter(args[1])) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player does not exist!");
				return true;
			}
			me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(args[1]);
			if (target.getSkills().size() <= 0) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The player does not have any professions!");
				return true;
			}
			int level;
			try {
				level = Integer.valueOf(args[3]);
			} catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + args[3] + " is not an integer!");
				return true;
			}
			Skill skill;
			try {
				skill = Skill.valueOf(args[2].toUpperCase());
			} catch(Exception e) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Profession does not exist!");
				return true;
			}
			if (!target.getSkills().containsKey(skill)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The player does not have that profession!");
				return true;
			}
			sender.sendMessage(ChatColor.GRAY + target.getPname() + "'s " + skill.toString() + " profession has been set to level " + level);
			HashMap<Integer, Integer> levelHash = new HashMap<>();
			levelHash.put(level, 0);
			target.getSkills().put(skill, levelHash);
		}
		if (args[0].equalsIgnoreCase("reset")) {
			if (!sender.hasPermission("mokcore.reset")) {
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Command Syntax!");
				return true;
			}
			if (!sender.hasPermission("mokcore.prof.edit")) {
				return true;
			}
			if (!main.getCharacterManager().hasCharacter(args[1])) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player does not exist!");
				return true;
			}
			me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(args[1]);
			target.setSkillCooldown(0);
		}
		return true;
	}
}
