package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.auth.AuthManager;
import com.samsung.fas.pir.login.auth.JWToken;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.providers.DeviceProvider;
import com.samsung.fas.pir.login.rest.service.AccountService;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.UsersDAO;
import com.samsung.fas.pir.persistence.models.entity.*;
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import com.samsung.fas.pir.rest.dto.user.*;
import com.samsung.fas.pir.rest.dto.user.base.CUserBaseDTO;
import com.samsung.fas.pir.rest.dto.user.base.UUserBaseDTO;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {
	@Autowired	private 	UsersDAO 			udao;
	@Autowired	private 	CityDAO 			cdao;
	@Autowired	private 	ProfileDAO 			pdao;
	@Autowired	private 	PasswordEncoder		encoder;

	@Autowired	private 	JWToken				token;
	@Autowired 	private 	AuthManager 		manager;
	@Autowired 	private 	AccountService 		service;
	@Autowired 	private 	DeviceProvider		provider;

	public List<RUserDTO> findAll() {
		return udao.findAll().stream().map(RUserDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RUserDTO> findAll(Pageable pageable) {
		return udao.findAll(pageable).map(RUserDTO::toDTO);
	}

	public List<RUserDTO> findAll(Predicate predicate) {
		return udao.findAll(predicate).stream().map(RUserDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RUserDTO> findAll(Predicate predicate, Pageable pageable) {
		return udao.findAll(predicate, pageable).map(RUserDTO::toDTO);
	}

	public RUserDTO findByID(String id) {
		User user = udao.findOne(IDCoder.decodeUUID(id));
		if (user == null)
			throw new RESTRuntimeException("profile.notfound");
		return RUserDTO.toDTO(user);
	}

	// region create and update model
	// region create model
	public void save(CUserBaseDTO user) {
		CPJurDTO pjur;
		CPFisDTO pfis;

		// Login already exists
		if(udao.findOneByLogin(user.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");

		if(udao.findOneByEmail(user.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");

		// Type of user
		if (user instanceof CPFisDTO) {
			pfis = (CPFisDTO) user;
			// Verify if CPF exists in database
			if (udao.findOneByCpf(pfis.getCpf()) != null)
				throw new RESTRuntimeException("user.type.pfis.cpf.exists");
		} else if (user instanceof CPJurDTO) {
			pjur = (CPJurDTO) user;
			// Verify if CPF exists in database
			if (udao.findOneByCnpj(pjur.getCnpj()) != null)
				throw new RESTRuntimeException("user.type.pjur.cnpj.exists");
		} else {
			throw new RESTRuntimeException("user.type.data.missing");
		}

		// Redudant
		if (user.getPassword() == null || user.getPassword().replaceAll("\\s+","").isEmpty())
			throw new RESTRuntimeException("user.login.password.empty");
		
		// City Validation
		City city = cdao.findCityByID(user.getAddressDTO().getCityId());
		if (city == null) 
			throw new RESTRuntimeException("user.address.city.invalid");
		
		// Profile Validation
		Profile profile = pdao.findOne(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(user.getProfile()), StandardCharsets.UTF_8)));
		if (profile == null)
			throw new RESTRuntimeException("user.profile.notfound");
		
		// All above conditions are satisfied
		User 	model		= user.getModel();
		String	password	= model.getAccount().getPassword();

		model.getAccount().setPassword(encoder.encode(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()));
		model.getAddress().setCity(city);
		model.getAddress().setUser(model);
		model.getAccount().setUser(model);
		model.getAccount().setProfile(profile);
		udao.save(model);
	}
	// endregion

	// region update model
	public String update(UUserBaseDTO dto, Account account, Device device) {
		User 				entity			= udao.findOne(dto.getModel().getGuid());
		User 				model 			= dto.getModel();
		String				tokien;

		// Verify if user exists
		if (entity == null)
			throw new RESTRuntimeException("user.id.notfound");

		// If email not equal to persited and email exists
		if(!dto.getEmail().equalsIgnoreCase(entity.getEmail()) && udao.findOneByEmail(dto.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");

		// Verify types
		if (dto.getType() == EUserType.PFIS) {
			// Verify instance type
			if (dto instanceof UPFisDTO) {
				// Verify if CPF exists in database
				String ecpf = entity instanceof IndividualPerson ? ((IndividualPerson) model).getCpf() : "";
				String ucpf = ((UPFisDTO) dto).getCpf();
				if (!ecpf.equalsIgnoreCase(ucpf) && udao.findOneByCpf(ucpf) != null)
					throw new RESTRuntimeException("user.type.pfis.cpf.exists");
			} else {
				throw new RESTRuntimeException("user.type.mismatch");
			}
		} else if (dto.getType() == EUserType.PJUR) {
			if (dto instanceof UPJurDTO) {
				// Verify if CNPJ exists in database
				String ecnpj = entity instanceof LegalPerson ? ((LegalPerson) model).getCnpj() : "";
				String ucnpj = ((UPJurDTO) dto).getCnpj();
				if (!ecnpj.equalsIgnoreCase(ucnpj) && udao.findOneByCnpj(ucnpj) != null)
					throw new RESTRuntimeException("user.type.legalPerson.cnpj.exists");
			} else {
				throw new RESTRuntimeException("user.type.mismatch");
			}
		}

		// Verify if login already exists
		if(!entity.getAccount().getUsername().equalsIgnoreCase(dto.getLogin()) && udao.findOneByLogin(dto.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");

		// Password validation
		if (dto.getPassword() != null && dto.getPassword().replaceAll("\\s+","").isEmpty())
			throw new RESTRuntimeException("user.login.password.empty");

		// If password is null or empty, then set old password
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			model.getAccount().setPassword(entity.getAccount().getPassword());
		} else {
			// Set new password
			model.getAccount().setPassword(encoder.encode(Hashing.sha256().hashString(dto.getPassword(), StandardCharsets.UTF_8).toString()));
		}

		// City Validation
		City city = cdao.findCityByID(dto.getAddressDTO().getCityId());
		if (city == null)
			throw new RESTRuntimeException("user.address.city.invalid");

		// Profile Validation
		Profile profile = pdao.findOne(IDCoder.decodeUUID(dto.getProfile()));
		if (profile == null)
			throw new RESTRuntimeException("user.profile.notfound");

		// All above conditions are satisfied
		model.setId(entity.getId());
		model.getAddress().setCity(city);
		model.getAccount().setProfile(profile);

		model.getAddress().setId(entity.getAddress().getId());
		model.getAccount().setId(entity.getAccount().getId());

		model.getAccount().setUser(model);
		model.getAddress().setUser(model);

		// If login changes, generate new token
		// Verify if editing is for same user
		if (udao.save(model) != null && model.getId() == account.getId()) {
			try {
				Authentication	authentication 	= manager.reAuthenticate(new UsernamePasswordAuthenticationToken(model.getAccount().getUsername(), model.getAccount().getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return token.generateToken((Account) authentication.getPrincipal(), device);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	// endregion
	// endregion
}
