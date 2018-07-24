package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@DTO(User.class)
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO<User> {
	@Setter
	@Getter
	@JsonProperty(value="name")
	@NotBlank(message="user.name.missing")
	private		String		name;

	@Setter
	@Getter
	@JsonProperty("email")
	@Email(message = "user.email.invalid")
	private		String		email;

	@Setter
	@Getter
	@JsonProperty("login")
	@NotBlank(message="user.login.missing")
	private		String		login;

	@Setter
	@Getter
	@JsonProperty("password")
	@Size(min=8, message="user.password.short")
	private		String		password;

	@Setter
	@Getter
	@JsonProperty("status")
	private		boolean		active;

	@Setter
	@Getter
	@JsonProperty("profile_id")
	@NotNull(message="profile.id.missing")
	private		UUID		profileUUID;

	@ApiModelProperty(readOnly = true)
	@Setter
	@Getter
	@JsonProperty(value = "profile", access = JsonProperty.Access.READ_ONLY)
	private		ProfileDTO	profileDTO;

	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private 	AddressDTO 	addressDTO;

	@Getter
	@Setter
	@JsonProperty("entity")
	@Valid
	private 	EntityDTO 	entityDTO;

	@Getter
	@Setter
	@JsonProperty("person")
	@Valid
	private 	PersonDTO 	personDTO;

	public UserDTO() {
		super();
	}

	public UserDTO(User user, Device device, boolean detailed) {
		super(user);
		setPassword(null);
		setActive(user.getAccount().isEnabled() && user.getAccount().isAccountNonExpired() && user.getAccount().isAccountNonLocked() && user.getAccount().isCredentialsNonExpired());
		setProfileDTO(new ProfileDTO(user.getAccount().getProfile(), device, false));
		setPersonDTO(user.getPerson() != null? new PersonDTO(user.getPerson(), device, false) : null);
		setEntityDTO(user.getEntity() != null? new EntityDTO(user.getEntity(), device, false) : null);
		setAddressDTO(detailed? user.getAddress() != null? new AddressDTO(user.getAddress(), device, false) : null : null);
		setLogin(detailed? user.getAccount().getUsername() : null);
	}

	@JsonIgnore
	@Override
	public User getModel() {
		User model = super.getModel();
		model.setAddress(getAddressDTO().getModel());
		model.getAddress().setUser(model);
		model.setEntity(getEntityDTO() != null? getEntityDTO().getModel() : null);
		model.setPerson(getPersonDTO() != null? getPersonDTO().getModel() : null);
		return model;
	}
}
