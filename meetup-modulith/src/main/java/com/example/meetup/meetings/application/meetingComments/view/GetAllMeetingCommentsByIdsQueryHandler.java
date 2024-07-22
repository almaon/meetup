package com.example.meetup.meetings.application.meetingComments.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMeetingCommentsByIdsQueryHandler implements IQueryHandler<GetAllMeetingCommentsByIdsQuery, List<MeetingCommentsView>> {

	private final MeetingCommentsRepository meetingCommentsRepository;
	
 	@Override
    public List<MeetingCommentsView> handle(GetAllMeetingCommentsByIdsQuery query) {
    	return meetingCommentsRepository.findAllById(query.getIds());
    }

}
