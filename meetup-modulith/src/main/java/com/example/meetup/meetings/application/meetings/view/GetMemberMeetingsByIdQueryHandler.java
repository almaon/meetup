package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMemberMeetingsByIdQueryHandler implements IQueryHandler<GetMemberMeetingsByIdQuery, MemberMeetingsView> {

	private final MemberMeetingsRepository memberMeetingsRepository;
	
 	@Override
    public MemberMeetingsView handle(GetMemberMeetingsByIdQuery query) {
    	return memberMeetingsRepository.findByMemberId(query.getMemberId());
    }

}
