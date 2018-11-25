package me.jupdyke01.mokcore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Town;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Command implements Listener {
	
	Main main;
	
	public Command(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void preCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equals("/town fel")) {
			main.getCharacterManager().getCharacter(e.getPlayer().getName()).setTown(Town.FELRATH);
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + e.getPlayer().getName() + " has selected the city of Felrath!");
			e.getPlayer().sendMessage(ChatColor.GRAY + "Your city has been set to FELRATH");
			
			e.getPlayer().setBedSpawnLocation(new Location(e.getPlayer().getWorld(), 1873, 44, 2152, -88.8F, 0.0F));
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 1873, 44, 2152, -88.8F, 0.0F));
			e.getPlayer().setDisplayName(ChatColor.BLUE + e.getPlayer().getName());
			e.getPlayer().setPlayerListName(ChatColor.BLUE + e.getPlayer().getName());
			e.setCancelled(true);
			return;
		} else if (e.getMessage().equals("/town hus")) {
			main.getCharacterManager().getCharacter(e.getPlayer().getName()).setTown(Town.HUSVENSTAD);
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + e.getPlayer().getName() + " has selected the city of Husvenstad!");
			e.getPlayer().sendMessage(ChatColor.GRAY + "Your city has been set to HUSVENSTAD");
			
			e.getPlayer().setBedSpawnLocation(new Location(e.getPlayer().getWorld(), -1802, 40, 3595, -178F, 0.0F));
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -1802, 40, 3595, -178F, 0.0F));
			e.getPlayer().setDisplayName(ChatColor.GRAY + e.getPlayer().getName());
			e.getPlayer().setPlayerListName(ChatColor.GRAY + e.getPlayer().getName());
			e.setCancelled(true);
			return;
		} else if (e.getMessage().equals("/town")){
			e.getPlayer().sendMessage(ChatColor.YELLOW + "         You have yet to select a city!");
			
			// Town Select Base
			TextComponent townSelect = new TextComponent(ChatColor.YELLOW + "                Click to pick one");
			
			// Base
			TextComponent base = new TextComponent("        ");
			// Felrath select
			TextComponent felrath = new TextComponent(ChatColor.GREEN + "  [" + ChatColor.BLUE + "Felrath" + ChatColor.GREEN + "]         ");
			felrath.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town fel"));
			// Husvenstad select
			TextComponent hus = new TextComponent(ChatColor.GREEN + "[" + ChatColor.GRAY + "Husvenstad" + ChatColor.GREEN + "]         ");
			hus.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town hus"));
			
			base.addExtra(felrath);
			base.addExtra(hus);
			
			e.getPlayer().spigot().sendMessage(townSelect);
			e.getPlayer().spigot().sendMessage(base);
			e.setCancelled(true);
			return;
		}
	}
	
}
