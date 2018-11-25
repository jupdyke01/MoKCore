package me.jupdyke01.mokcore.commands.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;

public class LayCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + " You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		for (Player player : Bukkit.getOnlinePlayers()) {
			p.getWorld().getBlockAt(p.getLocation()).setType(Material.BED_BLOCK);
			((CraftPlayer) p).sendBlockChange(p.getLocation(), Material.BED_BLOCK, (byte) 1);
			PacketPlayOutBed packet = new PacketPlayOutBed(((CraftPlayer) p).getHandle(), new BlockPosition(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
			
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			System.out.println("packet sent to" + player.getName());
		}
		return true;
	}

}
