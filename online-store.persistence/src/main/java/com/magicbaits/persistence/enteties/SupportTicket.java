package com.magicbaits.persistence.enteties;

public interface SupportTicket {
	Priority getPriority();
	
	int getSequentialNumber();
	
	RequestType getRequestType();
}
