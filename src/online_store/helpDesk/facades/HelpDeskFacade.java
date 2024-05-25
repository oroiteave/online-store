package online_store.helpDesk.facades;

import online_store.helpDesk.entities.SupportTicket;

public interface HelpDeskFacade {
	void addNewSupportTicket(SupportTicket supportTicket);
	
	SupportTicket getNextSupportTicket();
	
	int getNumberOfTickets();
}
