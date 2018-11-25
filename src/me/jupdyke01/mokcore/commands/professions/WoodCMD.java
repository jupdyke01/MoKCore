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

public class WoodCMD implements CommandExecutor {

	Main main;

	public WoodCMD(Main main) {
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
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR || !p.getInventory().getItemInMainHand().getType().toString().contains("_") || !p.getInventory().getItemInMainHand().getType().toString().split("_")[1].toUpperCase().equals("AXE")) {
			p.sendMessage(ChatColor.RED + "You have to be holding an axe to do this!");
			return true;
		}
		me.jupdyke01.mokcore.characters.Character target = main.getCharacterManager().getCharacter(p.getName());

		if (!target.getSkills().keySet().contains(Skill.WOODCUTTING)) {
			p.sendMessage(ChatColor.RED + "You must be a woodcutter to use this command!");
			return true;
		}

		if (p.getLocation().distance(new Location(p.getWorld(), 1634, 37, 2308)) > 10 && p.getLocation().distance(new Location(p.getWorld(), -1467, 41, 3598)) > 10) {
			p.sendMessage(ChatColor.RED + "You must be at the wood cutting shack!");
			return true;
		}
		if (target.getSkillCooldown() > 0) {
			p.sendMessage(ChatColor.RED + "You can not do this again yet!");
			return true;
		}
		if ((p.getInventory().getItemInMainHand().getDurability() >= p.getInventory().getItemInMainHand().getType().getMaxDurability() * 0.75)) {
			p.sendMessage(ChatColor.RED + "Your axe is too broken to do this!");
			return true;
		}
		int modifier = getModifier(p.getInventory().getItemInMainHand());
		modifier = modifier + target.getStatModifier(0);
		modifier = modifier + target.getStatModifier(1);

		int roll = 0;

		for (int i = 0; i < target.getDies(Skill.WOODCUTTING); i++) {
			roll = roll + Util.roll(20);
		}

		int logs = (roll + target.getDies(Skill.WOODCUTTING) * modifier) / 2;
		p.getInventory().addItem(new ItemStack(Material.LOG, logs));

		int cooldown = getRandom(43200,86400);
		target.setSkillCooldown(cooldown);
		if (cooldown > 64800) {
			if (roll >= target.getDies(Skill.WOODCUTTING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your wood cutting trip was extremely successful but you are feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your wood cutting trip was not very successful and you are feeling very drained.");
			}
		} else {
			if (roll >= target.getDies(Skill.WOODCUTTING) * 10) {
				p.sendMessage(ChatColor.GRAY + "You feel that your wood cutting trip was extremely successful and you aren't feeling very drained.");
			} else {
				p.sendMessage(ChatColor.GRAY + "You feel that your wood cutting trip was not very successful but you aren't feeling very drained.");
			}
		}
		if (target.getSkillExperience(Skill.WOODCUTTING) + logs >= target.getExperienceRemaining(Skill.WOODCUTTING)) {
			target.setSkillLevel(Skill.WOODCUTTING, target.getSkillLevel(Skill.WOODCUTTING) + 1);
			p.sendMessage(ChatColor.GRAY + "You now feel more confident in this profession.");
			return true;
		} else {
			target.setSkillExperience(Skill.WOODCUTTING, target.getSkillExperience(Skill.WOODCUTTING) + logs);
		}
		//p.getInventory().getItemInMainHand().setDurability((short) (p.getInventory().getItemInMainHand().getDurability() + (p.getInventory().getItemInMainHand().getType().getMaxDurability() * .25)));
		return true;
	}

	public int getModifier(ItemStack item) {
		int modifier = 0;

		switch(item.getType()) {
		case WOOD_AXE:
			modifier = modifier-1;
			break;
		case STONE_AXE:
			modifier = 0;
			break;
		case IRON_AXE:
			modifier = modifier+1;
			break;
		case GOLD_AXE:
			modifier = modifier+2;
			break;
		case DIAMOND_AXE:
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
