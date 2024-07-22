package com.example.meetup.payments.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.example.meetup.payments.application.meetingFees.view.MeetingFeesView;
import com.example.meetup.payments.application.meetingFees.view.GetAllMeetingFeesQuery;
import com.example.meetup.payments.application.meetingFees.view.GetMeetingFeesByIdQuery;
import com.example.meetup.payments.application.meetingFees.view.GetAllMeetingFeesByIdsQuery;
import com.example.meetup.payments.application.subscriptions.view.SubscriptionPaymentsView;
import com.example.meetup.payments.application.subscriptions.view.GetAllSubscriptionPaymentsQuery;
import com.example.meetup.payments.application.subscriptions.view.GetSubscriptionPaymentsByIdQuery;
import com.example.meetup.payments.application.subscriptions.view.GetAllSubscriptionPaymentsByIdsQuery;
import com.example.meetup.payments.application.payers.view.PayerView;
import com.example.meetup.payments.application.payers.view.GetAllPayerQuery;
import com.example.meetup.payments.application.payers.view.GetPayerByIdQuery;
import com.example.meetup.payments.application.payers.view.GetAllPayerByIdsQuery;
import com.example.meetup.payments.application.priceListItems.view.PriceListItemsView;
import com.example.meetup.payments.application.priceListItems.view.GetAllPriceListItemsQuery;
import com.example.meetup.payments.application.priceListItems.view.GetPriceListItemsByIdQuery;
import com.example.meetup.payments.application.priceListItems.view.GetAllPriceListItemsByIdsQuery;
import com.example.meetup.payments.application.subscriptions.view.SubscriptionsView;
import com.example.meetup.payments.application.subscriptions.view.GetAllSubscriptionsQuery;
import com.example.meetup.payments.application.subscriptions.view.GetSubscriptionsByIdQuery;
import com.example.meetup.payments.application.subscriptions.view.GetAllSubscriptionsByIdsQuery;

import java.util.UUID;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.meetup.payments.base.infrastructure.IQueryDispatcher;



@RestController
@RequestMapping("/api/payments/views")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentsViewController {

	private final IQueryDispatcher queryDispatcher;

	
	
	@GetMapping("/meetingFees/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetingFeesView getMeetingFeesById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetMeetingFeesByIdQuery(id));
	}
	
	@GetMapping("/meetingFees")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingFeesView> getAllMeetingFeesByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllMeetingFeesByIdsQuery(ids));
	}
	
	@GetMapping("/all/meetingFees")
	@ResponseStatus(HttpStatus.OK)
	public List<MeetingFeesView> getAllMeetingFees() {

		return queryDispatcher.executeQuery(new GetAllMeetingFeesQuery());
	}	
	
	
	@GetMapping("/subscriptionPayments/{id}")
	@ResponseStatus(HttpStatus.OK)
	public SubscriptionPaymentsView getSubscriptionPaymentsById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetSubscriptionPaymentsByIdQuery(id));
	}
	
	@GetMapping("/subscriptionPayments")
	@ResponseStatus(HttpStatus.OK)
	public List<SubscriptionPaymentsView> getAllSubscriptionPaymentsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllSubscriptionPaymentsByIdsQuery(ids));
	}
	
	@GetMapping("/all/subscriptionPayments")
	@ResponseStatus(HttpStatus.OK)
	public List<SubscriptionPaymentsView> getAllSubscriptionPayments() {

		return queryDispatcher.executeQuery(new GetAllSubscriptionPaymentsQuery());
	}	
	
	
	@GetMapping("/payer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PayerView getPayerById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetPayerByIdQuery(id));
	}
	
	@GetMapping("/payer")
	@ResponseStatus(HttpStatus.OK)
	public List<PayerView> getAllPayerByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllPayerByIdsQuery(ids));
	}
	
	@GetMapping("/all/payer")
	@ResponseStatus(HttpStatus.OK)
	public List<PayerView> getAllPayer() {

		return queryDispatcher.executeQuery(new GetAllPayerQuery());
	}	
	
	
	@GetMapping("/priceListItems/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PriceListItemsView getPriceListItemsById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetPriceListItemsByIdQuery(id));
	}
	
	@GetMapping("/priceListItems")
	@ResponseStatus(HttpStatus.OK)
	public List<PriceListItemsView> getAllPriceListItemsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllPriceListItemsByIdsQuery(ids));
	}
	
	@GetMapping("/all/priceListItems")
	@ResponseStatus(HttpStatus.OK)
	public List<PriceListItemsView> getAllPriceListItems() {

		return queryDispatcher.executeQuery(new GetAllPriceListItemsQuery());
	}	
	
	
	@GetMapping("/subscriptions/{id}")
	@ResponseStatus(HttpStatus.OK)
	public SubscriptionsView getSubscriptionsById(@PathVariable UUID id) {

		return queryDispatcher.executeQuery(new GetSubscriptionsByIdQuery(id));
	}
	
	@GetMapping("/subscriptions")
	@ResponseStatus(HttpStatus.OK)
	public List<SubscriptionsView> getAllSubscriptionsByIds(@RequestBody List<UUID> ids) {

		return queryDispatcher.executeQuery(new GetAllSubscriptionsByIdsQuery(ids));
	}
	
	@GetMapping("/all/subscriptions")
	@ResponseStatus(HttpStatus.OK)
	public List<SubscriptionsView> getAllSubscriptions() {

		return queryDispatcher.executeQuery(new GetAllSubscriptionsQuery());
	}	

}
