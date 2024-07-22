package com.example.meetup.meetings.application.meetings.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMemberMeetingsByIdsQueryHandler implements IQueryHandler<GetAllMemberMeetingsByIdsQuery, List<MemberMeetingsView>> {

	private final MemberMeetingsRepository memberMeetingsRepository;
	
 	@Override
    public List<MemberMeetingsView> handle(GetAllMemberMeetingsByIdsQuery query) {
    	return memberMeetingsRepository.findAllById(query.getIds());
    }

}
