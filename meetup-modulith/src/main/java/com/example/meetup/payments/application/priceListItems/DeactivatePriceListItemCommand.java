package com.example.meetup.payments.application.priceListItems;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.example.meetup.payments.base.application.CommandBase;
import com.example.meetup.payments.base.application.ICommandHandler;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeactivatePriceListItemCommand extends CommandBase {

	private UUID priceListItemId;
    	
    @Override
    public Class<? extends ICommandHandler> getHandlerType() {
        return DeactivatePriceListItemCommandHandler.class;
    }

}
