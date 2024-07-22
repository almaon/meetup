package com.example.meetup.meetings.application.meetings;

import java.time.LocalDateTime;
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
public class ChangeMeetingMainAttributesCommand extends CommandBase {

	@NotNull
	private UUID meetingId;
	@NotNull
	private LocalDateTime termStartDate;
	@NotBlank
	private String meetingLocationCity;
	private int attendeesLimit;
	@NotNull
	private LocalDateTime termEndDate;
	@NotBlank
	private String meetingLocationPostalCode;
	@NotBlank
	private String meetingLocationAddress;
	private String eventFeeCurrency;
	private String meetingLocationName;
	@NotBlank
	private String title;
	private double eventFeeValue;
	private int guestsLimit;
	private LocalDateTime rsvpTermStartDate;
	private LocalDateTime rsvpTermEndDate;
	@NotBlank
	private String description;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return ChangeMeetingMainAttributesCommandHandler.class;
    }

}
