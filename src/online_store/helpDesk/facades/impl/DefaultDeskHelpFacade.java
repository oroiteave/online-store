package online_store.helpDesk.facades.impl;

import online_store.helpDesk.facades.HelpDeskFacade;
import online_store.helpDesk.utils.CustomSupportTicketsComparator;
import online_store.helpDesk.entities.SupportTicket;

import java.util.PriorityQueue;
import java.util.Queue;


public class DefaultDeskHelpFacade implements HelpDeskFacade{

	private int numberOfTickets;
	private static Queue<SupportTicket> supportTickets;
	
	{
		supportTickets = new PriorityQueue<>(new CustomSupportTicketsComparator());
	}
	
	
	@Override
	public void addNewSupportTicket(SupportTicket supportTicket) {
		supportTickets.add(supportTicket);
		numberOfTickets++;
	}

	@Override
	public SupportTicket getNextSupportTicket() {
		numberOfTickets--;
		return supportTickets.poll();
	}

	@Override
	public int getNumberOfTickets() {
		return numberOfTickets;
	}
}
