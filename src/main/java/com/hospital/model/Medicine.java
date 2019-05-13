package com.hospital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Entity implementation class for Entity: Medicine
 *
 */
@Entity
@Table(name = "t_medicine")
@NamedQuery(name = "Medicine.findAll", query = "SELECT t FROM Medicine t")
@Data
@ToString(exclude = { "inventaries", "supplies" })
@RequiredArgsConstructor
public class Medicine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	private Long consecutive;

	@NonNull
	private String name;

	@NonNull
	private String genericName;

	@NonNull
	private String laboratory;

	private String administrationType;

	@NonNull
	private String indications;

	private String contraIndications;

	@OneToMany(mappedBy = "medicine", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<InventaryMedicine> inventaries = new ArrayList<>();

	@OneToMany(mappedBy = "medicine", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Supply> supplies = new ArrayList<>();

	public Medicine() {
		super();
	}

}
