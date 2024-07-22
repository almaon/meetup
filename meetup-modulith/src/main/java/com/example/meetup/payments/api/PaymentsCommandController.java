package com.example.meetup.payments.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.meetup.payments.base.infrastructure.ICommandDispatcher;


import com.example.meetup.payments.application.meetingFees.CreateMeetingFeeCommand;
import com.example.meetup.payments.application.meetingFees.CreateMeetingFeePaymentCommand;
import com.example.meetup.payments.application.meetingFees.MarkMeetingFeeAsPaidCommand;
import com.example.meetup.payments.application.meetingFees.MarkMeetingFeePaymentAsPaidCommand;
import com.example.meetup.payments.application.payers.CreatePayerCommand;
import com.example.meetup.payments.application.priceListItems.ActivatePriceListItemCommand;
import com.example.meetup.payments.application.subscriptions.MarkSubscriptionPaymentAsPaidCommand;
import com.example.meetup.payments.application.subscriptions.ExpireSubscriptionsCommand;
import com.example.meetup.payments.application.subscriptions.ExpireSubscriptionPaymentsCommand;
import com.example.meetup.payments.application.subscriptions.ExpireSubscriptionPaymentCommand;
import com.example.meetup.payments.application.subscriptions.ExpireSubscriptionCommand;
import com.example.meetup.payments.application.subscriptions.CreateSubscriptionCommand;
import com.example.meetup.payments.application.priceListItems.ChangePriceListItemAttributesCommand;
import com.example.meetup.payments.application.priceListItems.CreatePriceListItemCommand;
import com.example.meetup.payments.application.priceListItems.DeactivatePriceListItemCommand;
import com.example.meetup.payments.application.subscriptions.BuySubscriptionCommand;
import com.example.meetup.payments.application.subscriptions.BuySubscriptionRenewalCommand;
import com.example.meetup.payments.application.subscriptions.MarkSubscriptionRenewalPaymentAsPaidCommand;
import com.example.meetup.payments.application.subscriptions.RenewSubscriptionCommand;
import com.example.meetup.payments.application.subscriptions.SendSubscriptionCreationConfirmationEmailCommand;
import com.example.meetup.payments.application.subscriptions.SendSubscriptionRenewalConfirmationEmailCommand;
import com.example.meetup.payments.application.meetingFees.ExecuteMeetingFeePaymentCommand;
import com.example.meetup.payments.application.subscriptions.ExecuteSubscriptionPaymentCommand;
import java.util.UUID;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.meetup.payments.base.infrastructure.IQueryDispatcher;



@RestController
@RequestMapping("/api/payments/commands")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentsCommandController {

	private final ICommandDispatcher commandDispatcher;

	
	@PostMapping("/createMeetingFee")
	@ResponseStatus(HttpStatus.OK)
	public UUID createMeetingFee(@RequestBody CreateMeetingFeeCommand createMeetingFeeCommand) {

		return commandDispatcher.executeCommandSync(createMeetingFeeCommand);
	}
	
	@PostMapping("/createMeetingFeePayment")
	@ResponseStatus(HttpStatus.OK)
	public UUID createMeetingFeePayment(@RequestBody CreateMeetingFeePaymentCommand createMeetingFeePaymentCommand) {

		return commandDispatcher.executeCommandSync(createMeetingFeePaymentCommand);
	}
	
	
	@PostMapping("/markMeetingFeeAsPaid")
	@ResponseStatus(HttpStatus.OK)
	public void markMeetingFeeAsPaid(@RequestBody MarkMeetingFeeAsPaidCommand markMeetingFeeAsPaidCommand) {

		commandDispatcher.executeCommandAsync(markMeetingFeeAsPaidCommand);
	}
	
	
	@PostMapping("/markMeetingFeePaymentAsPaid")
	@ResponseStatus(HttpStatus.OK)
	public void markMeetingFeePaymentAsPaid(@RequestBody MarkMeetingFeePaymentAsPaidCommand markMeetingFeePaymentAsPaidCommand) {

		commandDispatcher.executeCommandAsync(markMeetingFeePaymentAsPaidCommand);
	}
	
	@PostMapping("/createPayer")
	@ResponseStatus(HttpStatus.OK)
	public UUID createPayer(@RequestBody CreatePayerCommand createPayerCommand) {

		return commandDispatcher.executeCommandSync(createPayerCommand);
	}
	
	
	@PostMapping("/activatePriceListItem")
	@ResponseStatus(HttpStatus.OK)
	public void activatePriceListItem(@RequestBody ActivatePriceListItemCommand activatePriceListItemCommand) {

		commandDispatcher.executeCommandAsync(activatePriceListItemCommand);
	}
	
	
	@PostMapping("/markSubscriptionPaymentAsPaid")
	@ResponseStatus(HttpStatus.OK)
	public void markSubscriptionPaymentAsPaid(@RequestBody MarkSubscriptionPaymentAsPaidCommand markSubscriptionPaymentAsPaidCommand) {

		commandDispatcher.executeCommandAsync(markSubscriptionPaymentAsPaidCommand);
	}
	
	
	@PostMapping("/expireSubscriptions")
	@ResponseStatus(HttpStatus.OK)
	public void expireSubscriptions(@RequestBody ExpireSubscriptionsCommand expireSubscriptionsCommand) {

		commandDispatcher.executeCommandAsync(expireSubscriptionsCommand);
	}
	
	
	@PostMapping("/expireSubscriptionPayments")
	@ResponseStatus(HttpStatus.OK)
	public void expireSubscriptionPayments(@RequestBody ExpireSubscriptionPaymentsCommand expireSubscriptionPaymentsCommand) {

		commandDispatcher.executeCommandAsync(expireSubscriptionPaymentsCommand);
	}
	
	
	@PostMapping("/expireSubscriptionPayment")
	@ResponseStatus(HttpStatus.OK)
	public void expireSubscriptionPayment(@RequestBody ExpireSubscriptionPaymentCommand expireSubscriptionPaymentCommand) {

		commandDispatcher.executeCommandAsync(expireSubscriptionPaymentCommand);
	}
	
	
	@PostMapping("/expireSubscription")
	@ResponseStatus(HttpStatus.OK)
	public void expireSubscription(@RequestBody ExpireSubscriptionCommand expireSubscriptionCommand) {

		commandDispatcher.executeCommandAsync(expireSubscriptionCommand);
	}
	
	@PostMapping("/createSubscription")
	@ResponseStatus(HttpStatus.OK)
	public UUID createSubscription(@RequestBody CreateSubscriptionCommand createSubscriptionCommand) {

		return commandDispatcher.executeCommandSync(createSubscriptionCommand);
	}
	
	
	@PostMapping("/changePriceListItemAttributes")
	@ResponseStatus(HttpStatus.OK)
	public void changePriceListItemAttributes(@RequestBody ChangePriceListItemAttributesCommand changePriceListItemAttributesCommand) {

		commandDispatcher.executeCommandAsync(changePriceListItemAttributesCommand);
	}
	
	@PostMapping("/createPriceListItem")
	@ResponseStatus(HttpStatus.OK)
	public UUID createPriceListItem(@RequestBody CreatePriceListItemCommand createPriceListItemCommand) {

		return commandDispatcher.executeCommandSync(createPriceListItemCommand);
	}
	
	
	@PostMapping("/deactivatePriceListItem")
	@ResponseStatus(HttpStatus.OK)
	public void deactivatePriceListItem(@RequestBody DeactivatePriceListItemCommand deactivatePriceListItemCommand) {

		commandDispatcher.executeCommandAsync(deactivatePriceListItemCommand);
	}
	
	@PostMapping("/buySubscription")
	@ResponseStatus(HttpStatus.OK)
	public UUID buySubscription(@RequestBody BuySubscriptionCommand buySubscriptionCommand) {

		return commandDispatcher.executeCommandSync(buySubscriptionCommand);
	}
	
	@PostMapping("/buySubscriptionRenewal")
	@ResponseStatus(HttpStatus.OK)
	public UUID buySubscriptionRenewal(@RequestBody BuySubscriptionRenewalCommand buySubscriptionRenewalCommand) {

		return commandDispatcher.executeCommandSync(buySubscriptionRenewalCommand);
	}
	
	
	@PostMapping("/markSubscriptionRenewalPaymentAsPaid")
	@ResponseStatus(HttpStatus.OK)
	public void markSubscriptionRenewalPaymentAsPaid(@RequestBody MarkSubscriptionRenewalPaymentAsPaidCommand markSubscriptionRenewalPaymentAsPaidCommand) {

		commandDispatcher.executeCommandAsync(markSubscriptionRenewalPaymentAsPaidCommand);
	}
	
	
	@PostMapping("/renewSubscription")
	@ResponseStatus(HttpStatus.OK)
	public void renewSubscription(@RequestBody RenewSubscriptionCommand renewSubscriptionCommand) {

		commandDispatcher.executeCommandAsync(renewSubscriptionCommand);
	}
	
	
	@PostMapping("/sendSubscriptionCreationConfirmationEmail")
	@ResponseStatus(HttpStatus.OK)
	public void sendSubscriptionCreationConfirmationEmail(@RequestBody SendSubscriptionCreationConfirmationEmailCommand sendSubscriptionCreationConfirmationEmailCommand) {

		commandDispatcher.executeCommandAsync(sendSubscriptionCreationConfirmationEmailCommand);
	}
	
	
	@PostMapping("/sendSubscriptionRenewalConfirmationEmail")
	@ResponseStatus(HttpStatus.OK)
	public void sendSubscriptionRenewalConfirmationEmail(@RequestBody SendSubscriptionRenewalConfirmationEmailCommand sendSubscriptionRenewalConfirmationEmailCommand) {

		commandDispatcher.executeCommandAsync(sendSubscriptionRenewalConfirmationEmailCommand);
	}
	
	
	@PostMapping("/executeMeetingFeePayment")
	@ResponseStatus(HttpStatus.OK)
	public void executeMeetingFeePayment(@RequestBody ExecuteMeetingFeePaymentCommand executeMeetingFeePaymentCommand) {

		commandDispatcher.executeCommandAsync(executeMeetingFeePaymentCommand);
	}
	
	
	@PostMapping("/executeSubscriptionPayment")
	@ResponseStatus(HttpStatus.OK)
	public void executeSubscriptionPayment(@RequestBody ExecuteSubscriptionPaymentCommand executeSubscriptionPaymentCommand) {

		commandDispatcher.executeCommandAsync(executeSubscriptionPaymentCommand);
	}

}
