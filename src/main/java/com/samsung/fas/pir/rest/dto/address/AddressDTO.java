package com.samsung.fas.pir.rest.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samsung.fas.pir.persistence.models.entity.Address;
import com.samsung.fas.pir.utils.serializers.LongJsonDeserializer;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
	@ApiObjectField(name="neighborhood")
	@Getter
	@JsonProperty("neighborhood")
	@NotBlank(message="user.address.neighborhood.missing")
	private		String			neighborhoodAddress;
	
	@ApiObjectField(name="street")
	@Getter
	@JsonProperty("street")
	@NotBlank(message="user.address.street.missing")
	private		String			streetNameAddress;
	
	@ApiObjectField(name="complement", required=false)
	@Getter
	@JsonProperty("complement")
	private		String			complementAddress;
	
	@ApiObjectField(name="number")
	@Getter
	@JsonProperty("number")
	@NotBlank(message="user.address.number.missing")
	private		String			numberAddress;
	
	@ApiObjectField(name="city")
	@Getter
	@JsonProperty("city")
	@JsonDeserialize(using = LongJsonDeserializer.class)
	@NotNull(message="user.address.city.missing")
	private		Long			cityId;
	
	@ApiObjectField(name="postalcode")
	@Getter
	@JsonProperty("postalcode")
	@NotBlank(message="user.address.postalcode.missing")
	@Size(min=4, message="user.address.postalcode.blank.short")
	private		String			postalCode;
	
	private AddressDTO(Address embedded) {
		streetNameAddress		= embedded.getStreetAddress();
		complementAddress		= embedded.getComplementAdress();
		numberAddress			= embedded.getNumberAddress();
		postalCode				= embedded.getPostalCode();
		neighborhoodAddress		= embedded.getNeighborhoodAddress();
		cityId					= embedded.getCity().getId();
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
	
	public static AddressDTO toDTO(Address entity) {
		if (entity != null) {
			return new AddressDTO(entity);
		}
		return null;
	}
}
