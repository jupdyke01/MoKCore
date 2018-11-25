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
import me.jupdyke01.mokcore.enums.Season;
import me.jupdyke01.mokcore.enums.Skill;
import net.md_5.bungee.api.ChatColor;

public class MineCMD implements CommandExecutor {

	Main main;
	Season season = Season.SPRING;

	public MineCMD(Main main) {
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
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR || !p.getInventory().getItemInMainHand().getType().toString().contains("_") || !p.getInventory().getItemInMainHand().getType().toString().split("_")[1].toUpperCase().equals("PICKAXE")) {
			p.sendMessage(ChatColor.RED + "You have to be holding a pickaxe to do this!");
			return true;
		}
		me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(p.getName());

		if (!target.getSkills().keySet().contains(Skill.MINING)) {
			p.sendMessage(ChatColor.RED + "You must be a miner to use this command!");
			return true;
		}

		if (p.getLocation().distance(new Location(p.getWorld(), 1591, 39, 2267)) > 10 && p.getLocation().distance(new Location(p.getWorld(), -1541, 35, 3495)) > 10) {
			p.sendMessage(ChatColor.RED + "You must be at the mines to use this!");
			return true;
		}
		if (target.getSkillCooldown() > 0) {
			p.sendMessage(ChatColor.RED + "You can not do this again yet!");
			return true;
		}
		if ((p.getInventory().getItemInMainHand().getDurability() >= p.getInventory().getItemInMainHand().getType().getMaxDurability() * 0.75)) {
			p.sendMessage(ChatColor.RED + "Your pickaxe is too broken to do this!");
			return true;
		}
		int modifier = getModifier(p.getInventory().getItemInMainHand());
		modifier = modifier + target.getStatModifier(3);
		modifier = modifier + target.getStatModifier(4);

		int roll = 0;

		for (int i = 0; i < target.getDies(Skill.MINING); i++) {
			roll = roll + Util.roll(20);
		}

		roll = roll + (modifier * target.getDies(Skill.MINING));
		
		int cobblestone = 0;
		int coal = 0;
		int iron = 0;
		int gold = 0;
		int lapis = 0;
		int redstone = 0;
		
		// HERE
		int experienceGained = 0;
		coal = coal + Util.roll(3);
		iron = iron + Util.roll(3);
		if (roll >= 15) {
			cobblestone = cobblestone + Util.roll(5);
			coal = coal + Util.roll(7);
			iron = iron + Util.roll(5);
		}if (roll >= 35) {
			cobblestone = cobblestone + Util.roll(5);
			coal = coal + Util.roll(5);
			iron = iron + Util.roll(10);
		}if (roll >= 45) {
			cobblestone = cobblestone + Util.roll(10);
			coal = coal + Util.roll(10);
			iron = iron + Util.roll(5);
			gold = gold + Util.roll(5);
		}if (roll >= 80) {
			cobblestone = cobblestone + Util.roll(10);
			lapis = lapis + Util.roll(2);
			redstone = redstone + Util.roll(2);
			gold = gold + Util.roll(3);
		}if (roll >= 100) {
			cobblestone = cobblestone + Util.roll(10);
			lapis = lapis + Util.roll(3);
			redstone = redstone + Util.roll(3);
			gold = gold + Util.roll(5);
		}if (roll >= 120) {
			cobblestone = cobblestone + Util.roll(15);
			iron = iron + Util.roll(10);
			gold = gold + Util.roll(5);
		}if (roll >= 150) {
			cobblestone = cobblestone + Util.roll(15);
			// Gemstone
		}if (roll >= 200) {
			cobblestone = cobblestone + Util.roll(15);
			coal = coal + Util.roll(10);
			iron = iron + Util.roll(10);
			gold = gold + Util.roll(10);
			lapis = lapis + Util.roll(10);
			redstone = redstone + Util.roll(10);
		}if (roll >= 250) {
			cobblestone = cobblestone + Util.roll(15);
			// UI
		}
		
		p.getInventory().addItem(new ItemStack(Material.COBBLESTONE, cobblestone));
		p.getInventory().addItem(new ItemStack(Material.IRON_ORE, iron));
		p.getInventory().addItem(new ItemStack(Material.GOLD_ORE, gold));
		p.getInventory().addItem(new ItemStack(Material.INK_SACK, lapis, (short) 4));
		p.getInventory().addItem(new ItemStack(Material.REDSTONE, redstone));
		p.getInventory().addItem(new ItemStack(Material.COAL, coal));
		experienceGained = experienceGained + cobblestone + coal*2 + iron*3 + gold*4 + redstone*5 + lapis*5;
		// Gemstone 10
		// Rare 25
		
		if (target.getSkillExperience(Skill.MINING) + experienceGained >= target.getExperienceRemaining(Skill.MINING)) {
			target.setSkillLevel(Skill.MINING, target.getSkillLevel(Skill.MINING) + 1);
			p.sendMessage(ChatColor.GRAY + "You now feel more confident in this profession.");
			return true;
		} else {
			target.setSkillExperience(Skill.MINING, target.getSkillExperience(Skill.MINING) + experienceGained);
		}
		int cooldown = getRandom(43200,86400);
		target.setSkillCooldown(cooldown);
		if (cooldown > 64800) {
			if (roll >= target.getDies(Skill.MINING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your mining trip was extremely successful but you are feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your mining trip was not very successful and you are feeling very drained.");
			}
		} else {
			if (roll >= target.getDies(Skill.MINING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your mining trip was extremely successful and you aren't feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your mining trip was not very successful but you aren't feeling very drained.");
			}
		}
		//p.getInventory().getItemInMainHand().setDurability((short) (p.getInventory().getItemInMainHand().getDurability() + (p.getInventory().getItemInMainHand().getType().getMaxDurability() * .25)));
		return true;
	}

	public int getModifier(ItemStack item) {
		int modifier = 0;

		switch(item.getType()) {
		case WOOD_PICKAXE:
			modifier = modifier-1;
			break;
		case STONE_PICKAXE:
			modifier = 0;
			break;
		case IRON_PICKAXE:
			modifier = modifier+1;
			break;
		case GOLD_PICKAXE:
			modifier = modifier+2;
			break;
		case DIAMOND_PICKAXE:
			modifier = modifier+3;
			break;
		default:
			break;
		}

		if (item.getEnchantments().containsKey(Enchantment.DIG_SPEED)) {
			modifier = modifier + item.getEnchantmentLevel(Enchantment.DIG_SPEED);
		}
		if (item.getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
			modifier = modifier + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return modifier;
	}

	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}

}
