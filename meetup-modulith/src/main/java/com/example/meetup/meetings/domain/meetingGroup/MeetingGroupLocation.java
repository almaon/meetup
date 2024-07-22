package com.example.meetup.meetings.domain.meetingGroup;

import com.example.meetup.meetings.base.domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MeetingGroupLocation extends ValueObject {

	private String city;
	private String countrycode;

}
