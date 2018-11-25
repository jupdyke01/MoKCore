package me.jupdyke01.mokcore.events;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class PlayerMove implements Listener {

	private HashMap<Player, Pair<Integer, Boolean>> waiting = new HashMap<>();
	private Main main;

	public PlayerMove(Main main) {
		this.main = main;
		timer();
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location hus = new Location(e.getPlayer().getWorld(), -1762, 31, 3561);
		Location fel = new Location(e.getPlayer().getWorld(), 1806, 33, 2191);

		if (e.getTo().distance(hus) < 5 && !(e.getFrom().distance(hus) < 5)) {
			e.getPlayer().sendMessage(ChatColor.GRAY + "The ship will leave in 1 minute.");
			Pair<Integer, Boolean> info = Pair.of(60, true);
			waiting.put(e.getPlayer(), info);
		} else if (e.getTo().distance(fel) < 5 && !(e.getFrom().distance(fel) < 5)) {
			e.getPlayer().sendMessage(ChatColor.GRAY + "The ship will leave in 1 minute.");
			Pair<Integer, Boolean> info = Pair.of(60, false);
			waiting.put(e.getPlayer(), info);
		} else {
			if (waiting.containsKey(e.getPlayer())) {
				if (e.getTo().distance(hus) < 5 || e.getTo().distance(fel) < 5)
					return;
				waiting.remove(e.getPlayer());
				e.getPlayer().sendMessage(ChatColor.RED + "You have moved out of the teleport zone!");
			}
		}
	}

	public void timer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			@Override
			public void run() {
				if (!waiting.isEmpty()) {
					for (Player p : waiting.keySet()) {
						if (waiting.get(p).getLeft() >= 1) {
							Pair<Integer, Boolean> info = Pair.of(waiting.get(p).getLeft() - 1, waiting.get(p).getRight());
							waiting.put(p, info);
							if (info.getLeft() % 10 == 0)
								p.sendMessage(ChatColor.GRAY + "You have " + info.getLeft() + " seconds left!");
						} else {
							if (waiting.get(p).getRight().equals(true)) {
								p.teleport(new Location(p.getWorld(), 1808, 33, 2191), TeleportCause.PLUGIN);
								p.sendMessage(ChatColor.GRAY + "You have arrived at Felrath!");
								waiting.remove(p);
							} else {
								p.teleport(new Location(p.getWorld(), -1765, 33, 3561), TeleportCause.PLUGIN);
								p.sendMessage(ChatColor.GRAY + "You have arrived at Husvenstad!");
								waiting.remove(p);
							}
						}
					}
				}
			}
		}, 0L, 20L);
	}

}
