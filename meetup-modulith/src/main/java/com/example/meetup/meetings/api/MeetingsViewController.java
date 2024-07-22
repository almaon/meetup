package com.example.meetup.meetings.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.GetAllMeetingCommentingConfigurationsByIdsQuery;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.GetAllMeetingCommentingConfigurationsQuery;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.GetMeetingCommentingConfigurationsByIdQuery;
import com.example.meetup.meetings.application.meetingCommentingConfigurations.view.MeetingCommentingConfigurationsView;
import com.example.meetup.meetings.application.meetingComments.view.GetAllMeetingCommentLikersByIdsQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetAllMeetingCommentLikersQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetAllMeetingCommentsByIdsQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetAllMeetingCommentsQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentLikersByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.GetMeetingCommentsByIdQuery;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentLikersView;
import com.example.meetup.meetings.application.meetingComments.view.MeetingCommentsView;
import com.example.meetup.meetings.application.meetingGroupProposals.view.GetAllMeetingGroupProposalsByIdsQuery;
import com.example.meetup.meetings.application.meetingGroupProposals.view.GetAllMeetingGroupProposalsQuery;
import com.example.meetup.meetings.application.meetingGroupProposals.view.GetMeetingGroupProposalsByIdQuery;
import com.example.meetup.meetings.application.meetingGroupProposals.view.MeetingGroupProposalsView;
import com.example.meetup.meetings.application.meetingGroups.view.GetAllMeetingGroupsByIdsQuery;
import com.example.meetup.meetings.application.meetingGroups.view.GetAllMeetingGroupsQuery;
import com.example.meetup.meetings.application.meetingGroups.view.GetAllMemberMeetingGroupsByIdsQuery;
import com.example.meetup.meetings.application.meetingGroups.view.GetAllMemberMeetingGroupsQuery;
import com.example.meetup.meetings.application.meetingGroups.view.GetMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.GetMemberMeetingGroupsByIdQuery;
import com.example.meetup.meetings.application.meetingGroups.view.MeetingGroupsView;
import com.example.meetup.meetings.application.meetingGroups.view.MemberMeetingGroupsView;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingAttendeesByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingAttendeesQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingDetailsByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingDetailsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingWaitlistsByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingWaitlistsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingsByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingsMeetingFeesPaymentStatusByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingsMeetingFeesPaymentStatusQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMeetingsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMemberMeetingsByIdsQuery;
import com.example.meetup.meetings.application.meetings.view.GetAllMemberMeetingsQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingAttendeesByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingDetailsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingWaitlistsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMeetingsMeetingFeesPaymentStatusByIdQuery;
import com.example.meetup.meetings.application.meetings.view.GetMemberMeetingsByIdQuery;
import com.example.meetup.meetings.application.meetings.view.MeetingAttendeesView;
import com.example.meetup.meetings.application.meetings.view.MeetingDetailsView;
import com.example.meetup.meetings.application.meetings.view.MeetingWaitlistsView;
import com.example.meetup.meetings.application.meetings.view.MeetingsMeetingFeesPaymentStatusView;
import com.example.meetup.meetings.application.meetings.view.MeetingsView;
import com.example.meetup.meetings.application.meetings.view.MemberMeetingsView;
import com.example.meetup.meetings.application.members.view.GetAllMemberByIdsQuery;
import com.example.meetup.meetings.application.members.view.GetAllMemberQuery;
import com.example.meetup.meetings.application.members.view.GetMemberByIdQuery;
import com.example.meetup.meetings.application.members.view.GetMemberByLoginQuery;
import com.example.meetup.meetings.application.members.view.MemberView;
import com.example.meetup.meetings.base.infrastructure.IQueryDispatcher;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meetings/views")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MeetingsViewController {

	private final IQueryDispatcher queryDispatcher;

	@GetMapping("/userId")
	@ResponseStatus(HttpStatus.OK)
	public UUID userId() {
		JwtAuthenticationToken principal = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		MemberView member = queryDispatcher.executeQuery(
				new GetMemberByLoginQuery(principal.getName()));
		
		return member.getMemberId();
	}
	
	@GetMapping("/meetingGroups/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingGroupsView getMeetingGroupsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingGroupsByIdQuery(id));
	}
	
	@GetMapping("/meetingGroups")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingGroupsView> getAllMeetingGroupsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingGroupsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingGroups")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingGroupsView> getAllMeetingGroups() {

		return queryDispatcher.executeQuery(new GetAllMeetingGroupsQuery());
	}	
	
	
	@GetMapping("/meetings/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingsView getMeetingsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingsByIdQuery(id));
	}
	
	@GetMapping("/meetings")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingsView> getAllMeetingsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetings")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingsView> getAllMeetings() {

		return queryDispatcher.executeQuery(new GetAllMeetingsQuery());
	}	
	
	
	@GetMapping("/meetingWaitlists/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingWaitlistsView getMeetingWaitlistsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingWaitlistsByIdQuery(id));
	}
	
	@GetMapping("/meetingWaitlists")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingWaitlistsView> getAllMeetingWaitlistsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingWaitlistsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingWaitlists")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingWaitlistsView> getAllMeetingWaitlists() {

		return queryDispatcher.executeQuery(new GetAllMeetingWaitlistsQuery());
	}	
	
	
	@GetMapping("/meetingCommentingConfigurations/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingCommentingConfigurationsView getMeetingCommentingConfigurationsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingCommentingConfigurationsByIdQuery(id));
	}
	
	@GetMapping("/meetingCommentingConfigurations")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentingConfigurationsView> getAllMeetingCommentingConfigurationsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentingConfigurationsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingCommentingConfigurations")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentingConfigurationsView> getAllMeetingCommentingConfigurations() {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentingConfigurationsQuery());
	}	
	
	
	@GetMapping("/meetingCommentLikers/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingCommentLikersView getMeetingCommentLikersById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingCommentLikersByIdQuery(id));
	}
	
	@GetMapping("/meetingCommentLikers")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentLikersView> getAllMeetingCommentLikersByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentLikersByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingCommentLikers")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentLikersView> getAllMeetingCommentLikers() {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentLikersQuery());
	}	
	
	
	@GetMapping("/meetingComments/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingCommentsView getMeetingCommentsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingCommentsByIdQuery(id));
	}
	
	@GetMapping("/meetingComments")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentsView> getAllMeetingCommentsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingComments")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingCommentsView> getAllMeetingComments() {

		return queryDispatcher.executeQuery(new GetAllMeetingCommentsQuery());
	}	
	
	
	@GetMapping("/memberMeetings/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberMeetingsView getMemberMeetingsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMemberMeetingsByIdQuery(id));
	}
	
	@GetMapping("/memberMeetings")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberMeetingsView> getAllMemberMeetingsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMemberMeetingsByIdsQuery(ids));
	}
	
	@GetMapping("/all/memberMeetings")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberMeetingsView> getAllMemberMeetings() {

		return queryDispatcher.executeQuery(new GetAllMemberMeetingsQuery());
	}	
	
	
	@GetMapping("/meetingAttendees/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingAttendeesView getMeetingAttendeesById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingAttendeesByIdQuery(id));
	}
	
	@GetMapping("/meetingAttendees")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingAttendeesView> getAllMeetingAttendeesByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingAttendeesByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingAttendees")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingAttendeesView> getAllMeetingAttendees() {

		return queryDispatcher.executeQuery(new GetAllMeetingAttendeesQuery());
	}	
	
	
	@GetMapping("/meetingDetails/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingDetailsView getMeetingDetailsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingDetailsByIdQuery(id));
	}
	
	@GetMapping("/meetingDetails")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingDetailsView> getAllMeetingDetailsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingDetailsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingDetails")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingDetailsView> getAllMeetingDetails() {

		return queryDispatcher.executeQuery(new GetAllMeetingDetailsQuery());
	}	
	
	
	@GetMapping("/meetingGroupProposals/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingGroupProposalsView getMeetingGroupProposalsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingGroupProposalsByIdQuery(id));
	}
	
	@GetMapping("/meetingGroupProposals")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingGroupProposalsView> getAllMeetingGroupProposalsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingGroupProposalsByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingGroupProposals")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingGroupProposalsView> getAllMeetingGroupProposals() {

		return queryDispatcher.executeQuery(new GetAllMeetingGroupProposalsQuery());
	}	
	
	
	@GetMapping("/member/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberView getMemberById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMemberByIdQuery(id));
	}
	
	@GetMapping("/member")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberView> getAllMemberByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMemberByIdsQuery(ids));
	}
	
	@GetMapping("/all/member")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberView> getAllMember() {

		return queryDispatcher.executeQuery(new GetAllMemberQuery());
	}	
	
	
	@GetMapping("/memberMeetingGroups/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberMeetingGroupsView getMemberMeetingGroupsById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMemberMeetingGroupsByIdQuery(id));
	}
	
	@GetMapping("/memberMeetingGroups")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberMeetingGroupsView> getAllMemberMeetingGroupsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMemberMeetingGroupsByIdsQuery(ids));
	}
	
	@GetMapping("/all/memberMeetingGroups")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberMeetingGroupsView> getAllMemberMeetingGroups() {

		return queryDispatcher.executeQuery(new GetAllMemberMeetingGroupsQuery());
	}	
	
	
	@GetMapping("/meetingsMeetingFeesPaymentStatus/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingsMeetingFeesPaymentStatusView getMeetingsMeetingFeesPaymentStatusById(@PathVariable(name = "id") UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingsMeetingFeesPaymentStatusByIdQuery(id));
	}
	
	@GetMapping("/meetingsMeetingFeesPaymentStatus")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingsMeetingFeesPaymentStatusView> getAllMeetingsMeetingFeesPaymentStatusByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingsMeetingFeesPaymentStatusByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingsMeetingFeesPaymentStatus")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingsMeetingFeesPaymentStatusView> getAllMeetingsMeetingFeesPaymentStatus() {

		return queryDispatcher.executeQuery(new GetAllMeetingsMeetingFeesPaymentStatusQuery());
	}	

}
