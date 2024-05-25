package online_store.helpDesk.entities;

public interface SupportTicket {
	Priority getPriority();
	
	int getSequentialNumber();
	
	RequestType getRequestType();
}
