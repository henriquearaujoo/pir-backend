package com.samsung.fas.pir.models.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.models.dto.address.AddressDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PFisDTO;
import com.samsung.fas.pir.models.dto.user.typemodel.PJurDTO;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.models.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/*
 * Update Uer DTO
 */
@ApiObject
public class UUserDTO {
	@ApiObjectField(name="id",required=false, order=0)
	@Getter
	@JsonProperty("id")
	private		String			id;

	@ApiObjectField(name="name", order=1)
	@Setter
	@Getter
	@JsonProperty(value="name")
	@NotEmpty(message="user.name.missing")
	@NotBlank(message="user.name.blank")
	private		String			name;

	@ApiObjectField(name="login", order=2)
	@Setter
	@Getter
	@JsonProperty("login")
	@NotEmpty(message="user.login.empty")
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
	private UserType type;

	@ApiObjectField(name="profile", order=6)
	@Setter
	@Getter
	@JsonProperty("profile")
	@NotNull(message="user.profile.null")
	@NotEmpty(message="user.profile.empty")
	@NotBlank(message="user.profile.blank")
	private		String		profile;

	@ApiObjectField(name="date", order=7)
	@Setter
	@Getter
	@JsonProperty("date")
	private Date registerDate;

	// Other properties
	@ApiObjectField(name="address", order=8)
	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private AddressDTO addressDTO;

	@ApiObjectField(name="pfis", order=9)
	@Setter
	@Getter
	@JsonProperty("pfis")
	@Valid
	private PFisDTO pfis;

	@ApiObjectField(name="pjur", order=10)
	@Setter
	@Getter
	@JsonProperty("pjur")
	@Valid
	private PJurDTO pjur;

	@JsonIgnore
	public User getModel() {
		User 			user 			= new User();
		user.setActive(active);
		user.setLogin(login);
		user.setName(name);
		user.setPassword(password);
		user.setType(type);
		user.setGuid(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(id), StandardCharsets.UTF_8)));

		try {
			user.setAddress(addressDTO.getModel());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
		}

		try {
			user.setOrganization(pjur.getModel());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			try {
				user.setPerson(pfis.getModel());
			} catch (Exception ex) {
				LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			}
		}
		return user;
	}
}
