package com.example.meetup.meetings.domain.meeting;


import com.example.meetup.meetings.base.domain.ValueObject;

import java.util.List;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class Term extends ValueObject {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public boolean isInTerm(LocalDateTime date) {
		 return date == null || (date.isAfter(startDate) && date.isBefore(endDate));
	}	

}
