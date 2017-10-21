package com.samsung.fas.pir.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.enums.UserType;
import com.samsung.fas.pir.models.User;

import lombok.Getter;
import lombok.Setter;

/*
 * Use to create or update an user on server side
 * Missing, blank and null fields validation are
 * made inside this class when put together with
 * @Valid annotation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	@Getter
	@JsonProperty("id")
	private		UUID			id;
	
	@Setter
	@Getter
	@JsonProperty(value="name")
	@NotEmpty(message="user.name.missing")
	@NotBlank(message="user.name.blank")
	private		String			name;

	@Setter
	@Getter
	@JsonProperty("login")
	@NotEmpty(message="user.login.empty")
	@NotBlank(message="user.login.blank")
	private		String			login;
	
	@Setter
	@Getter
	@JsonProperty("password")
	@Size(min=8, message="user.password.short")
	private		String			password;

	@Setter
	@Getter
	@JsonProperty("status")
	private		Boolean			active;

	@Setter
	@Getter
	@JsonProperty("type")
	private		UserType		type;
	
	@Setter
	@Getter
	@JsonProperty("date")
	private		Date			registerDate;
	
	// Other properties
	@Setter
	@Getter
	@JsonProperty("address")
	@NotNull(message="user.address.missing")
	@Valid
	private		AddressDTO		addressDTO;
	
	@Setter
	@Getter
	@JsonProperty("person")
	@Valid
	private		PersonDTO		personDTO;
	
	@Setter
	@Getter
	@JsonProperty("org")
	@Valid
	private		OrganizationDTO	orgDTO;
	
	private UserDTO(User entity) {
		id				= entity.getId();
		name			= entity.getName();
		login			= entity.getLogin();
		type			= entity.getType();
		active			= entity.getActive();
		registerDate	= entity.getRegisterDate();
		addressDTO		= AddressDTO.toDTO(entity.getAddress());
		personDTO		= PersonDTO.toDTO(entity.getPerson());
		orgDTO			= OrganizationDTO.toDTO(entity.getOrganization());
	}
 	
	public UserDTO() {
		// JSON
	}

	@JsonIgnore
	public User getModel() {
		User 			user 			= new User();
		user.setActive(active == null? false : active);
		user.setLogin(login);
		user.setName(name);
		user.setPassword(password);
		user.setType(type);
		
		try {
			user.setAddress(addressDTO.getModel());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
		}
		
		try {
			user.setOrganization(orgDTO.getModel());
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			try {
				user.setPerson(personDTO.getModel());
			} catch (Exception ex) {
				LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			}
		}
		return user;
	}
	
	public static User toEntity(UserDTO dto) {
		if (dto != null) {
			User 			user 			= new User();
			user.setActive(dto.active);
			user.setLogin(dto.login);
			user.setName(dto.name);
			user.setPassword(dto.password);
			user.setType(dto.type);
			return user;
		}
		return null;
	}
	
	public static UserDTO toDTO(User entity) {
		if (entity != null) {
			return new UserDTO(entity);
		}
		return null;
	}
}
