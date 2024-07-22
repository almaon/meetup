package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMemberMeetingsQueryHandler implements IQueryHandler<GetAllMemberMeetingsQuery, List<MemberMeetingsView>> {

	private final MemberMeetingsRepository memberMeetingsRepository;
	
 	@Override
    public List<MemberMeetingsView> handle(GetAllMemberMeetingsQuery query) {
    	return memberMeetingsRepository.findAll();
    }

}
