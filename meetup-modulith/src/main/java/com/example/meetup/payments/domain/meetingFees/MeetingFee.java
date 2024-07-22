package com.example.meetup.payments.domain.meetingFees;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.meetup.payments.base.domain.Aggregate;
import com.example.meetup.payments.base.domain.Entity;
import com.example.meetup.payments.base.domain.IDomainEvent;
import com.example.meetup.payments.domain.MeetingId;
import com.example.meetup.payments.domain.MoneyValue;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeCanceledEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeCreatedEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeeExpiredEvent;
import com.example.meetup.payments.domain.meetingFees.events.MeetingFeePaidEvent;
import com.example.meetup.payments.domain.payers.PayerId;

import lombok.Getter;
import lombok.Setter;


@Getter
public class MeetingFee extends Aggregate {
	
	// business id
	@Setter // for testing
	protected MeetingFeeId meetingFeeId;
	
	public String getStreamId() {
		return "Payments-MeetingFee-" + meetingFeeId.getValue();
	}
		
	protected PayerId payerId;
	protected MeetingId meetingId;
	protected MoneyValue fee;
	protected MeetingFeeStatus status;
	
	public MeetingFee() {
	}
	
	public MeetingFee(PayerId payerId, MeetingId meetingId, MoneyValue fee) {

		var meetingFeeCreated = new MeetingFeeCreatedEvent(
			UUID.randomUUID(),
			MeetingFeeStatus.WaitingForPayment.name(),
			payerId.getValue(),
			meetingId.getValue(),
			fee.getValue(),
			fee.getCurrency());
 
		apply(meetingFeeCreated);
		addDomainEvent(meetingFeeCreated);

	}
	
	public void markAsPaid() {

		var meetingFeePaid = new MeetingFeePaidEvent(
			meetingFeeId.getValue(),
			status.name());
 
		apply(meetingFeePaid);
		addDomainEvent(meetingFeePaid);

	}	
	public void expire() {

		var meetingFeeExpired = new MeetingFeeExpiredEvent(
			meetingFeeId.getValue(),
			status.toString());
 
		apply(meetingFeeExpired);
		addDomainEvent(meetingFeeExpired);

	}	
	public void cancel() {

		var meetingFeeCanceled = new MeetingFeeCanceledEvent(
			meetingFeeId.getValue(),
			status.toString());
 
		apply(meetingFeeCanceled);
		addDomainEvent(meetingFeeCanceled);

	}	
	
	
	
	private boolean when(MeetingFeeCanceledEvent event) {

		status = MeetingFeeStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(MeetingFeeCreatedEvent event) {

		meetingFeeId = new MeetingFeeId(event.getMeetingFeeId());
		status = MeetingFeeStatus.valueOf(event.getStatus());
		payerId = new PayerId(event.getPayerId());
		meetingId = new MeetingId(event.getMeetingId());
		fee = new MoneyValue(event.getFeeValue(), event.getFeeCurrency());
		
		return true;

	}
	
	private boolean when(MeetingFeePaidEvent event) {

		status = MeetingFeeStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	private boolean when(MeetingFeeExpiredEvent event) {

		status = MeetingFeeStatus.valueOf(event.getStatus());
		
		return true;

	}
	
	@Override
	protected boolean apply(IDomainEvent event) {
	
		if (event instanceof MeetingFeeCanceledEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof MeetingFeeCreatedEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof MeetingFeePaidEvent castedEvent) {
			return when(castedEvent);
		}
	
		if (event instanceof MeetingFeeExpiredEvent castedEvent) {
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
