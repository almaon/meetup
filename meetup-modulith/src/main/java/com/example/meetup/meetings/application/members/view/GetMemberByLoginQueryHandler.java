package com.example.meetup.meetings.application.members.view;

import org.springframework.stereotype.Component;

import com.example.meetup.meetings.base.application.IQueryHandler;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class GetMemberByLoginQueryHandler implements IQueryHandler<GetMemberByLoginQuery, MemberView> {

	private final MemberRepository memberRepository;
	
 	@Override
    public MemberView handle(GetMemberByLoginQuery query) {
    	return memberRepository.findByLogin(query.getLogin());
    }

}
