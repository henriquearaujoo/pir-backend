package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.Address;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@DTO(Address.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO extends BaseDTO<Address> {
	@Getter
	@Setter
	@JsonProperty("neighborhood")
	@NotBlank(message="user.address.neighborhood.missing")
	private		String			neighborhoodAddress;

	@Getter
	@Setter
	@JsonProperty("street")
	@NotBlank(message="user.address.street.missing")
	private		String			streetAddress;

	@Getter
	@Setter
	@JsonProperty("complement")
	private		String			complementAddress;

	@Getter
	@Setter
	@JsonProperty("number")
	@NotBlank(message="user.address.number.missing")
	private		String			numberAddress;

	@Getter
	@Setter
	@JsonProperty("city_id")
	@NotNull(message="user.address.city.missing")
	private 	UUID			cityUUID;

	@Getter
	@Setter
	@JsonProperty("postalcode")
	@NotBlank(message="user.address.postalcode.missing")
	@Size(min=4, message="user.address.postalcode.blank.short")
	private		String			postalCode;

	@Getter
	@Setter
	@JsonProperty("city")
	private 	CityDTO 		cityDTO;
	
	public AddressDTO() {
		super();
	}

	public AddressDTO(Address address, Device device, boolean detailed) {
		super(address);
		setCityDTO(new CityDTO(address.getCity(), device, false));
	}
}
