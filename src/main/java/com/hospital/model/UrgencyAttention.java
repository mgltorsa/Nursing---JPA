package com.hospital.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "t_urgency")
@NamedQuery(name="UrgencyAttention.findAll", query="SELECT t FROM UrgencyAttention t")
public class UrgencyAttention {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Urgency_IDUrgency_Generator")
	@SequenceGenerator(name = "Urgency_IDUrgency_Generator", allocationSize = 1)
	private Long consecutive;

	@NonNull
	private LocalDate date;

	@NonNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Patient patient;

	@NonNull
	private String generalDescription;

	@NonNull
	private String procedure;

	@NonNull
	private Boolean forwarded;	

	private String forwardedPlace;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "urgencyAttention",orphanRemoval = true)
	private List<Supply> supplies;
	
	
	public UrgencyAttention(Patient patient, LocalDate date ,String generalDescription,String procedure, Boolean forwarded) {
		this.patient=patient;
		this.date=date;
		this.generalDescription=generalDescription;
		this.procedure=procedure;
		this.forwarded=forwarded;
		this.supplies=new ArrayList<>();
		

	}
	

	

}
