package com.example.meetup.meetings.application.members.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAllMemberByIdsQueryHandler implements IQueryHandler<GetAllMemberByIdsQuery, List<MemberView>> {

	private final MemberRepository memberRepository;
	
 	@Override
    public List<MemberView> handle(GetAllMemberByIdsQuery query) {
    	return memberRepository.findAllById(query.getIds());
    }

}
