package me.jupdyke01.mokcore.enums;

import org.bukkit.ChatColor;

public enum Rank {
	
	OWNER("&8&l[&dOwner&8&l]&r", new String[] {""}),
	COOWNER("&8&l[&5Co-Owner&8&l]&r", new String[] {""}),
	SRADMIN("&8&l[&4Sr. Admin&8&l]&r", new String[] {""}),
	ADMIN("&8&l[&4Admin&8&l]&r", new String[] {"coreprotect.*", "essentials.give", "essentials.invsee.modify", "essentials.gamemode.*", "essentials.ban", "essentials.banip", "essentials.unban", "essentials.unbanip", "minecraft.command.whitelist"}),
	MOD("&7&l[&eMod&7&l]&r", new String[] {"essentials.back", "essentials.tp", "essentials.tempban", "essentials.tphere", "essentials.tpo", "essentials.seen", "essentials.socialspy", "essentials.heal", "essentials.invsee", "mokcore.staff"}),
	JRMOD("&7&l[&eJr-Mod&7&l]&r", new String[] {"essentials.mute", "essentials.kick",}),
	DEV("&7&l[&aDev&7&l]&r", new String[] {""}),
	BUILDER("&7&l[&bBuilder&7&l]&r", new String[] {"worldedit.*", "headdb.*", ""}),
	EVENT("&7&l[&3Event&7&l]&r", new String[] {"essentials.warps.*", "essentials.warp.*", "essentails.fly", "essentials.time", "essentials.weather", "essentials.warp"}),
	LORE("&7&l[&2Lore&7&l]&r", new String[] {""}),
	MEMBER("&7&l[&8Member&7&l]&r", new String[] {"mokcore.user", "essentials.kit", "essentials.kits.fresh", "brewery.user", "essentials.rules", "essentials.msg", "essentials.list", "essentials.help", "essetials.afk", "essentials.user", "essentials.reply", "modifyworld.*"});

	
	String prefix;
	String[] perms;
	
	Rank(String prefix, String[] perms) {
		this.prefix = prefix;
		this.perms = perms;
	}
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}
	
	public String[] getPerms() {
		return perms;
	}
}
