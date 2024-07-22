package com.example.meetup.payments.domain.meetingFeePayments;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingFeePaymentId extends TypedIdValueBase<UUID> {

	public MeetingFeePaymentId(UUID value) {
		super(value);
	}

}
