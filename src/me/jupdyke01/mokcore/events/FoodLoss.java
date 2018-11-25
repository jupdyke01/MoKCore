package me.jupdyke01.mokcore.events;

import java.util.ArrayList;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLoss implements Listener {

	ArrayList<Player> food = new ArrayList<>();

	@EventHandler
	public void onFoodLoss(FoodLevelChangeEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			CraftPlayer player = (CraftPlayer) e.getEntity();
			if (food.contains(e.getEntity())) {
				player.setSaturation(player.getSaturation() + player.getSaturation() / 2);
				food.remove(e.getEntity());
			} else {
				food.add((Player) e.getEntity());
			}
		}
	}
}
