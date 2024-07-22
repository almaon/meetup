package com.example.meetup.meetings.infrastructure;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;
import com.example.meetup.meetings.domain.member.MemberId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeetingContext {

	private final IQueryDispatcher queryDispatcher;

	private static UUID prinicipalUUIDForTesting;
	
	public MemberId principalId() {
		
		if (prinicipalUUIDForTesting != null) {
			return new MemberId(prinicipalUUIDForTesting);
		}

		JwtAuthenticationToken principal = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		MemberView member = queryDispatcher.executeQuery(
				new GetMemberByLoginQuery(principal.getName()));
		
		return new MemberId(member.getMemberId());
	}
	
	public static void setPrinicpalUUIDForTesting(UUID id) {
		prinicipalUUIDForTesting = id;
	}
	
}
