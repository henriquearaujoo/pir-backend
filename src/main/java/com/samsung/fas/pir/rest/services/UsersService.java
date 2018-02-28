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
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import com.samsung.fas.pir.rest.dto.user.CRUUserDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UsersService extends BService<User, CRUUserDTO, UserDAO, Long> {
	private final	CityDAO 			cdao;
	private final	ProfileDAO 			pdao;
	private final	PasswordEncoder		encoder;

	@Autowired
	public UsersService(UserDAO dao, ProfileDAO pdao, CityDAO cdao, PasswordEncoder encoder) {
		super(dao, User.class, CRUUserDTO.class);
		this.pdao 		= pdao;
		this.cdao		= cdao;
		this.encoder	= encoder;
	}

	@Override
	public CRUUserDTO save(CRUUserDTO create, Account principal) {
		User		model		= create.getModel();
		String		password	= create.getPassword();
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(create.getProfileID()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findOne(create.getAddress().getCityId())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));
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

		return new CRUUserDTO(dao.save(model));
	}

	@Override
	public CRUUserDTO update(CRUUserDTO update, Account principal) {
		User		model		= update.getModel();
		User		user		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("user.notfound"));
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(update.getProfileID()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findOne(update.getAddress().getCityId())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));

		if (model.getEntity() != null && model.getPerson() != null)
			throw new RESTRuntimeException("user.cannotbe.both");

		if (model.getEntity() == null && model.getPerson() == null)
			throw new RESTRuntimeException("user.data.missing");

		if (update.getPassword() != null && update.getPassword().trim().isEmpty())
			throw new RESTRuntimeException("user.login.password.empty");

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.getAddress().setCity(city);
		user.getAccount().setUsername(model.getName());
		user.getAccount().setProfile(profile);
		user.getAccount().setEnabled(update.isActive());
		user.getAccount().setLocked(!update.isActive());
		user.getAccount().setExpired(!update.isActive());
		user.getAccount().setCredentialsExpired(!update.isActive());
		user.getAccount().setPassword(update.getPassword() != null && !update.getPassword().trim().isEmpty()? encoder.encode(Hashing.sha256().hashString(update.getPassword(), StandardCharsets.UTF_8).toString()) : user.getAccount().getPassword());

		if (user.getEntity() != null) {
			user.setEntity(model.getEntity());
			user.getEntity().setId(user.getId());
			user.getEntity().setUser(user);
			user.setPerson(null);
		} else {
			user.setType(EUserType.PFIS);
			user.setPerson(model.getPerson());
			user.getPerson().setId(user.getId());
			user.getPerson().setUser(user);
			user.setEntity(null);
		}

		return new CRUUserDTO(dao.save(user));
	}
}
