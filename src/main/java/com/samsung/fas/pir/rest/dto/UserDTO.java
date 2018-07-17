package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.graph.annotations.DTO;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.rest.dto.base.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mobile.device.Device;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@DTO(User.class)
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

	@Setter
	@Getter
	@JsonProperty("profile")
	private		ProfileDTO	profileDTO;

	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private 	AddressDTO 	addressDTO;

	@Getter
	@Setter
	@JsonProperty("person")
	@Valid
	private 	PersonDTO 	personDTO;

	@Getter
	@Setter
	@JsonProperty("entity")
	@Valid
	private 	EntityDTO 	entityDTO;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter
	@JsonProperty("visits")
	private 	Collection<VisitDTO>	visitsDTO;

	@ApiModelProperty(value = "For AGENT Only")
	@Getter
	@Setter
	@JsonProperty("latitude")
	private 	Double					latitude;

	@ApiModelProperty(value = "For AGENT Only")
	@Getter
	@Setter
	@JsonProperty("longitude")
	private 	Double					longitude;

	public UserDTO() {
		super();
	}

	public UserDTO(User user, Device device, boolean detailed) {
		super(user);
		setPassword(null);
		setActive(user.getAccount().isEnabled() && user.getAccount().isAccountNonExpired() && user.getAccount().isAccountNonLocked() && user.getAccount().isCredentialsNonExpired());
		setProfileDTO(new ProfileDTO(user.getAccount().getProfile(), device, false));
		setPersonDTO(detailed && user.getPerson() != null? new PersonDTO(user.getPerson(), false) : null);
		setEntityDTO(detailed && user.getEntity() != null? new EntityDTO(user.getEntity(), device, false) : null);
		setAddressDTO(detailed? user.getAddress() != null? new AddressDTO(user.getAddress(), device, false) : null : null);
		setLogin(detailed? user.getAccount().getUsername() : null);
//		setVisitsDTO(detailed && user.getVisits() != null? user.getVisits().stream().map(item -> new VisitDTO(item, device, false)).collect(Collectors.toList()) : null);
	}

	@JsonIgnore
	public User getModel() {
		User model = super.getModel();
		model.setAddress(getAddressDTO().getModel());
		model.getAddress().setUser(model);
		model.setEntity(getEntityDTO() != null? getEntityDTO().getModel() : null);
		model.setPerson(getPersonDTO() != null? getPersonDTO().getModel() : null);
		return model;
	}
}
