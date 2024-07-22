package com.example.meetup.meetings.application.meetingComments.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMeetingCommentsByIdQueryHandler implements IQueryHandler<GetMeetingCommentsByIdQuery, MeetingCommentsView> {

	private final MeetingCommentsRepository meetingCommentsRepository;
	
 	@Override
    public MeetingCommentsView handle(GetMeetingCommentsByIdQuery query) {
    	return meetingCommentsRepository.findByMeetingCommentId(query.getMeetingCommentId());
    }

}
