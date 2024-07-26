package com.magicbaits.persistence.utils.comparators;

import java.util.Comparator;

import com.magicbaits.persistence.enteties.SupportTicket;

public class CustomSupportTicketsComparator implements Comparator<SupportTicket>{

//	@Override
//	public int compare(SupportTicket ticket1, SupportTicket ticket2) {
//		if (ticket1 == null || ticket2 == null || ticket1.getPriority() == null ||  
//				ticket2.getPriority() == null) {
//			return 0;
//		}
//		
//		int result = ticket2.getPriority().compareTo(ticket1.getPriority());
//		if (result == 0) {
//			result = ticket1.getSequentialNumber() - ticket2.getSequentialNumber();
//		}
//		return result;
//	}
	
	
	@Override
	public int compare(SupportTicket o1, SupportTicket o2) {
		if(o1 == null || o1 == null || o1.getPriority() == null || o2.getPriority() == null) {
			return 0;
		}
		
		int comparePriority = o2.getPriority().compareTo(o1.getPriority());
		
		int compareSequencialNumber = o1.getSequentialNumber() - o2.getSequentialNumber();
		return (comparePriority!=0)? comparePriority:compareSequencialNumber;
	}
}
