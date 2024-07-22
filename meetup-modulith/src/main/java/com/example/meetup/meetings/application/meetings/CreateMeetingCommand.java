package com.example.meetup.meetings.application.meetings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.meetup.meetings.base.application.CommandBase;
import com.example.meetup.meetings.base.application.ICommandHandler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeetingCommand extends CommandBase {

	@NotNull
	private UUID meetingGroupId;
	@NotBlank
	private String description;
	private LocalDateTime termStartDate;
	@NotBlank
	private String meetingLocationPostalCode;
	@NotBlank
	private String title;
	@NotBlank
	private String meetingLocationAddress;
	private double eventFeeValue;
	@NotBlank
	private String meetingLocationCity;
	private String eventFeeCurrency;
	private int attendeesLimit;
	@NotBlank
	private String meetingLocationName;
	private LocalDateTime rsvpTermEndDate;
	private LocalDateTime rsvpTermStartDate;
	@NotBlank
	private LocalDateTime termEndDate;
	private int guestsLimit;
	private List<UUID> hostMemberIds;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return CreateMeetingCommandHandler.class;
    }

}
