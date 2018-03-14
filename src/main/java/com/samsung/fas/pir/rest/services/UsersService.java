package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.UserDAO;
import com.samsung.fas.pir.persistence.models.entity.City;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.rest.dto.UserDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UsersService extends BService<User, UserDTO, UserDAO, Long> {
	private final	CityDAO 			cdao;
	private final	ProfileDAO 			pdao;
	private final	PasswordEncoder		encoder;

	@Autowired
	public UsersService(UserDAO dao, ProfileDAO pdao, CityDAO cdao, PasswordEncoder encoder) {
		super(dao, User.class, UserDTO.class);
		this.pdao 		= pdao;
		this.cdao		= cdao;
		this.encoder	= encoder;
	}

	@Override
	public UserDTO save(UserDTO create, UserDetails principal) {
		User		model		= create.getModel();
		String		password	= create.getPassword();
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(create.getProfileID()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findOne(IDCoder.decode(create.getAddress().getCityId()))).orElseThrow(() -> new RESTRuntimeException("city.notfound"));
		Account		account		= new Account();

		if (model.getEntity() != null && model.getPerson() != null)
			throw new RESTRuntimeException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new RESTRuntimeException("user.data.missing");

		if (create.getPassword() == null || create.getPassword().trim().isEmpty())
			throw new RESTRuntimeException("user.password.empty");

		account.setUsername(create.getLogin());
		account.setPassword(encoder.encode(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()));
		account.setEnabled(create.isActive());
		account.setCredentialsExpired(!create.isActive());
		account.setExpired(!create.isActive());
		account.setLocked(!create.isActive());
		account.setUser(model);
		account.setProfile(profile);
		model.getAddress().setCity(city);
		model.setAccount(account);

		if (model.getPerson() != null)
			model.getPerson().setUser(model);
		else
			model.getEntity().setUser(model);

		return new UserDTO(dao.save(model), true);
	}

	@Override
	public UserDTO update(UserDTO update, UserDetails principal) {
		User		model		= update.getModel();
		User		user		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("user.notfound"));
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(update.getProfileID()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findOne(IDCoder.decode(update.getAddress().getCityId()))).orElseThrow(() -> new RESTRuntimeException("city.notfound"));

		if (model.getEntity() != null && model.getPerson() != null)
			throw new RESTRuntimeException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new RESTRuntimeException("user.data.missing");

		if (update.getPassword() != null && update.getPassword().trim().isEmpty())
			throw new RESTRuntimeException("user.login.password.empty");

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.getAddress().setComplementAdress(model.getAddress().getComplementAdress());
		user.getAddress().setNeighborhoodAddress(model.getAddress().getNeighborhoodAddress());
		user.getAddress().setNumberAddress(model.getAddress().getNumberAddress());
		user.getAddress().setPostalCode(model.getAddress().getPostalCode());
		user.getAddress().setStreetAddress(model.getAddress().getStreetAddress());
		user.getAddress().setCity(city);
		user.getAccount().setUsername(update.getLogin());
		user.getAccount().setProfile(profile);
		user.getAccount().setEnabled(update.isActive());
		user.getAccount().setLocked(!update.isActive());
		user.getAccount().setExpired(!update.isActive());
		user.getAccount().setCredentialsExpired(!update.isActive());
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

		return new UserDTO(dao.save(user), true);
	}
}
