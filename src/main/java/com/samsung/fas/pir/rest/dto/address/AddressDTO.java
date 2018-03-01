package com.samsung.fas.pir.rest.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samsung.fas.pir.persistence.models.entity.Address;
import com.samsung.fas.pir.utils.serializers.LongJsonDeserializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
	@Getter
	@Setter
	@JsonProperty("neighborhood")
	@NotBlank(message="user.address.neighborhood.missing")
	private		String			neighborhoodAddress;

	@Getter
	@Setter
	@JsonProperty("street")
	@NotBlank(message="user.address.street.missing")
	private		String			streetNameAddress;

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
	@JsonProperty("city")
	@JsonDeserialize(using = LongJsonDeserializer.class)
	@NotNull(message="user.address.city.missing")
	private		Long			cityId;

	@Getter
	@Setter
	@JsonProperty("postalcode")
	@NotBlank(message="user.address.postalcode.missing")
	@Size(min=4, message="user.address.postalcode.blank.short")
	private		String			postalCode;
	
	public AddressDTO() {
		super();
	}

	public AddressDTO(Address embedded) {
		setStreetNameAddress(embedded.getStreetAddress());
		setComplementAddress(embedded.getComplementAdress());
		setNumberAddress(embedded.getNumberAddress());
		setPostalCode(embedded.getPostalCode());
		setNeighborhoodAddress(embedded.getNeighborhoodAddress());
		setCityId(embedded.getCity().getId());
	}
	
	@JsonIgnore
	public Address getModel() {
		Address			addr			= new Address();
		addr.setComplementAdress(getComplementAddress());
		addr.setNeighborhoodAddress(getNeighborhoodAddress());
		addr.setNumberAddress(getNumberAddress());
		addr.setPostalCode(getPostalCode());
		addr.setStreetAddress(getStreetNameAddress());
		return addr;
	}
}
