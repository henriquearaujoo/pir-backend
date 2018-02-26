package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import com.samsung.fas.pir.rest.dto.address.AddressDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;
import java.util.Optional;

/*
 * Read user DTO
 */
@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RUserDTO {
    @ApiObjectField(name="id", order=0)
    @Getter
	@Setter
    @JsonProperty("id")
    private     String          id;

    @ApiObjectField(name="name", order=1)
    @Getter
	@Setter
    @JsonProperty(value="name")
    private		String			name;

	@ApiObjectField(name="email", order=3)
	@Getter
	@Setter
	@JsonProperty("email")
	private		String			email;

    @ApiObjectField(name="login", order=2)
    @Getter
	@Setter
    @JsonProperty("login")
    private		String			login;

    @ApiObjectField(name="status", order=4)
    @Getter
	@Setter
    @JsonProperty("status")
    private		boolean			active;

    @ApiObjectField(name="type", order=5)
    @Getter
	@Setter
    @JsonProperty("type")
    private EUserType type;

    @ApiObjectField(name="profile", order=6)
    @Getter
	@Setter
    @JsonProperty("profile")
    private		String			profile;

    @ApiObjectField(name="date", order=7)
    @Getter
	@Setter
    @JsonProperty("date")
    private 	Date			registerDate;

    @ApiObjectField(name="address", order=8)
    @Getter
	@Setter
    @JsonProperty("address")
    private 	AddressDTO 		addressDTO;

	@ApiObjectField(name="IndividualPerson", order=9)
	@Getter
	@Setter
	@JsonProperty("pfis")
	private 	CPFisDTO 		pfis;

	@ApiObjectField(name="org", order=10)
	@Getter
	@Setter
	@JsonProperty("pjur")
	private 	CPJurDTO 		pjur;

    private RUserDTO(User entity) {
    	setId(IDCoder.encode(entity.getUuid()));
		setName(entity.getName());
        setLogin(entity.getAccount().getUsername());
        setActive(entity.getAccount().isEnabled());
		setEmail(entity.getEmail());
        setType(entity.getType());
        setRegisterDate(entity.getRegisterDate());
        setProfile(IDCoder.encode(entity.getAccount().getProfile().getUuid()));
        setAddressDTO(AddressDTO.toDTO(entity.getAddress()));
        Optional.ofNullable(entity.getAddress()).ifPresent(item -> setAddressDTO(AddressDTO.toDTO(item)));
        Optional.ofNullable(entity.getIndividual()).ifPresent(item -> setPfis(CPFisDTO.toDTO(entity.getIndividual())));
        Optional.ofNullable(entity.getLegal()).ifPresent(item -> setPjur(CPJurDTO.toDTO(entity.getLegal())));
    }

    public static RUserDTO toDTO(User entity) {
        if (entity != null) {
            return new RUserDTO(entity);
        }
        return null;
    }
}
