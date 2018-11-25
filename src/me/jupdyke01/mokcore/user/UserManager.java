package me.jupdyke01.mokcore.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Rank;

public class UserManager {

	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<Player> isVanished = new ArrayList<>();
	private HashMap<User, HashMap<Inventory, HashMap<String, ItemStack>>> buildmode = new HashMap<>();
	private Main main;
	
	public UserManager(Main main) {
		this.main = main;
	}
	
	
	public User getUser(OfflinePlayer p) {
		for (User user : users) {
			if (user.getOfflinePlayer().getUniqueId().equals(p.getUniqueId()))
				return user;
		}
		return null;
	}	

	public User getUser(UUID uuid) {
		for (User user : users) {
			if (user.getOfflinePlayer().getUniqueId().equals(uuid))
				return user;
		}
		return null;
	}
	
	public User getUser(String name) {
		for (User user : users) {
			if (user.getOfflinePlayer().getName().equals(name))
				return user;
		}
		return null;
	}

	public void addUser(User user) {
		users.add(user);
	}
	
	public ArrayList<Rank> getRanks(User user) {
		return user.getRanks();
	}
	
	public void saveUsers() {
		for (User user : users) {
			main.getConfig().set("users." + user.getOfflinePlayer().getUniqueId() + ".name", user.getOfflinePlayer().getName());
			List<String> rankStr = new ArrayList<>();
			for (Rank rank : user.getRanks()) {
				rankStr.add(rank.toString());
			}
			main.getConfig().set("users." + user.getOfflinePlayer().getUniqueId() + ".ranks", rankStr);

		}
		main.saveConfig();
	}
	
	public void loadUsers() {
		if (main.getConfig().contains("users")) {
			for (String uuid : main.getConfig().getConfigurationSection("users").getKeys(false)) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
				List<String> rankStr = main.getConfig().getStringList("users." + uuid + ".ranks");
				ArrayList<Rank> ranks = new ArrayList<>();
				for (String rankName : rankStr) {
					ranks.add(Rank.valueOf(rankName));
				}
				System.out.println("Added user: " + p.getName());
				addUser(new User(p, ranks, main));
			}
		}
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public void addVanished(Player p) {
		isVanished.add(p);
	}
	
	public void remVanished(Player p) {
		if (isVanished.contains(p))
			isVanished.remove(p);
	}
	
	public void addBuildMode(User user, Inventory inventory, HashMap<String, ItemStack> extra) {
		HashMap<Inventory, HashMap<String, ItemStack>> inv = new HashMap<>();
		inv.put(inventory, extra);
		buildmode.put(user, inv);
	}
	
	public void remBuildMode(User user) {
		buildmode.remove(user);
	}
	
	public HashMap<User, HashMap<Inventory, HashMap<String, ItemStack>>> getBuildMode() {
		return buildmode;
	}
	
	public ArrayList<Player> getVanished() {
		return isVanished;
	}
	
	public Boolean isVanished(Player p) {
		if (getVanished().contains(p))
			return true;
		return false;
	}
	
	public void setupPerms() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			getUser(p).setPermissions();
		}
	}
	
	public boolean hasUser(Player p) {
		for (User user : users) {
			if (user.getOfflinePlayer().getUniqueId().equals(p.getUniqueId()))
				return true;
		}
		return false;
	}
}
