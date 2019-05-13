package com.hospital.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Entity implementation class for Entity: Supply
 *
 */
@Entity
@Table(name = "t_supply")
@NamedQuery(name="Supply.findAll", query="SELECT t FROM Supply t")
@Data
@ToString(exclude = {"patient","urgencyAttention"})
@RequiredArgsConstructor
public class Supply implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Supply_IDSupply_Generator")
	@SequenceGenerator(name = "Supply_IDSupply_Generator", allocationSize = 1)
	private Long consecutive;

	@NonNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "medicine_consecutive", referencedColumnName = "consecutive")
	private Medicine medicine;

	@NonNull
	private Integer quantity;

	@NonNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_document", referencedColumnName = "document")
	private Patient patient;

	@NonNull
	private LocalDate date;

	private String observations;

	@NonNull
	private String pathology;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "urgencyAttention_consecutive", referencedColumnName = "consecutive")
	private UrgencyAttention urgencyAttention;

	
	public Supply() {
		super();
	}

      
	public String getObservations() {
		return observations;
	}


	public void setObservations(String observations) {
		this.observations = observations;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
   
}
