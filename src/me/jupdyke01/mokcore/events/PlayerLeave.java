package me.jupdyke01.mokcore.events;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.characters.Character;
import net.md_5.bungee.api.ChatColor;

public class PlayerLeave implements Listener {

	private Main main;

	public PlayerLeave(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onLeaveMessage(PlayerQuitEvent e) {
		e.setQuitMessage("");
		String quitMessage = ChatColor.GRAY + e.getPlayer().getName() + " has left the server!";
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!main.disabledGlobal.contains(p))
				p.sendMessage(quitMessage);
		}
	}

	@EventHandler
	public void onLeaveVanish(PlayerQuitEvent e) {
		if (main.getUserManager().isVanished(e.getPlayer())) {
			main.getUserManager().remVanished(e.getPlayer());
		}
	}

	@EventHandler
	public void onLeaveBuild(PlayerQuitEvent e) {
		if (main.getUserManager().getBuildMode().containsKey(main.getUserManager().getUser(e.getPlayer())))
			main.getUserManager().getUser(e.getPlayer()).setBuildMode(false);
	}

	@EventHandler
	public void onLeaveTime(PlayerQuitEvent e) {
		if (main.getCharacterManager().hasCharacter(e.getPlayer().getName())) {
			Character c = main.getCharacterManager().getCharacter(e.getPlayer().getName());
			c.setLastLeft(System.currentTimeMillis());
			c.addSession();
			
		}
	}

	public String formatDate(long d){
		System.out.println(d);
		LocalDateTime utcDate = Instant.ofEpochMilli(d).atZone(ZoneId.systemDefault()).toLocalDateTime();		
		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("MM-dd-yy");
		return simpleDateFormat.format(utcDate);
	}
}
