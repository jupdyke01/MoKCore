package me.jupdyke01.mokcore.commands.character;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import net.md_5.bungee.api.ChatColor;

public class CharacterCMD implements CommandExecutor {

	private Main main;

	public CharacterCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be a player to use this command!");
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("mokcore.user")) {
				if (main.getCharacterManager().hasCharacter(p.getName())) {
					Character c = main.getCharacterManager().getCharacter(p.getName());
					p.sendMessage(ChatColor.GREEN + "==========" + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "SHEET" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + "==========");
					p.sendMessage(ChatColor.DARK_GRAY + "Player Name: " + ChatColor.RED + c.getPname());
					p.sendMessage(" ");
					p.sendMessage(ChatColor.DARK_GRAY + "Character Name: " + ChatColor.RED + c.getName());
					p.sendMessage(" ");
					p.sendMessage(ChatColor.DARK_GRAY + "Your Race: " + ChatColor.RED + c.getRace().toString().replace("_", " "));
					p.sendMessage(" ");
					p.sendMessage(ChatColor.RED + c.getDesc());
					p.sendMessage(" ");
					p.sendMessage(ChatColor.DARK_GRAY + "STR: " + ChatColor.RED + c.getStats().get(0));
					p.sendMessage(ChatColor.DARK_GRAY + "DEX: " + ChatColor.RED + c.getStats().get(1));
					p.sendMessage(ChatColor.DARK_GRAY + "CON: " + ChatColor.RED + c.getStats().get(2));
					p.sendMessage(ChatColor.DARK_GRAY + "INT: " + ChatColor.RED + c.getStats().get(3));
					p.sendMessage(ChatColor.DARK_GRAY + "WIS: " + ChatColor.RED + c.getStats().get(4));
					p.sendMessage(ChatColor.DARK_GRAY + "CHA: " + ChatColor.RED + c.getStats().get(5));
					p.sendMessage(" ");
					p.sendMessage(ChatColor.GREEN + "===========================");
				} else {
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You do not have a character sheet!");
				}
			}
		} else {
			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " The player " + ChatColor.BOLD + args[0] + ChatColor.RESET + ChatColor.RED + ChatColor.ITALIC + " does not exist!");
				return true;
			}
			if (main.getCharacterManager().hasCharacter(t.getName())) {
				Character c = main.getCharacterManager().getCharacter(t.getName());
				sender.sendMessage(ChatColor.GREEN + "==========" + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "SHEET" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + "==========");
				sender.sendMessage(ChatColor.DARK_GRAY + "Player Name: " + ChatColor.RED + c.getPname());
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_GRAY + "Character Name: " + ChatColor.RED + c.getName());
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.RED + c.getDesc());
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_GRAY + "STR: " + ChatColor.RED + c.getStats().get(0));
				sender.sendMessage(ChatColor.DARK_GRAY + "DEX: " + ChatColor.RED + c.getStats().get(1));
				sender.sendMessage(ChatColor.DARK_GRAY + "CON: " + ChatColor.RED + c.getStats().get(2));
				sender.sendMessage(ChatColor.DARK_GRAY + "INT: " + ChatColor.RED + c.getStats().get(3));
				sender.sendMessage(ChatColor.DARK_GRAY + "WIS: " + ChatColor.RED + c.getStats().get(4));
				sender.sendMessage(ChatColor.DARK_GRAY + "CHA: " + ChatColor.RED + c.getStats().get(5));
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.GREEN + "===========================");
			} else {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "They do not have a character sheet!");
			}

			return true;
		}
		return true;
	}

}
