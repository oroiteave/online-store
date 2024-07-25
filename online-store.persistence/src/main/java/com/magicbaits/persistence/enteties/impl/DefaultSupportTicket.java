package com.magicbaits.persistence.enteties.impl;

import com.magicbaits.persistence.enteties.Priority;
import com.magicbaits.persistence.enteties.RequestType;
import com.magicbaits.persistence.enteties.SupportTicket;

public class DefaultSupportTicket implements SupportTicket{
	
	private static int sequencialNumberCount=1;
	
	private Priority priority;
	private RequestType requestType;
	private int sequencialNumber;
	
	{
		sequencialNumber = sequencialNumberCount++;
	}
	
	public DefaultSupportTicket() {
	}
	
	public DefaultSupportTicket(RequestType requesType){
		this.requestType = requesType;
		this.priority = requestType.getPriority();
	}
	
	@Override
	public Priority getPriority() {
		return this.priority;
	}

	@Override
	public int getSequentialNumber() {
		return this.sequencialNumber;
	}

	@Override
	public RequestType getRequestType() {
		return this.requestType;
	}

}
