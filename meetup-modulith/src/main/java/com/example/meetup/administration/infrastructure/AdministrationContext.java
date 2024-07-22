package com.example.meetup.administration.infrastructure;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.meetup.administration.domain.administrator.AdministratorId;
import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdministrationContext {

	private final MeetingsQueryDispatcher queryDispatcher;

	private static UUID prinicipalUUIDForTesting;
	
	public AdministratorId principalId() {
		
		if (prinicipalUUIDForTesting != null) {
			return new AdministratorId(prinicipalUUIDForTesting);
		}
		return new AdministratorId(UUID.fromString("e7de6763-3f6b-4c2a-8632-1f87316ec9eb"));
//		JwtAuthenticationToken principal = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//		MemberView member = queryDispatcher.executeQuery(
//				new GetMemberByLoginQuery(principal.getName()));
//		
//		return new AdministratorId(member.getMemberId());
	}
	
	public static void setPrinicpalUUIDForTesting(UUID id) {
		prinicipalUUIDForTesting = id;
	}
	
}
