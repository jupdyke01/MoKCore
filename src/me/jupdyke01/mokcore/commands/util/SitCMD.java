package me.jupdyke01.mokcore.commands.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.jupdyke01.mokcore.Main;
import net.md_5.bungee.api.ChatColor;

public class SitCMD implements CommandExecutor {

	Main main;

	public SitCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You must be a player to use this command!");
			return true;
		}

		Player p = (Player) sender;
		if (p.hasPermission("mokcore.user")) {
			if (p.isOnGround()) {
				net.minecraft.server.v1_12_R1.Entity armorNMS = ((CraftWorld) p.getWorld()).createEntity(p.getLocation().subtract(0, 1.7, 0), ArmorStand.class);			
				armorNMS.setInvisible(true);
				armorNMS.setSilent(true);

				Entity armor = (ArmorStand) ((CraftWorld) p.getWorld()).addEntity(armorNMS, SpawnReason.CUSTOM);
				armor.setInvulnerable(true);
				armor.addPassenger(p);
				armor.setGravity(false);
				armor.setCustomName("Armor Seat " + p.getName());

				return true;
			}
			return true;
		}
		return true;
	}
}
