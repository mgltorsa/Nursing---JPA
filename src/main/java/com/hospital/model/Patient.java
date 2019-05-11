package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "attentions")
@Table(name = "t_patient")
@NamedQuery(name="Patient.findAll", query="SELECT t FROM Patient t")
public class Patient {

	@NonNull
	@Id
	private String document;

	@NonNull
	private String names;

	@NonNull
	private String lastnames;

	private String academicProgram;

	private String academicDependency;

	private String state;
	
	
	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UrgencyAttention> attentions;

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Supply> supplies;
	
	
	public Patient(String document, String names, String lastnames) {
		this.document=document;
		this.names=names;
		this.lastnames=lastnames;
		this.state="ACTIVO";
		this.attentions=new ArrayList<>();
		this.supplies=new ArrayList<>();
	}
	

}
