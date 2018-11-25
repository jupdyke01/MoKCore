package me.jupdyke01.mokcore.commands.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import net.md_5.bungee.api.ChatColor;

public class RollCMD implements CommandExecutor {
	Random r;
	Main main;
	public RollCMD(Main main) {
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		r = new Random();
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (p.hasPermission("mokcore.user")) {
			if (args.length == 0) {
				sendAreaMessage(p, ChatColor.GRAY + p.getName() + " rolled " + (r.nextInt(20) + 1) + "/" + "20", 12);
				return true;
			} else {
				try {
					int roll = Integer.valueOf(args[0]);
					sendAreaMessage(p, ChatColor.GRAY + p.getName() + " rolled " + (r.nextInt(roll) + 1) + "/" + roll, 12);
					return true;
				} catch (NumberFormatException nfe) {
					Character c = main.getCharacterManager().getCharacter(p.getName());
					switch(args[0].toLowerCase()) {
					case "str":
						int random = (r.nextInt(20) + 1); 
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s strength roll was a " + (random + ((c.getStats().get(0) / 2) - 5)), 12);
						break;
					case "dex":
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s Your dexterity roll was a " + ((r.nextInt(20) + 1) + ((c.getStats().get(1) / 2) - 5)), 12);
						break;
					case "con":
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s Your consitution roll was a " + ((r.nextInt(20) + 1) + ((c.getStats().get(2) / 2) - 5)), 12);
						break;
					case "int":
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s Your intelligence roll was a " + ((r.nextInt(20) + 1) + ((c.getStats().get(3) / 2) - 5)), 12);
						break;
					case "wis":
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s Your wisdom roll was a " + ((r.nextInt(20) + 1) + ((c.getStats().get(4) / 2) - 5)), 12);
						break;
					case "cha":
						sendAreaMessage(p, ChatColor.GRAY + p.getName() + "'s Your charisma roll was a " + ((r.nextInt(20) + 1) + ((c.getStats().get(5) / 2) - 5)), 12);
						break;
					}
				}
			}
		}
		return true;
	}
	public void sendAreaMessage(Player p, String message, int range) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getLocation().distance(p.getLocation()) <= range) {
				player.sendMessage(message);
			}
		}
	}
}
