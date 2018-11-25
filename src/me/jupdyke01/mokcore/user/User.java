package me.jupdyke01.mokcore.user;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Rank;

public class User {

	private OfflinePlayer p;
	private ArrayList<Rank> ranks;
	private Main main;

	public User(OfflinePlayer p, ArrayList<Rank> ranks, Main main) {
		this.p = p;
		this.ranks = ranks;
		this.main = main;
	}

	public ArrayList<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(ArrayList<Rank> ranks) {
		this.ranks = ranks;
	}

	public OfflinePlayer getOfflinePlayer() {
		return p;
	}

	public void setOfflinePlayer(OfflinePlayer player) {
		this.p = player;
	}

	public boolean hasRank(Rank rank) {
		if (getRanks().contains(rank))
			return true;
		return false;
	}


	public void setVanish(boolean option) {
		if (option == true) {
			if (p.isOnline()) {
				Player p = this.p.getPlayer();
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 900000000, 1, false));
				main.getUserManager().addVanished(p);
				for (Player online : Bukkit.getOnlinePlayers()) {
					online.hidePlayer(main, p);
				}
			}
		} else {
			if (p.isOnline()) {
				Player p = this.p.getPlayer();
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				main.getUserManager().remVanished(p);
				for (Player online : Bukkit.getOnlinePlayers()) {
					online.showPlayer(main, p);
				}
			}
		}
	}

	public void setBuildMode(boolean o) {
		if (o == true) {
			if (p.isOnline()) {
				setVanish(true);
				Player p = this.p.getPlayer();
				Inventory inventory = Bukkit.createInventory(null, 36);
				inventory.setContents(p.getInventory().getStorageContents());
				HashMap<String, ItemStack> extra = new HashMap<>();
				if (p.getInventory().getHelmet() != null) {
					extra.put("helmet", p.getInventory().getHelmet());
				}
				if (p.getInventory().getChestplate() != null) {
					extra.put("chestplate", p.getInventory().getChestplate());
				}
				if (p.getInventory().getLeggings() != null) {
					extra.put("leggings", p.getInventory().getLeggings());
				}
				if (p.getInventory().getBoots() != null) {
					extra.put("boots", p.getInventory().getBoots());
				}
				if (p.getInventory().getItemInOffHand() != null) {
					extra.put("offhand", p.getInventory().getItemInOffHand());
				}

				main.getUserManager().addBuildMode(this, inventory, extra);
				p.getInventory().clear();
				ItemStack axe = new ItemStack(Material.WOOD_AXE, 1);
				p.getInventory().addItem(axe);
				p.updateInventory();
				p.setGameMode(GameMode.CREATIVE);
			}
		} else if (o == false) {
			if (main.getUserManager().getBuildMode().containsKey(this))
				if (p.isOnline()) {
					setVanish(false);
					Player p = this.p.getPlayer();
					ArrayList<Inventory> contents = new ArrayList<>();
					contents.addAll(main.getUserManager().getBuildMode().get(this).keySet());
					ArrayList<HashMap<String, ItemStack>> extraHash = new ArrayList<>();
					extraHash.addAll(main.getUserManager().getBuildMode().get(this).values());
					HashMap<String, ItemStack> extra = extraHash.get(0);
					p.getInventory().setContents(contents.get(0).getContents());
					if (extra.containsKey("helmet")) {
						p.getInventory().setHelmet(extra.get("helmet"));
					}
					if (extra.containsKey("chestplate")) {
						p.getInventory().setChestplate(extra.get("chestplate"));
					}
					if (extra.containsKey("leggings")) {
						p.getInventory().setLeggings(extra.get("leggings"));
					}
					if (extra.containsKey("boots")) {
						p.getInventory().setBoots(extra.get("boots"));
					}
					if (extra.containsKey("offhand")) {
						p.getInventory().setItemInOffHand(extra.get("offhand"));
					}

					p.updateInventory();
					main.getUserManager().remBuildMode(this);
					p.setGameMode(GameMode.SURVIVAL);
				}
		}
	}

	public boolean getBuildMode() {
		return main.getUserManager().getBuildMode().containsKey(this);
	}

	public boolean isRankOrHigher(Rank r) {
		for (Rank rank : ranks) {
			if (rank.ordinal() <= r.ordinal()) {
				return true;
			}
			if (p.isOnline() && p.getPlayer().isOp()) {
				return true;
			}
		}
		return false;
	}

	public Rank getHighestRank() {
		Rank highest = Rank.MEMBER;
		for (Rank rank : getRanks()) {
			if (rank.ordinal() < highest.ordinal())
				highest = rank;
		}
		return highest;
	}

	public ArrayList<Rank> getInheritance() {
		Rank highest = getHighestRank();
		ArrayList<Rank> inheritance = new ArrayList<>();
		for (Rank rank : Rank.values()) {
			if (highest.ordinal() - 1 < rank.ordinal())
				inheritance.add(rank);
		}
		return inheritance;
	}
	
	public ArrayList<Rank> notAllowed() {
		ArrayList<Rank> notAllowed = new ArrayList<>();
		for (Rank rank : Rank.values()) {
			if (rank.ordinal() < getHighestRank().ordinal())
				notAllowed.add(rank);
		}
		return notAllowed;
	}

	public void setPermissions() {
		if (p.isOnline()) {
			PermissionAttachment attachment = p.getPlayer().addAttachment(main);
			for (Rank rank : notAllowed()) {
				for (String permission : rank.getPerms()) {
					attachment.setPermission(permission, false);
				}
			}
			for (Rank rank : getInheritance()) {
				for (String permission : rank.getPerms()) {
					attachment.setPermission(permission, true);
				}
			}
		}
	}
}
