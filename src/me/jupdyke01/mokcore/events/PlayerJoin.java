package me.jupdyke01.mokcore.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import me.jupdyke01.mokcore.enums.Race;
import me.jupdyke01.mokcore.enums.Rank;
import me.jupdyke01.mokcore.enums.Skill;
import me.jupdyke01.mokcore.enums.Town;
import me.jupdyke01.mokcore.session.Session;
import me.jupdyke01.mokcore.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoin implements Listener {

	private Main main;

	public PlayerJoin(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!main.getCharacterManager().hasCharacter(e.getPlayer().getName())) {
			System.out.println("No Character: " + e.getPlayer().getName());
			ArrayList<Integer> stats = new ArrayList<>();
			stats.add(0);
			stats.add(0);
			stats.add(0);
			stats.add(0);
			stats.add(0);
			stats.add(0);
			HashMap<Skill, HashMap<Integer, Integer>> skills = new HashMap<>();
			HashMap<String, ArrayList<Session>> sessions = new HashMap<>();
			main.getCharacterManager().addCharacter(new Character(e.getPlayer().getName(), "Undefined", "Undefined", Race.Unselected, stats, Town.UNSELECTED, skills, 0, 0, 0, sessions));
		}
		if (!main.getUserManager().hasUser(e.getPlayer())) {
			ArrayList<Rank> ranks = new ArrayList<>();
			ranks.add(Rank.MEMBER);
			main.getUserManager().addUser(new User(e.getPlayer(), ranks, main));
		}
	}

	@EventHandler
	public void onJoinTown(PlayerJoinEvent e) {
		if (!main.getCharacterManager().hasCharacter(e.getPlayer().getName()) || main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().equals(Town.UNSELECTED)) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				public void run() {
					e.getPlayer().sendMessage(ChatColor.YELLOW + "         You have yet to select a city!");
					TextComponent townSelect = new TextComponent(ChatColor.YELLOW + "                Click to pick one");
					TextComponent base = new TextComponent("        ");
					TextComponent felrath = new TextComponent(ChatColor.GREEN + "  [" + ChatColor.BLUE + "Felrath" + ChatColor.GREEN + "]         ");
					felrath.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town fel"));
					TextComponent hus = new TextComponent(ChatColor.GREEN + "[" + ChatColor.GRAY + "Husvenstad" + ChatColor.GREEN + "]         ");
					hus.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town hus"));
					base.addExtra(felrath);
					base.addExtra(hus);
					e.getPlayer().spigot().sendMessage(townSelect);
					e.getPlayer().spigot().sendMessage(base);
				}
			}, 40L);
		} else if (main.getCharacterManager().hasCharacter(e.getPlayer().getName()) && !main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().equals(Town.UNSELECTED)){ 
			e.getPlayer().setPlayerListName(main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().getColor() + e.getPlayer().getName());
			e.getPlayer().setDisplayName(main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().getColor() + e.getPlayer().getName());
		} else {
			return;
		}
	}

	@EventHandler
	public void onJoinMessage(PlayerJoinEvent e) {
		e.setJoinMessage("");
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, 
				new Runnable() {
			@Override
			public void run() {
				String joinMessage = ChatColor.GRAY + e.getPlayer().getName() + " has joined the server!";
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!main.disabledGlobal.contains(p))
						p.sendMessage(joinMessage);
				}
			}
		}, 10);
	}

	@EventHandler
	public void onJoinVanish(PlayerJoinEvent e) {
		for (Player p : main.getUserManager().getVanished()) {
			e.getPlayer().hidePlayer(main, p);
		}
	}

	@EventHandler
	public void onJoinTime(PlayerJoinEvent e) {
		if (main.getCharacterManager().hasCharacter(e.getPlayer().getName())) {
			Character c = main.getCharacterManager().getCharacter(e.getPlayer().getName());
			c.setLastJoined(System.currentTimeMillis());
		}
	}

	/*	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoinPermissions(PlayerJoinEvent e) {
		if (main.getUserManager().getUser(e.getPlayer()) != null)
			main.getUserManager().getUser(e.getPlayer()).setPermissions();
	} */
}
