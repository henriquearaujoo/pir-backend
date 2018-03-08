package com.samsung.fas.pir.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.address.AddressDTO;
import com.samsung.fas.pir.rest.dto.profile.CRUProfileDTO;
import com.samsung.fas.pir.utils.IDCoder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRUUserDTO {
	@Getter
	@Setter
	@JsonProperty("id")
	private		String			id;

	@Setter
	@Getter
	@JsonProperty(value="name")
	@NotBlank(message="user.name.missing")
	private		String			name;

	@Setter
	@Getter
	@JsonProperty("email")
	@Email(message = "user.email.invalid")
	private		String			email;

	@Setter
	@Getter
	@JsonProperty("login")
	@NotBlank(message="user.login.missing")
	private		String			login;

	@Setter
	@Getter
	@JsonProperty("password")
	@Size(min=8, message="user.password.short")
	private		String			password;

	@Setter
	@Getter
	@JsonProperty("status")
	private		boolean			active;

	@Setter
	@Getter
	@JsonProperty("profile_id")
	@NotBlank(message="profile.id.missing")
	private		String			profileID;

	@Setter
	@Getter
	@JsonProperty("profile")
	private 	CRUProfileDTO 	profile;

	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private 	AddressDTO 		address;

	@Getter
	@Setter
	@JsonProperty("person")
	@Valid
	private 	CRUPersonDTO 	person;

	@Getter
	@Setter
	@JsonProperty("entity")
	@Valid
	private 	CRUEntityDTO 	entity;

	public CRUUserDTO() {
		super();
	}

	public CRUUserDTO(User user, boolean detailed) {
		setId(IDCoder.encode(user.getUuid()));
		setName(user.getName());
		setEmail(user.getEmail());
		setLogin(user.getAccount().getUsername());
		setPassword(null);
		setActive(user.getAccount().isEnabled() && user.getAccount().isAccountNonExpired() && user.getAccount().isAccountNonLocked() && user.getAccount().isCredentialsNonExpired());
		setPerson(user.getPerson() != null? new CRUPersonDTO(user.getPerson(), false) : null);
		setEntity(user.getEntity() != null? new CRUEntityDTO(user.getEntity(), false) : null);
		setAddress(user.getAddress() != null? new AddressDTO(user.getAddress(), false) : null);
		setProfile(new CRUProfileDTO(user.getAccount().getProfile(), false));
	}

	@JsonIgnore
	public User getModel() {
		User model = new User();
		model.setUuid(getId() != null && !getId().trim().isEmpty()? IDCoder.decode(getId()) : null);
		model.setName(getName());
		model.setEmail(getEmail());
		model.setAddress(getAddress().getModel());
		model.getAddress().setUser(model);
		model.setEntity(getEntity() != null? getEntity().getModel() : null);
		model.setPerson(getPerson() != null? getPerson().getModel() : null);
		return model;
	}

}
