package me.jupdyke01.mokcore.events;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.jupdyke01.mokcore.Main;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;

public class PacketListen {
	
	public PacketListen(Main main) {
		main.getProtocolManager().addPacketListener(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.ENTITY_ACTION) {
		    @Override
		    public void onPacketReceiving(PacketEvent e) {
		        if (e.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
		            PacketContainer packet = e.getPacket();
		            if (packet.getIntegers().read(1) == 0) {
		        		Player p = e.getPlayer();
		        		for (Player player : Bukkit.getOnlinePlayers()) {
		        			PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) p).getHandle(), 2);
		        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
		        		}
		            }
		        }
		    }
		});
	}
}
