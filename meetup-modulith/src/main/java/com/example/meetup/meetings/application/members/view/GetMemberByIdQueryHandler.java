package com.example.meetup.meetings.application.members.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;


@Component
@RequiredArgsConstructor
public class GetMemberByIdQueryHandler implements IQueryHandler<GetMemberByIdQuery, MemberView> {

	private final MemberRepository memberRepository;
	
 	@Override
    public MemberView handle(GetMemberByIdQuery query) {
    	return memberRepository.findByMemberId(query.getMemberId());
    }

}
