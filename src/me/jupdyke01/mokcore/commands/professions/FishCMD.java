package me.jupdyke01.mokcore.commands.professions;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.Util;
import me.jupdyke01.mokcore.enums.Skill;
import net.md_5.bungee.api.ChatColor;

public class FishCMD implements CommandExecutor {

	Main main;

	public FishCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;

		if (!main.getCharacterManager().hasCharacter(p.getName())) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Unknown error has occured!");
			return true;
		}
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR || !p.getInventory().getItemInMainHand().getType().toString().contains("_") || !p.getInventory().getItemInMainHand().getType().toString().split("_")[1].toUpperCase().equals("ROD")) {
			p.sendMessage(ChatColor.RED + "You have to be holding a fishing rod to do this!");
			return true;
		}
		me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(p.getName());

		if (!target.getSkills().keySet().contains(Skill.FISHING)) {
			p.sendMessage(ChatColor.RED + "You must be a fisher to use this command!");
			return true;
		}

		if (p.getLocation().distance(new Location(p.getWorld(), -1726, 34, 3575)) > 10 && p.getLocation().distance(new Location(p.getWorld(), 1753, 32, 2206)) > 10) {
			p.sendMessage(ChatColor.RED + "You must be at the docks to use this!");
			return true;
		}
		if (target.getSkillCooldown() > 0) {
			p.sendMessage(ChatColor.RED + "You can not do this again yet!");
			return true;
		}
		if ((p.getInventory().getItemInMainHand().getDurability() >= p.getInventory().getItemInMainHand().getType().getMaxDurability() * 0.90)) {
			p.sendMessage(ChatColor.RED + "Your fishing rod is too broken to do this!");
			return true;
		}
		int modifier = getModifier(p.getInventory().getItemInMainHand());
		modifier = modifier + target.getStatModifier(0);
		modifier = modifier + target.getStatModifier(4);
		

		int roll = 0;

		for (int i = 0; i < target.getDies(Skill.FISHING); i++) {
			roll = roll + Util.roll(20);
		}

		roll = roll + (modifier * target.getDies(Skill.FISHING));
		
		int fish = roll/6;
		int salmon = roll/6;
		int pufferfish = roll/10;
		int clownfish = roll/10;
		
		// HERE
		int experienceGained = 0;
		
		p.getInventory().addItem(new ItemStack(Material.RAW_FISH, fish));
		p.getInventory().addItem(new ItemStack(Material.RAW_FISH, salmon, (short) 1));
		p.getInventory().addItem(new ItemStack(Material.RAW_FISH, clownfish, (short) 2));
		p.getInventory().addItem(new ItemStack(Material.RAW_FISH, pufferfish, (short) 3));
		experienceGained = experienceGained + fish*3 + salmon*3 + pufferfish*5 + clownfish*5;
		
		if (target.getSkillExperience(Skill.FISHING) + experienceGained >= target.getExperienceRemaining(Skill.FISHING)) {
			target.setSkillLevel(Skill.FISHING, target.getSkillLevel(Skill.FISHING) + 1);
			p.sendMessage(ChatColor.GRAY + "You now feel more confident in this profession.");
		} else {
			target.setSkillExperience(Skill.FISHING, target.getSkillExperience(Skill.FISHING) + experienceGained);
		}
		int cooldown = getRandom(43200,86400);
		target.setSkillCooldown(cooldown);
		if (cooldown > 64800) {
			if (roll >= target.getDies(Skill.FISHING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your fishing trip was extremely successful but you are feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your fishing trip was not very successful and you are feeling very drained.");
			}
		} else {
			if (roll >= target.getDies(Skill.FISHING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your fishing trip was extremely successful and you aren't feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your fishing trip was not very successful but you aren't feeling very drained.");
			}
		}
		//p.getInventory().getItemInMainHand().setDurability((short) (p.getInventory().getItemInMainHand().getDurability() + (p.getInventory().getItemInMainHand().getType().getMaxDurability() * .1)));
		return true;
	}

	public int getModifier(ItemStack item) {
		int modifier = 0;
		
		if (item.getEnchantments().containsKey(Enchantment.LURE)) {
			modifier = modifier + item.getEnchantmentLevel(Enchantment.LURE)*2;
		}
		if (item.getEnchantments().containsKey(Enchantment.LUCK)) {
			modifier = modifier + item.getEnchantmentLevel(Enchantment.LUCK)*2;
		}
		return modifier;
	}

	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
}
