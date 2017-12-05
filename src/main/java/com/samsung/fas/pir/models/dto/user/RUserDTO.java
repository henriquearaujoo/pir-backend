package com.samsung.fas.pir.models.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.address.AddressDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PFisDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PJurDTO;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.util.Base64Utils;

import java.util.Date;

/*
 * Read user DTO
 */
@ApiObject
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RUserDTO {
    @ApiObjectField(name="id", order=0)
    @Getter
    @JsonProperty("id")
    private     String          id;

    @ApiObjectField(name="name", order=1)
    @Getter
    @JsonProperty(value="name")
    private		String			name;

	@ApiObjectField(name="email", order=3)
	@Setter
	@Getter
	@JsonProperty("email")
	private		String			email;

    @ApiObjectField(name="login", order=2)
    @Getter
    @JsonProperty("login")
    private		String			login;

    @ApiObjectField(name="status", order=4)
    @Getter
    @JsonProperty("status")
    private		boolean			active;

    @ApiObjectField(name="type", order=5)
    @Getter
    @JsonProperty("type")
    private EUserType type;

    @ApiObjectField(name="profile", order=6)
    @Getter
    @JsonProperty("profile")
    private		String			profile;

    @ApiObjectField(name="date", order=7)
    @Getter
    @JsonProperty("date")
    private 	Date			registerDate;

    @ApiObjectField(name="address", order=8)
    @Getter
    @JsonProperty("address")
    private 	AddressDTO 		addressDTO;

	@ApiObjectField(name="person", order=9)
	@Getter
	@JsonProperty("pfis")
	private 	PFisDTO 		pfis;

	@ApiObjectField(name="org", order=10)
	@Getter
	@JsonProperty("pjur")
	private 	PJurDTO 		pjur;


    private RUserDTO(User entity) {
        id				= Base64Utils.encodeToUrlSafeString(entity.getGuid().toString().getBytes());
        name			= entity.getName();
        login			= entity.getLogin();
        email			= entity.getEmail();
        type			= entity.getType();
        active			= entity.isActive();
        registerDate	= entity.getRegisterDate();
        profile			= Base64Utils.encodeToUrlSafeString(entity.getProfile().getGuid().toString().getBytes());
        addressDTO		= AddressDTO.toDTO(entity.getAddress());
        pfis			= PFisDTO.toDTO(entity.getPerson());
        pjur			= PJurDTO.toDTO(entity.getOrganization());
    }

    public static RUserDTO toDTO(User entity) {
        if (entity != null) {
            return new RUserDTO(entity);
        }
        return null;
    }
}
