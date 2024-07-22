package com.example.meetup.payments.domain.meetingFees;

import com.example.meetup.payments.base.domain.TypedIdValueBase;

import java.util.UUID;


public class MeetingFeeId extends TypedIdValueBase<UUID> {

	public MeetingFeeId(UUID value) {
		super(value);
	}

}
