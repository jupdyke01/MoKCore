package me.jupdyke01.mokcore.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener {

	Main main;

	public ChatListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (e.getPlayer().getWorld().equals(p.getWorld()))
				if (e.getPlayer().getLocation().distance(p.getLocation()) <= 12) {
					if (main.getCharacterManager().hasCharacter(e.getPlayer().getName())) {
						String prefix = e.getPlayer().getDisplayName().split("]")[0] + "]";
						String msg = e.getMessage();
						msg = msg.replaceAll("\\*", ChatColor.YELLOW + "");
						msg = msg.replaceAll("\"", ChatColor.WHITE + "\"");
						if(e.getMessage().startsWith("((")) {
							p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "L" + ChatColor.GRAY + "] " + prefix + " " + main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().getColor() + main.getCharacterManager().getCharacter(e.getPlayer().getName()).getName() + ChatColor.RESET + ": " + ChatColor.DARK_GRAY + msg);
							continue;
						}
						p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "L" + ChatColor.GRAY + "] " + prefix + " " + main.getCharacterManager().getCharacter(e.getPlayer().getName()).getTown().getColor() + main.getCharacterManager().getCharacter(e.getPlayer().getName()).getName() + ChatColor.RESET + ": " + ChatColor.GRAY + msg);
						continue;
					}
				}
		}
	}
}
