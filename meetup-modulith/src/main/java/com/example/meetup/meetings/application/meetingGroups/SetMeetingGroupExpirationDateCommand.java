package com.example.meetup.meetings.application.meetingGroups;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetMeetingGroupExpirationDateCommand extends CommandBase {

	private UUID meetingGroupId;
	private LocalDateTime dateTo;

	@Override
	public Class<? extends ICommandHandler> getHandlerType() {
		return SetMeetingGroupExpirationDateCommandHandler.class;
	}

}
