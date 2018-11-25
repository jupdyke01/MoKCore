package me.jupdyke01.mokcore.enums;

import net.md_5.bungee.api.ChatColor;

public enum Town {
	UNSELECTED(ChatColor.WHITE),
	DROWELFCAMP(ChatColor.RED),
	FELRATH(ChatColor.BLUE),
	HUSVENSTAD(ChatColor.GRAY);
	
	private ChatColor color;
	
	Town(ChatColor color) {
		this.color = color;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
	
}
