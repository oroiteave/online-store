package com.magicbaits.core.facades;

import com.magicbaits.persistence.enteties.SupportTicket;

public interface HelpDeskFacade {
	void addNewSupportTicket(SupportTicket supportTicket);
	
	SupportTicket getNextSupportTicket();
	
	int getNumberOfTickets();
}
