package me.jupdyke01.mokcore.commands.stats;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import me.jupdyke01.mokcore.enums.Race;
import me.jupdyke01.mokcore.enums.Skill;
import me.jupdyke01.mokcore.enums.Town;
import me.jupdyke01.mokcore.session.Session;
import net.md_5.bungee.api.ChatColor;

public class StatsCMD implements CommandExecutor {

	private Main main;

	public StatsCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Invalid Syntax!");
			return true;
		}
		if (!sender.hasPermission("mokcore.staff")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}

		if (args[0].equalsIgnoreCase("town") || args[0].equalsIgnoreCase("towns")) {
			int husvenstad = 0;
			int felrath = 0;
			int unselected = 0;

			for (me.jupdyke01.mokcore.characters.Character c : main.getCharacterManager().getCharacters()) {
				if (c.getTown().equals(Town.FELRATH))
					felrath++;
				else if (c.getTown().equals(Town.HUSVENSTAD))
					husvenstad++;
				else
					unselected++;
			}
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-" + ChatColor.GREEN + "TOWN" + ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.BLUE + "Felrath: " + ChatColor.RED + felrath);
			sender.sendMessage(ChatColor.GRAY + "Husvenstad: " + ChatColor.RED + husvenstad);
			sender.sendMessage(ChatColor.WHITE + "Unselected: " + ChatColor.RED + unselected);
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			return true;
		} else if (args[0].equalsIgnoreCase("race") || args[0].equalsIgnoreCase("races")) {
			int human = 0;
			int highelf = 0;
			int woodelf = 0;
			int snowelf = 0;
			int bloodelf = 0;
			int halfelf = 0;
			int darkelf = 0;
			int dragonborn = 0;
			int dwarf = 0;
			int orc = 0;
			int unselected = 0;
			for (me.jupdyke01.mokcore.characters.Character c : main.getCharacterManager().getCharacters()) {
				if (c.getRace().equals(Race.Human)) {
					human++;
				} else if (c.getRace().equals(Race.High_Elf)) {
					highelf++;
				} else if (c.getRace().equals(Race.Wood_Elf)) {
					woodelf++;
				} else if (c.getRace().equals(Race.Snow_Elf)) {
					snowelf++;
				} else if (c.getRace().equals(Race.Blood_Elf)) {
					bloodelf++;
				} else if (c.getRace().equals(Race.Half_Elf)) {
					halfelf++;
				} else if (c.getRace().equals(Race.Dark_Elf)) {
					darkelf++;
				} else if (c.getRace().equals(Race.Dragonborn)) {
					dragonborn++;
				} else if (c.getRace().equals(Race.Dwarf)) {
					dwarf++;
				} else if (c.getRace().equals(Race.Orc)) {
					orc++;
				} else if (c.getRace().equals(Race.Unselected)) {
					unselected++;
				}
			}

			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-" + ChatColor.GREEN + "RACE" + ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.GRAY + "Human: " + ChatColor.RED + human);
			sender.sendMessage(ChatColor.GRAY + "High Elf: " + ChatColor.RED + highelf);
			sender.sendMessage(ChatColor.GRAY + "Wood Elf: " + ChatColor.RED + woodelf);
			sender.sendMessage(ChatColor.GRAY + "Snow Elf: " + ChatColor.RED + snowelf);
			sender.sendMessage(ChatColor.GRAY + "Blood Elf: " + ChatColor.RED + bloodelf);
			sender.sendMessage(ChatColor.GRAY + "Half Elf: " + ChatColor.RED + halfelf);
			sender.sendMessage(ChatColor.GRAY + "Dark Elf: " + ChatColor.RED + darkelf);
			sender.sendMessage(ChatColor.GRAY + "Dragonborn: " + ChatColor.RED + dragonborn);
			sender.sendMessage(ChatColor.GRAY + "Dwarf: " + ChatColor.RED + dwarf);
			sender.sendMessage(ChatColor.GRAY + "Orc: " + ChatColor.RED + orc);
			sender.sendMessage(ChatColor.GRAY + "Unselected: " + ChatColor.RED + unselected);
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		} else if (args[0].equalsIgnoreCase("played")) {
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "Please enter a player name!");
				return true;
			}
			Character c = main.getCharacterManager().getCharacter(args[1]);
			if (c == null) {
				sender.sendMessage(ChatColor.RED + args[1] + " is not a valid player!");
				return true;
			}
			//DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("M/d/y h:mm:ss a");
			ArrayList<String> dates = new ArrayList<>();
			dates.addAll(c.getSessions().keySet());

			int sevenDays = 0;
			if (dates.size() >= 7) {
				for (int i = 1; i < 8; i++) {
					for (Session session : c.getSessions().get(dates.get(dates.size() - i))) {
						sevenDays += session.getSessionLength() / 1000;
					}
				}
			} else {
				for (String date : dates) {
					for (Session session : c.getSessions().get(date)) {
						sevenDays += session.getSessionLength() / 1000;
					}
				}
			}

			int thirtyDays = 0;
			if (dates.size() >= 30) {
				for (int i = 1; i < 31; i++) {
					for (Session session : c.getSessions().get(dates.get(dates.size() - i))) {
						thirtyDays += session.getSessionLength() / 1000;
					}
				}
			} else {
				for (String date : dates) {
					for (Session session : c.getSessions().get(date)) {
						thirtyDays += session.getSessionLength() / 1000;
					}
				}
			}

			int lastSession = (int) c.getSessions().get(dates.get(dates.size() - 1)).get(c.getSessions().get(dates.get(dates.size() - 1)).size() - 1).getSessionLength() / 1000;

			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-" + ChatColor.GREEN + "PLAYED" + ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.GRAY + args[1] + " last logged in on: " + ChatColor.RED + formatDate(c.getLastJoined()));
			sender.sendMessage(ChatColor.GRAY + args[1] + " last logged off on: " + ChatColor.RED + formatDate(c.getLastLeft()));
			sender.sendMessage(ChatColor.GRAY + args[1] + "'s last session length was: " + ChatColor.RED + formatTime(lastSession));
			sender.sendMessage(ChatColor.GRAY + args[1] + "'s 7 day play time is: " + ChatColor.RED + formatTime(sevenDays));
			sender.sendMessage(ChatColor.GRAY + args[1] + "'s 30 day play time is: " + ChatColor.RED + formatTime(thirtyDays));
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		} else if (args[0].equalsIgnoreCase("prof")) {
			int mining = 0;
			int fishing = 0;
			int farming = 0;
			int woodcutting = 0;

			for (me.jupdyke01.mokcore.characters.Character c : main.getCharacterManager().getCharacters()) {
				if (c.getSkills().keySet().size() > 1)
					sender.sendMessage(ChatColor.RED + "Bad player: " + ChatColor.GRAY + c.getPname() + ChatColor.RED + "! contact Jupdyke01 about them.");
				else if (c.getSkills().keySet().contains(Skill.MINING))
					mining++;
				else if (c.getSkills().keySet().contains(Skill.FISHING))
					fishing++;
				else if (c.getSkills().keySet().contains(Skill.FARMING))
					farming++;
				else if (c.getSkills().keySet().contains(Skill.WOODCUTTING))
					woodcutting++;
			}
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-" + ChatColor.GREEN + "PROFS" + ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.GRAY + "Mining: " + ChatColor.RED + mining);
			sender.sendMessage(ChatColor.GRAY + "Fishing: " + ChatColor.RED + fishing);
			sender.sendMessage(ChatColor.GRAY + "Farming: " + ChatColor.RED + farming);
			sender.sendMessage(ChatColor.GRAY + "Woodcutting: " + ChatColor.RED + woodcutting);
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		} else if (args[0].equalsIgnoreCase("activity")) {

			HashMap<me.jupdyke01.mokcore.characters.Character, Integer> seven = new HashMap<>();
			HashMap<me.jupdyke01.mokcore.characters.Character, Integer> thirty = new HashMap<>();

			for (me.jupdyke01.mokcore.characters.Character c : main.getCharacterManager().getCharacters()) {
				ArrayList<String> dates = new ArrayList<>();
				dates.addAll(c.getSessions().keySet());
				int sevenDays = 0;
				if (dates.size() >= 7) {
					for (int i = 1; i < 8; i++) {
						for (Session session : c.getSessions().get(dates.get(dates.size() - i))) {
							sevenDays += session.getSessionLength() / 1000;
						}
					}
				} else {
					for (String date : dates) {
						for (Session session : c.getSessions().get(date)) {
							sevenDays += session.getSessionLength() / 1000;
						}
					}
				}
				seven.put(c, sevenDays);
				int thirtyDays = 0;
				if (dates.size() >= 30) {
					for (int i = 1; i < 31; i++) {
						for (Session session : c.getSessions().get(dates.get(dates.size() - i))) {
							thirtyDays += session.getSessionLength() / 1000;
						}
					}
				} else {
					for (String date : dates) {
						for (Session session : c.getSessions().get(date)) {
							thirtyDays += session.getSessionLength() / 1000;
						}
					}
				}
				thirty.put(c, thirtyDays);
			}

			List<Entry<me.jupdyke01.mokcore.characters.Character, Integer>> sevenSorted = new LinkedList<Entry<me.jupdyke01.mokcore.characters.Character, Integer>>(seven.entrySet());
			Collections.sort(sevenSorted, new Comparator<Entry<me.jupdyke01.mokcore.characters.Character, Integer>>() {
				@Override
				public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			}.reversed());

			List<Entry<me.jupdyke01.mokcore.characters.Character, Integer>> thirtySorted = new LinkedList<Entry<me.jupdyke01.mokcore.characters.Character, Integer>>(thirty.entrySet());
			Collections.sort(thirtySorted, new Comparator<Entry<me.jupdyke01.mokcore.characters.Character, Integer>>() {
				@Override
				public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			}.reversed());

			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-" + ChatColor.GREEN + "ACTIVITY" + ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=");
			sender.sendMessage(ChatColor.GRAY + "The 5 most active players for the last 7 days:");
			for (int i = 0; i < 5; i++) {
				if (sevenSorted.size() > i) {
					sender.sendMessage(ChatColor.GRAY + "" + (i + 1) + ": " + sevenSorted.get(i).getKey().getPname() + ": " + ChatColor.RED + formatTime(sevenSorted.get(i).getValue()));
				}
			}
			sender.sendMessage("");
			sender.sendMessage(ChatColor.GRAY + "The 5 most active players for the last 30 days:");
			for (int i = 0; i < 5; i++) {
				if (thirtySorted.size() > i) {
					sender.sendMessage(ChatColor.GRAY + "" + (i + 1) + ": " + thirtySorted.get(i).getKey().getPname() + ": " + ChatColor.RED + formatTime(thirtySorted.get(i).getValue()));
				}
			}
			sender.sendMessage(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		}
		return true;
	}

	public String formatDate(long d){
		LocalDateTime utcDate = Instant.ofEpochMilli(d).atZone(ZoneId.systemDefault()).toLocalDateTime();		
		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("MM/dd/yy 'at' hh:mm:ss a");
		return simpleDateFormat.format(utcDate);
	}

	public String formatTime(int totalSecs) {
		int hours = totalSecs / 3600;
		int minutes = (totalSecs % 3600) / 60;
		int seconds = totalSecs % 60;

		return String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);
	}

}
