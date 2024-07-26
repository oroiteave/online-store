package com.magicbaits.core.facades.impl;

import java.util.PriorityQueue;
import java.util.Queue;

import com.magicbaits.core.facades.HelpDeskFacade;
import com.magicbaits.persistence.enteties.SupportTicket;
import com.magicbaits.persistence.utils.comparators.CustomSupportTicketsComparator;


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
