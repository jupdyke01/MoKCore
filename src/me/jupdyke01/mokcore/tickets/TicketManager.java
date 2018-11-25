package me.jupdyke01.mokcore.tickets;

import java.util.ArrayList;

import me.jupdyke01.mokcore.Main;
import me.jupdyke01.mokcore.enums.Status;

public class TicketManager {

	Main main;
	private ArrayList<Ticket> tickets = new ArrayList<>();
	
	public TicketManager(Main main) {
		this.main = main;
	}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}
	
	public Ticket getTicket(int ticketID) {
		for (Ticket ticket : tickets) {
			if (ticket.getTicketID() == ticketID)
				return ticket;
		}
		return null;
	}
	
	public int ticketNumber() {
		return tickets.size() + 1;
	}
	
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
	
	public void saveTickets() {
		for (Ticket ticket : tickets) {
			main.getConfig().set("tickets." + ticket.getTicketID() + ".creator", ticket.getCreatorUUID());
			main.getConfig().set("tickets." + ticket.getTicketID() + ".information", ticket.getInformation());
			main.getConfig().set("tickets." + ticket.getTicketID() + ".status", ticket.getStatus().toString());
			main.saveConfig();
		}
	}
	
	public void loadTickets() {
		if (main.getConfig().contains("tickets")) {
			for (String str : main.getConfig().getConfigurationSection("tickets").getKeys(false)) {
				String creatorUUID = main.getConfig().getString("tickets." + str + ".creator");
				String information = main.getConfig().getString("tickets." + str + ".information");
				Status status = Status.valueOf(main.getConfig().getString("tickets." + str + ".status"));
				addTicket(new Ticket(str, creatorUUID, information, status));
			}
		}
	}
}
