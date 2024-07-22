package com.example.meetup.administration.infrastructure.aggregateStore.jpa;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class AdministrationEventStream {

	@Id
	@Column(name = "id", length = 100, unique = true, nullable = false)
	private String streamId;
	
	private int version;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AdministrationEvent> events;
}
