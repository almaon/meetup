package com.example.meetup.administration.domain.meetingGroupProposals;


import com.example.meetup.administration.base.domain.ValueObject;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AllArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class MeetingGroupProposalDecision extends ValueObject {

	private DecisionCode decisionCode;
	
	

}
