package com.example.meetup.meetings.domain.meeting;


import com.example.meetup.meetings.base.domain.ValueObject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MeetingLocation extends ValueObject {

	private String name;
	private String address;
	private String postalCode;
	private String city;
	
	

}
