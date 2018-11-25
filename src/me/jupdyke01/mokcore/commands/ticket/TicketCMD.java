package me.jupdyke01.mokcore.commands.ticket;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Status;
import me.jupdyke01.mokcore.tickets.Ticket;
import net.md_5.bungee.api.ChatColor;

public class TicketCMD implements CommandExecutor {

	Main main;

	public TicketCMD(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must be a player to use this command!");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please use /ticket create <description>!");
			return true;
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (p.hasPermission("mokcore.user")) {
				if (args.length < 3) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a valid description!");
					return true;
				}
				String description = "";
				for (int i = 1; i < args.length; i++) {
					description = description + args[i] + " ";
				}
				p.sendMessage(ChatColor.GRAY + "Ticket has been created with ticket ID: " + main.getTicketManager().ticketNumber());
				main.getTicketManager().addTicket(new Ticket(String.valueOf(main.getTicketManager().ticketNumber()), p.getUniqueId().toString(), description, Status.OPEN));
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("mokcore.staff")) {
						player.sendMessage(ChatColor.GRAY + "New ticket created with ID: " + (main.getTicketManager().ticketNumber() - 1));
					}
				}
				
			}
		} else if (args[0].equalsIgnoreCase("list")) {
			if (p.hasPermission("mokcore.staff")) {
				for (Ticket ticket : main.getTicketManager().getTickets()) {
					if (ticket.getStatus().equals(Status.OPEN)) {
						p.sendMessage(ChatColor.GRAY + "-----------------------------");
						p.sendMessage(ChatColor.DARK_GRAY + "ID: " + ChatColor.AQUA + ChatColor.BOLD + ticket.getTicketID());
						p.sendMessage(ChatColor.DARK_GRAY + "Creator: " + ChatColor.AQUA + ChatColor.BOLD + Bukkit.getOfflinePlayer(UUID.fromString(ticket.getCreatorUUID())).getName());
						p.sendMessage(ChatColor.DARK_GRAY + "Description: " + ChatColor.AQUA + ChatColor.BOLD + ticket.getInformation());
						p.sendMessage(ChatColor.GRAY + "-----------------------------");
					}
				}
			}
		} else if (args[0].equalsIgnoreCase("close")) {
			if (p.hasPermission("mokcore.staff")) {
				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a ticket #");
					return true;
				}
				String ticketID;
				try {
				ticketID = args[1];
				} catch(NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a ticket #");
					return true;
				}
				main.getTicketManager().getTicket(Integer.valueOf(ticketID)).setStatus(Status.CLOSED);
				p.sendMessage(ChatColor.GRAY + "Ticket with ID: " + ticketID + " successfuly closed!");
			}
		} else if (args[0].equalsIgnoreCase("show")) {
			if (p.hasPermission("mokcore.staff")) {
				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a ticket #");
					return true;
				}
				String ticketID;
				try {
				ticketID = args[1];
				} catch(NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You must enter a ticket #");
					return true;
				}
				Ticket ticket = main.getTicketManager().getTicket(Integer.valueOf(ticketID));
				p.sendMessage(ChatColor.GRAY + "-----------------------------");
				p.sendMessage(ChatColor.DARK_GRAY + "ID: " + ChatColor.AQUA + ChatColor.BOLD + ticket.getTicketID());
				p.sendMessage(ChatColor.DARK_GRAY + "Creator: " + ChatColor.AQUA + ChatColor.BOLD + Bukkit.getOfflinePlayer(UUID.fromString(ticket.getCreatorUUID())).getName());
				p.sendMessage(ChatColor.DARK_GRAY + "Description: " + ChatColor.AQUA + ChatColor.BOLD + ticket.getInformation());
				p.sendMessage(ChatColor.GRAY + "-----------------------------");
			}
		}

		return true;
	}

}
