package com.example.meetup.payments.domain.meetingFeePayments;


import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.base.domain.Entity;

	
import com.example.meetup.payments.domain.meetingFeePayments.events.MeetingFeePaymentCreatedEvent;
	
import com.example.meetup.payments.domain.meetingFeePayments.events.MeetingFeePaymentExpiredEvent;
	
import com.example.meetup.payments.domain.meetingFeePayments.events.MeetingFeePaymentPaidEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.example.meetup.payments.domain.meetingFees.MeetingFeeId;
import lombok.Getter;
import lombok.Setter;


@Getter
public class MeetingFeePayment extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingFeePaymentId meetingFeePaymentId;
	
	public String getStreamId() {
		return "Payments-MeetingFeePayment-" + meetingFeePaymentId.getValue();
	}
		
	protected MeetingFeeId meetingFeeId;
	protected MeetingFeePaymentStatus status;
	
	public MeetingFeePayment() {
	}
	
	public MeetingFeePayment(MeetingFeeId meetingFeeId) {

		var meetingFeePaymentCreated = new MeetingFeePaymentCreatedEvent(
			meetingFeeId.getValue(),
			UUID.randomUUID(),
			MeetingFeePaymentStatus.WaitingForPayment.name());
 
		apply(meetingFeePaymentCreated);
		addDomainEvent(meetingFeePaymentCreated);

	}
	
	public void expire() {

		var meetingFeePaymentExpired = new MeetingFeePaymentExpiredEvent(
			null,
			status.toString());
 
		apply(meetingFeePaymentExpired);
		addDomainEvent(meetingFeePaymentExpired);

	}	
	public void markAsPaid() {

		var meetingFeePaymentPaid = new MeetingFeePaymentPaidEvent(
			meetingFeePaymentId.getValue(),
			status.name());
 
		apply(meetingFeePaymentPaid);
		addDomainEvent(meetingFeePaymentPaid);

	}	
	
	
	
	private boolean when(MeetingFeePaymentCreatedEvent event) {

		meetingFeePaymentId = new MeetingFeePaymentId(event.getMeetingFeePaymentId());
		meetingFeeId = new MeetingFeeId(event.getMeetingFeeId());
		status = MeetingFeePaymentStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(MeetingFeePaymentPaidEvent event) {

		status = MeetingFeePaymentStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(MeetingFeePaymentExpiredEvent event) {

		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof MeetingFeePaymentCreatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof MeetingFeePaymentPaidEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof MeetingFeePaymentExpiredEvent castedEvent) {
			return when(castedEvent);
		}
		return false;
	}
	
	@Override
	public List<Entity> getDirectChildEntities() {
		var entities = new ArrayList<Entity>();
		return entities;
	}
	
	
}
