package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.UserDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.rest.dto.UserDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Service
public class UserBO extends BaseBO<User, UserDAO, UserDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CityDAO			cdao;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		ProfileDAO		pdao;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private 	UserDAO			userDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		PasswordEncoder	encoder;

	@Autowired
	public UserBO(UserDAO dao) {
		super(dao);
	}

	@Override
	public UserDTO save(UserDTO create, UserDetails principal) {
		User		model		= create.getModel();
		String		password	= create.getPassword();
		Profile	 	profile		= pdao.findOne(create.getProfileUUID());
		City 		city		= cdao.findOne(create.getAddress().getCityUUID());
		Account 	account		= new Account();

		if (model.getEntity() != null && model.getPerson() != null)
			throw new RESTException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new RESTException("user.data.missing");

		if (create.getPassword() == null || create.getPassword().trim().isEmpty())
			throw new RESTException("user.password.empty");

		account.setUsername(create.getLogin());
		account.setPassword(encoder.encode(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()));
		account.setEnabled(create.isActive());
		account.setCredentialsNonExpired(create.isActive());
		account.setAccountNonExpired(create.isActive());
		account.setAccountNonLocked(create.isActive());
		account.setUser(model);
		account.setProfile(profile);
		model.getAddress().setCity(city);
		model.setAccount(account);

		if (model.getPerson() != null)
			model.getPerson().setUser(model);
		else
			model.getEntity().setUser(model);

		return new UserDTO(getDao().save(model), true);
	}

	@CacheEvict("accountsCache")
	@Override
	public UserDTO update(UserDTO update, UserDetails principal) {
		User		model		= update.getModel();
		User		user		= getDao().findOne(model.getUuid());
		Profile		profile		= pdao.findOne(update.getProfileUUID());
		City		city		= cdao.findOne(update.getAddress().getCityUUID());

		if (model.getEntity() != null && model.getPerson() != null)
			throw new RESTException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new RESTException("user.data.missing");

		if (update.getPassword() != null && update.getPassword().trim().isEmpty())
			throw new RESTException("user.login.password.empty");

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.setLatitude(model.getLatitude());
		user.setLongitude(model.getLongitude());
		user.getAddress().setComplementAdress(model.getAddress().getComplementAdress());
		user.getAddress().setNeighborhoodAddress(model.getAddress().getNeighborhoodAddress());
		user.getAddress().setNumberAddress(model.getAddress().getNumberAddress());
		user.getAddress().setPostalCode(model.getAddress().getPostalCode());
		user.getAddress().setStreetAddress(model.getAddress().getStreetAddress());
		user.getAddress().setCity(city);
		user.getAccount().setUsername(update.getLogin());
		user.getAccount().setProfile(profile);
		user.getAccount().setEnabled(update.isActive());
		user.getAccount().setAccountNonLocked(update.isActive());
		user.getAccount().setAccountNonExpired(update.isActive());
		user.getAccount().setCredentialsNonExpired(update.isActive());
		user.getAccount().setPassword(update.getPassword() != null && !update.getPassword().trim().isEmpty()? encoder.encode(Hashing.sha256().hashString(update.getPassword(), StandardCharsets.UTF_8).toString()) : user.getAccount().getPassword());

		if (model.getEntity() != null) {
			model.getEntity().setId(user.getId());
			user.setEntity(model.getEntity());
			user.getEntity().setUser(user);
			user.setPerson(null);
		} else {
			model.getPerson().setId(user.getId());
			user.setPerson(model.getPerson());
			user.getPerson().setUser(user);
			user.setEntity(null);
		}

		return new UserDTO(getDao().save(user), true);
	}

	@Override
	public Collection<UserDTO> save(Collection<UserDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<UserDTO> update(Collection<UserDTO> update, UserDetails details) {
		return null;
	}
}
