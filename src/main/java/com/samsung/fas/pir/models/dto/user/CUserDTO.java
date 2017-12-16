package com.samsung.fas.pir.models.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.address.AddressDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PFisDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PJurDTO;
import com.samsung.fas.pir.models.entity.Login;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Create user DTO
 */
@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class CUserDTO {
    @ApiObjectField(name="name", order=1)
    @Setter
    @Getter
    @JsonProperty(value="name")
    @NotBlank(message="user.name.blank")
    private		String			name;

	@ApiObjectField(name="email", order=3)
	@Setter
	@Getter
	@JsonProperty("email")
	@Email(message = "user.email.invalid")
	@NotBlank(message="user.email.blank")
	private		String			email;

    @ApiObjectField(name="login", order=2)
    @Setter
    @Getter
    @JsonProperty("login")
    @NotBlank(message="user.login.blank")
    private		String			login;

    @ApiObjectField(name="password", order=3)
    @Setter
    @Getter
    @JsonProperty("password")
    @Size(min=8, message="user.password.short")
    private		String			password;

    @ApiObjectField(name="status", order=4)
    @Setter
    @Getter
    @JsonProperty("status")
    private		boolean			active;

    @ApiObjectField(name="type", order=5)
    @Setter
    @Getter
    @JsonProperty("type")
    @NotNull(message="user.type.null")
    private EUserType type;

    @ApiObjectField(name="profile", order=6)
    @Setter
    @Getter
    @JsonProperty("profile")
    @NotBlank(message="user.profile.blank")
    private		String		profile;

    // Other properties
    @ApiObjectField(name="address", order=8)
    @Setter
    @Getter
    @JsonProperty("address")
    @NotNull(message="user.address.missing")
    @Valid
    private 	AddressDTO 	addressDTO;

    @ApiObjectField(name="pfis", order=9)
    @Setter
    @Getter
    @JsonProperty("pfis")
    @Valid
    private 	PFisDTO 	pfis;

    @ApiObjectField(name="pjur", order=10)
    @Setter
    @Getter
    @JsonProperty("pjur")
    @Valid
    private 	PJurDTO 	pjur;

    public CUserDTO() {
        // JSON
    }

    @JsonIgnore
    public User getModel() {
        User 			user 			= new User();
		Login			login			= new Login();

		login.setActive(active);
		login.setUsername(this.login);
		login.setPassword(password);

		user.setAddress(addressDTO.getModel());
        user.setLogin(login);
        user.setName(name);
        user.setType(type);
        user.setEmail(email);

        try {
            user.setAddress(addressDTO.getModel());
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }

        try {
            user.setLegalPerson(pjur.getModel());
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            try {
                user.setIndividualPerson(pfis.getModel());
            } catch (Exception ex) {
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return user;
    }
}
