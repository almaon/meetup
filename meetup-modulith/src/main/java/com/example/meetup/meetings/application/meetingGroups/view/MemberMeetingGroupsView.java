package com.example.meetup.meetings.application.meetingGroups.view;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberMeetingGroupsView {

	@Id
	@Column(name = "id", length = 16, unique = true, nullable = false)
	private UUID memberId;

	@OneToMany(cascade = CascadeType.ALL)
	private List<MeetingGroup> meetingGroups;

}
