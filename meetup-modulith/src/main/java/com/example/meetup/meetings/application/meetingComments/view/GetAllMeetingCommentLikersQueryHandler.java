package com.example.meetup.meetings.application.meetingComments.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingCommentLikersQueryHandler implements IQueryHandler<GetAllMeetingCommentLikersQuery, List<MeetingCommentLikersView>> {

	private final MeetingCommentLikersRepository meetingCommentLikersRepository;
	
 	@Override
    public List<MeetingCommentLikersView> handle(GetAllMeetingCommentLikersQuery query) {
    	return meetingCommentLikersRepository.findAll();
    }

}
