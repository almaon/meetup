package com.example.meetup.payments.infrastructure;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.infrastructure.MeetingsQueryDispatcher;
import com.example.meetup.payments.domain.payers.PayerId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentContext {

	private final MeetingsQueryDispatcher queryDispatcher;

	private UUID prinicipalUUIDForTesting;
	
	public PayerId principalId() {
		
		if (prinicipalUUIDForTesting != null) {
			return new PayerId(prinicipalUUIDForTesting);
		}

		JwtAuthenticationToken principal = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		MemberView member = queryDispatcher.executeQuery(
				new GetMemberByLoginQuery(principal.getName()));
				
		return new PayerId(member.getMemberId());
	}
	
	public void setPrinicpalUUIDForTesting(UUID id) {
		prinicipalUUIDForTesting = id;
	}
	
}
