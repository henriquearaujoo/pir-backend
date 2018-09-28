package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.dao.AgentDAO;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserBO extends BaseBO<User, UserDAO, UserDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ConservationUnityBO		unityBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ProfileBO				profileBO;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	private 	AgentDAO 				agentDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PasswordEncoder			encoder;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper 			mapper;

	@Autowired
	public UserBO(UserDAO dao, AgentDAO agentDAO, ConservationUnityBO unityBO, ProfileBO profileBO, PasswordEncoder encoder, @Lazy ModelMapper mapper) {
		super(dao);
		setUnityBO(unityBO);
		setProfileBO(profileBO);
		setEncoder(encoder);
		setMapper(mapper);
		setAgentDAO(agentDAO);
	}

	public Collection<UserDTO> findAllAgents(Device device, UserDetails details) {
		return getDao().findAllAgents().stream().map(item -> new UserDTO(item, device, false)).collect(Collectors.toList());
	}

	public Collection<UserDTO> findAllAgents(Predicate predicate, Device device, UserDetails details) {
		return getDao().findAllAgents(predicate).stream().map(item -> new UserDTO(item, device, false)).collect(Collectors.toList());
	}

	public Page<UserDTO> findAllAgents(Pageable pageable, Device device, UserDetails details) {
		return getDao().findAllAgents(pageable).map(item -> new UserDTO((User) item, device, false));
	}

	public Page<UserDTO> findAllAgents(Predicate predicate, Pageable pageable, Device device, UserDetails details) {
		return getDao().findAllAgents(predicate, pageable).map(item -> new UserDTO((User) item, device, false));
	}

	@Override
	public UserDTO save(UserDTO create, Device device, UserDetails principal) {
		User		model		= create.getModel();
		String		password	= create.getPassword();
		Profile	 	profile		= getProfileBO().getDao().findOne(create.getProfileUUID());
		City 		city		= getUnityBO().getCityDAO().findOne(create.getAddressDTO().getCityUUID());
		Account 	account		= new Account();

		if (model.getEntity() != null && model.getPerson() != null)
			throw new ServiceException("user.cannot.be.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new ServiceException("user.data.missing");

		if (create.getPassword() == null || create.getPassword().trim().isEmpty())
			throw new ServiceException("user.password.empty");

		account.setUsername(create.getLogin().toLowerCase().replaceAll("\\s", "").replaceAll("\\t", ""));
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
				model.getPerson().getAgent().setCode(getAgentDAO().getSequentialCode(model.getPerson().getAgent().getUnity().getAbbreviation().concat("A")));
				model.getPerson().getAgent().setCity(getUnityBO().getCityDAO().findOne(create.getPersonDTO().getAgentDTO().getCityDTO().getUuid()));
				model.getPerson().getAgent().setUnity(getUnityBO().getDao().findOne(model.getPerson().getAgent().getUnity().getUuid()));
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
			throw new ServiceException("user.cannot.be.both");

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
		user.getAccount().setUsername(update.getLogin().toLowerCase().replaceAll("\\s", "").replaceAll("\\t", ""));
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
			user.getPerson().setUser(user);

			if (user.getPerson() == null) {
				user.setPerson(model.getPerson());
			}

			if (model.getPerson().getAgent() != null) {
				if (user.getPerson().getAgent() == null) {
					model.getPerson().getAgent().setPerson(model.getPerson());
					model.getPerson().getAgent().setCode(getAgentDAO().getSequentialCode(model.getPerson().getAgent().getUnity().getAbbreviation().concat("A")));
					model.getPerson().getAgent().setCity(getUnityBO().getCityDAO().findOne(update.getPersonDTO().getAgentDTO().getCityDTO().getUuid()));
					model.getPerson().getAgent().setUnity(getUnityBO().getDao().findOne(model.getPerson().getAgent().getUnity().getUuid()));
					user.getPerson().setAgent(model.getPerson().getAgent());
				} else {
					getMapper().map(model.getPerson().getAgent(), user.getPerson().getAgent());
					user.getPerson().getAgent().setPerson(user.getPerson());
					user.getPerson().getAgent().setCity(getUnityBO().getCityDAO().findOne(update.getPersonDTO().getAgentDTO().getCityDTO().getUuid()));
					user.getPerson().getAgent().setUnity(getUnityBO().getDao().findOne(model.getPerson().getAgent().getUnity().getUuid()));
				}
			} else {
				if (user.getPerson() == null) {
					user.setEntity(null);
					user.setPerson(model.getPerson());
				} else {
					getMapper().map(model.getPerson(), user.getPerson());
				}
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

	public UserDTO enable(UUID id, Device device) {
		User		user		= getDao().findOne(id);
		user.getAccount().setEnabled(!user.getAccount().isEnabled());
		user.getAccount().setAccountNonLocked(!user.getAccount().isAccountNonLocked());
		user.getAccount().setAccountNonExpired(!user.getAccount().isAccountNonExpired());
		user.getAccount().setCredentialsNonExpired(!user.getAccount().isCredentialsNonExpired());
		return new UserDTO(getDao().save(user), device, true);
	}
}
