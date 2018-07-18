package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.UserDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.rest.dto.UserDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Service
public class UserBO extends BaseBO<User, UserDAO, UserDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ConservationUnityBO		unityBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ProfileBO				profileBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PasswordEncoder			encoder;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper 			mapper;

	@Autowired
	public UserBO(UserDAO dao, ConservationUnityBO unityBO, ProfileBO profileBO, PasswordEncoder encoder, ModelMapper mapper) {
		super(dao);
		setUnityBO(unityBO);
		setProfileBO(profileBO);
		setEncoder(encoder);
		setMapper(mapper);
	}

	@Override
	public UserDTO save(UserDTO create, Device device, UserDetails principal) {
		User		model		= create.getModel();
		String		password	= create.getPassword();
		Profile	 	profile		= getProfileBO().getDao().findOne(create.getProfileUUID());

		City 		city		= getUnityBO().getCityDAO().findOne(create.getAddressDTO().getCityUUID());
		Account 	account		= new Account();

		if (model.getEntity() != null && model.getPerson() != null)
			throw new ServiceException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new ServiceException("user.data.missing");

		if (create.getPassword() == null || create.getPassword().trim().isEmpty())
			throw new ServiceException("user.password.empty");

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

		if (model.getPerson() != null) {
			model.getPerson().setUser(model);
			if (model.getPerson().getAgent() != null) {
				model.getPerson().getAgent().setPerson(model.getPerson());
				model.getPerson().getAgent().setUnity(getUnityBO().getDao().findOne(model.getPerson().getAgent().getUnity().getUuid()));
				model.getPerson().getAgent().setCity(getUnityBO().getCityDAO().findOne(model.getPerson().getAgent().getCity().getUuid()));
			}
		} else {
			model.getEntity().setUser(model);
		}

		return new UserDTO(getDao().save(model), device, true);
	}

//	@CacheEvict("accountsCache")
	@Override
	public UserDTO update(UserDTO update, Device device, UserDetails principal) {
		User		model		= update.getModel();
		User		user		= getDao().findOne(model.getUuid());
		Profile		profile		= getProfileBO().getDao().findOne(update.getProfileUUID());
		City		city		= getUnityBO().getCityDAO().findOne(update.getAddressDTO().getCityUUID());

		if (model.getEntity() != null && model.getPerson() != null)
			throw new ServiceException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new ServiceException("user.data.missing");

		if (update.getPassword() != null && update.getPassword().trim().isEmpty())
			throw new ServiceException("user.login.password.empty");

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.getAddress().setComplementAddress(model.getAddress().getComplementAddress());
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
			user.getPerson().setAgent(model.getPerson().getAgent());
			if (model.getPerson().getAgent() != null) {
				getMapper().map(model.getPerson().getAgent(), user.getPerson().getAgent());
				user.getPerson().getAgent().setPerson(user.getPerson());
				user.getPerson().getAgent().setUnity(getUnityBO().getDao().findOne(model.getPerson().getAgent().getUnity().getUuid()));
				user.getPerson().getAgent().setCity(getUnityBO().getCityDAO().findOne(model.getPerson().getAgent().getCity().getUuid()));
			}
			user.setEntity(null);
		}

		return new UserDTO(getDao().save(user), device, true);
	}

	@Override
	public Collection<UserDTO> save(Collection<UserDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<UserDTO> update(Collection<UserDTO> update, Device device, UserDetails details) {
		return null;
	}
}
