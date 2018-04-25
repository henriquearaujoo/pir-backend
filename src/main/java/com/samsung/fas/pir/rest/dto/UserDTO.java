package com.samsung.fas.pir.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		UUID		uuid;

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
	private		ProfileDTO	profile;

	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private 	AddressDTO 	address;

	@Getter
	@Setter
	@JsonProperty("person")
	@Valid
	private 	PersonDTO 	person;

	@Getter
	@Setter
	@JsonProperty("entity")
	@Valid
	private 	EntityDTO 	entity;

	@ApiModelProperty(readOnly = true, hidden = true)
	@Getter
	@Setter(onMethod = @__({@JsonIgnore}))
	@JsonProperty("visits")
	private 	Collection<VisitDTO>	visits;

	@ApiModelProperty(value = "For AGENT Only")
	@Getter
	@Setter
	@JsonProperty("latitude")
	private 	Double					latidute;

	@ApiModelProperty(value = "For AGENT Only")
	@Getter
	@Setter
	@JsonProperty("longitude")
	private 	Double					longitude;

	public UserDTO() {
		super();
	}

	public UserDTO(User user, boolean detailed) {
		setUuid(user.getUuid());
		setName(user.getName());
		setEmail(user.getEmail());
		setLogin(user.getAccount().getUsername());
		setPassword(null);
		setActive(user.getAccount().isEnabled() && user.getAccount().isAccountNonExpired() && user.getAccount().isAccountNonLocked() && user.getAccount().isCredentialsNonExpired());
		setPerson(user.getPerson() != null? new PersonDTO(user.getPerson(), false) : null);
		setEntity(user.getEntity() != null? new EntityDTO(user.getEntity(), false) : null);
		setProfile(new ProfileDTO(user.getAccount().getProfile(), false));
		setAddress(user.getAddress() != null? new AddressDTO(user.getAddress(), false) : null);
		setLatidute(user.getLatitude());
		setLongitude(user.getLongitude());
		setVisits(user.getVisits() != null? user.getVisits().stream().map(item -> new VisitDTO(item, false)).collect(Collectors.toList()) : null);
	}

	@JsonIgnore
	public User getModel() {
		User model = new User();
		model.setUuid(getUuid());
		model.setName(getName());
		model.setEmail(getEmail());
		model.setAddress(getAddress().getModel());
		model.getAddress().setUser(model);
		model.setEntity(getEntity() != null? getEntity().getModel() : null);
		model.setPerson(getPerson() != null? getPerson().getModel() : null);
		model.setLatitude(getLatidute());
		model.setLongitude(getLongitude());
		return model;
	}

}
