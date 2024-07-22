package com.example.meetup.meetings.domain;


import com.example.meetup.meetings.base.domain.ValueObject;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MeetingGroupLocation extends ValueObject {

	private String city;
	private String countryCode;
	
	

}
