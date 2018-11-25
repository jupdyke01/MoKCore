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

public class FarmCMD implements CommandExecutor {

	Main main;
	Season season = Season.SPRING;

	public FarmCMD(Main main) {
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
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR || !p.getInventory().getItemInMainHand().getType().toString().contains("_") || !p.getInventory().getItemInMainHand().getType().toString().split("_")[1].toUpperCase().equals("HOE")) {
			p.sendMessage(ChatColor.RED + "You have to be holding a till to do this!");
			return true;
		}
		me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(p.getName());

		if (!target.getSkills().keySet().contains(Skill.FARMING)) {
			p.sendMessage(ChatColor.RED + "You must be a farmer to use this command!");
			return true;
		}

		if (p.getLocation().distance(new Location(p.getWorld(), 1697, 40, 2207)) > 10 && p.getLocation().distance(new Location(p.getWorld(), -1646, 37, 3582)) > 10) {
			p.sendMessage(ChatColor.RED + "You must be at the farming shack!");
			return true;
		}
		if (target.getSkillCooldown() > 0) {
			p.sendMessage(ChatColor.RED + "You can not do this again yet!");
			return true;
		}
		if ((p.getInventory().getItemInMainHand().getDurability() >= p.getInventory().getItemInMainHand().getType().getMaxDurability() * 0.75)) {
			p.sendMessage(ChatColor.RED + "Your till is too broken to do this!");
			return true;
		}
		int modifier = getModifier(p.getInventory().getItemInMainHand());
		modifier = modifier + target.getStatModifier(3);
		modifier = modifier + target.getStatModifier(4);

		int roll = 0;

		for (int i = 0; i < target.getDies(Skill.FARMING); i++) {
			roll = roll + Util.roll(20);
		}

		roll = roll + (modifier * target.getDies(Skill.FARMING));

		int wheat = roll/2;
		int potatoes = roll/4;
		int carrots = roll/4;
		int sugarcane = roll/8;
		int beets = roll/6;
		int melons = roll*2;
		int pumpkins = roll/4;
		int mushrooms = roll/8;

		// HERE
		int experienceGained;
		if (season == Season.SPRING) {
			p.getInventory().addItem(new ItemStack(Material.WHEAT, wheat * 6));
			p.getInventory().addItem(new ItemStack(Material.POTATO_ITEM, potatoes * 6));
			p.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, carrots * 6));
			p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, sugarcane));
			experienceGained = (int) (wheat*0.25 + carrots*0.5 + potatoes*0.5);
		} else if (season == Season.SUMMER) {			
			p.getInventory().addItem(new ItemStack(Material.WHEAT, wheat * 6));
			p.getInventory().addItem(new ItemStack(Material.POTATO_ITEM, potatoes * 6));
			p.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, carrots * 6));
			p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, sugarcane));
			p.getInventory().addItem(new ItemStack(Material.BEETROOT, beets));
			p.getInventory().addItem(new ItemStack(Material.MELON, melons));
			experienceGained = (int) (wheat*0.25 + carrots*0.5 + potatoes*0.5 + beets*2 + melons*0.125);

		} else if (season == Season.FALL) {
			p.getInventory().addItem(new ItemStack(Material.PUMPKIN, pumpkins));
			p.getInventory().addItem(new ItemStack(Material.RED_MUSHROOM, mushrooms));
			p.getInventory().addItem(new ItemStack(Material.BROWN_MUSHROOM, mushrooms));
			experienceGained = (int) (pumpkins*2 + mushrooms*3 + mushrooms*3);
		} else if (season == Season.WINTER) {
			p.getInventory().addItem(new ItemStack(Material.RED_MUSHROOM, mushrooms));
			p.getInventory().addItem(new ItemStack(Material.BROWN_MUSHROOM, mushrooms));
			experienceGained = (int) (mushrooms*3 + mushrooms*3);
		} else {
			experienceGained = 0;
		}

		if (target.getSkillExperience(Skill.FARMING) + experienceGained >= target.getExperienceRemaining(Skill.FARMING)) {
			target.setSkillLevel(Skill.FARMING, target.getSkillLevel(Skill.FARMING) + 1);
			p.sendMessage(ChatColor.GRAY + "You now feel more confident in this profession.");
			return true;
		} else {
			target.setSkillExperience(Skill.FARMING, target.getSkillExperience(Skill.FARMING) + experienceGained);
		}
		int cooldown = getRandom(43200,86400);
		target.setSkillCooldown(cooldown);
		if (cooldown > 64800) {
			if (roll >= target.getDies(Skill.FARMING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your farming trip was extremely successful but you are feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your farming trip was not very successful and you are feeling very drained.");
			}
		} else {
			if (roll >= target.getDies(Skill.FARMING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your farming trip was extremely successful and you aren't feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your farming trip was not very successful but you aren't feeling very drained.");
			}
		}
		//p.getInventory().getItemInMainHand().setDurability((short) (p.getInventory().getItemInMainHand().getDurability() + (p.getInventory().getItemInMainHand().getType().getMaxDurability() * .25)));
		return true;
	}

	public int getModifier(ItemStack item) {
		int modifier = 0;

		switch(item.getType()) {
		case WOOD_HOE:
			modifier = modifier-1;
			break;
		case STONE_HOE:
			modifier = 0;
			break;
		case IRON_HOE:
			modifier = modifier+1;
			break;
		case GOLD_HOE:
			modifier = modifier+2;
			break;
		case DIAMOND_HOE:
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
