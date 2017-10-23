package com.samsung.fas.pir.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.user.embedded.Address;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
	@Getter
	@JsonProperty("neighborhood")
	@NotNull(message="user.address.neighborhood.missing")
	@NotEmpty(message="user.address.neighborhood.empty")
	@NotBlank(message="user.address.neighborhood.blank")
	private		String			neighborhoodAddress;
	
	@Getter
	@JsonProperty("street")
	@NotNull(message="user.address.street.missing")
	@NotEmpty(message="user.address.street.empty")
	@NotBlank(message="user.address.street.blank")
	private		String			streetNameAddress;
	
	@Getter
	@JsonProperty("complement")
	private		String			complementAddress;
	
	@Getter
	@JsonProperty("number")
	@NotNull(message="user.address.number.missing")
	@NotEmpty(message="user.address.number.empty")
	@NotBlank(message="user.address.number.blank")
	private		String			numberAddress;
	
	@Getter
	@JsonProperty("city")
	@NotNull(message="user.address.city.missing")
	private		Long			cityId;
	
	@Getter
	@JsonProperty("postalcode")
	@NotNull(message="user.address.postalcode.missing")
	@NotEmpty(message="user.address.postalcode.empty")
	@NotBlank(message="user.address.postalcode.blank")
	@Size(min=4, message="user.address.postalcode.blank.short")
	private		String			postalCode;
	
	private AddressDTO(Address embedded) {
		streetNameAddress		= embedded.getStreetAddress();
		complementAddress		= embedded.getComplementAdress();
		numberAddress			= embedded.getNumberAddress();
		postalCode				= embedded.getPostalCode();
		neighborhoodAddress		= embedded.getNeighborhoodAddress();
	}
	
	public AddressDTO() {
		// JSON
	}
	
	@JsonIgnore
	public Address getModel() {
		Address			addr			= new Address();
		addr.setComplementAdress(complementAddress);
		addr.setNeighborhoodAddress(neighborhoodAddress);
		addr.setNumberAddress(numberAddress);
		addr.setPostalCode(postalCode);
		addr.setStreetAddress(streetNameAddress);
		return addr;
	}
	
	public static Address toModel(AddressDTO dto) {
		if (dto != null) {
			Address			addr			= new Address();
			addr.setComplementAdress(dto.complementAddress);
			addr.setNeighborhoodAddress(dto.neighborhoodAddress);
			addr.setNumberAddress(dto.numberAddress);
			addr.setPostalCode(dto.postalCode);
			addr.setStreetAddress(dto.streetNameAddress);
			return addr;
		}
		return null;
	}
	
	public static AddressDTO toDTO(Address entity) {
		if (entity != null) {
			return new AddressDTO(entity);
		}
		return null;
	}
}
