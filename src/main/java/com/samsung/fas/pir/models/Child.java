package com.samsung.fas.pir.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.geo.Point;

@Entity(name="child")
public class Child {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id")
	private 	UUID		id;
	
	@Column(name="name")
	@NotNull(message = "Invalid value for name field (NULL)")
	@NotEmpty(message = "Invalid value for name field (EMPTY)")
	private 	String 		name;
	
	@Column(name="mother_name")
	@NotNull(message = "Invalid value for mother's name field (NULL)")
	@NotEmpty(message = "Invalid value for mother's field (EMPTY)")
	private		String		motherName;
	
	@Column(name="father_name")
	private		String		fatherName;
	
	@Column(name="birth")
	@NotNull(message = "Invalid value for birthday field (NULL)")
	@NotEmpty(message = "Invalid value for birthday field (EMPTY)")
	private 	Date 		birth;
	
	@Column(name="genre")
	private		String		genre;
	
	@Column(name="status")
	@NotNull(message = "Invalid value for status field (NULL)")
	@NotEmpty(message = "Invalid value for status field (EMPTY)")
	private		boolean		status;
	
	@Column(name="dt_register")
	@NotNull(message = "Invalid value for registration date field (NULL)")
	@NotEmpty(message = "Invalid value for registration date field (EMPTY)")
	private		Date		registerDate;
	
	@Column(name="location")
	private		Point		location;
	
	@Column(name="neighborhood")
	private		String			neighborhoodAddress;
	
	@Column(name="street")
	private		String			streetAddress;
	
	@Column(name="complement")
	private		String			complementAdress;
	
	@Column(name="number")
	private		String			numberAddress;
	
	@Column(name="postal_code")
	private		String			postalCode;

	@ManyToOne
	@JoinColumn(name="agent_id_fk")
	@NotNull(message = "Invalid value for agent id field (NULL)")
	@NotEmpty(message = "Invalid value for agent id field (EMPTY)")
	private		User		agent;

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMotherName() {
		return motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public Date getBirth() {
		return birth;
	}

	public String getGenre() {
		return genre;
	}

	public boolean isStatus() {
		return status;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public Point getLocation() {
		return location;
	}

	public String getNeighborhoodAddress() {
		return neighborhoodAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getComplementAdress() {
		return complementAdress;
	}

	public String getNumberAddress() {
		return numberAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public User getAgent() {
		return agent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setNeighborhoodAddress(String neighborhoodAddress) {
		this.neighborhoodAddress = neighborhoodAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setComplementAdress(String complementAdress) {
		this.complementAdress = complementAdress;
	}

	public void setNumberAddress(String numberAddress) {
		this.numberAddress = numberAddress;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setAgent(User agent) {
		this.agent = agent;
	}
}
