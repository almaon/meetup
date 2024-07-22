package com.example.meetup.meetings.application.meetingComments.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingCommentLikersByIdQueryHandler implements IQueryHandler<GetMeetingCommentLikersByIdQuery, MeetingCommentLikersView> {

	private final MeetingCommentLikersRepository meetingCommentLikersRepository;
	
 	@Override
    public MeetingCommentLikersView handle(GetMeetingCommentLikersByIdQuery query) {
    	return meetingCommentLikersRepository.findByMeetingCommentId(query.getMeetingCommentId());
    }

}
