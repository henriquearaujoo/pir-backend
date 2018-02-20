package com.samsung.fas.pir.rest.services;

import com.google.common.hash.Hashing;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.auth.AuthManager;
import com.samsung.fas.pir.login.auth.JWToken;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.UsersDAO;
import com.samsung.fas.pir.persistence.models.entity.City;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import com.samsung.fas.pir.rest.dto.user.*;
import com.samsung.fas.pir.rest.dto.user.base.CUserBaseDTO;
import com.samsung.fas.pir.rest.dto.user.base.UUserBaseDTO;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {
	private final	UsersDAO 			udao;
	private final	CityDAO 			cdao;
	private final	ProfileDAO 			pdao;
	private final	PasswordEncoder		encoder;

	@Autowired
	public UsersService(UsersDAO udao, CityDAO cdao, ProfileDAO pdao, PasswordEncoder encoder, JWToken token, AuthManager manager) {
		this.udao		= udao;
		this.cdao		= cdao;
		this.pdao		= pdao;
		this.encoder	= encoder;
	}

	public RUserDTO findOne(UUID id) {
		return RUserDTO.toDTO(udao.findOne(id));
	}

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

	public RUserDTO save(CUserBaseDTO dto) {
		User 		model		= dto.getModel();
		String		password	= model.getAccount().getPassword();
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(dto.getProfile()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findCityByID(dto.getAddress().getCityId())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));

		// Login already exists
		if(udao.findOneByLogin(dto.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");

		if(udao.findOneByEmail(dto.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");

		// Type of user
		if (dto instanceof CPFisDTO) {
			CPFisDTO pfis = (CPFisDTO) dto;
			// Verify if CPF exists in database
			if (udao.findOneByCpf(pfis.getCpf()) != null)
				throw new RESTRuntimeException("user.type.pfis.cpf.exists");
		} else if (dto instanceof CPJurDTO) {
			CPJurDTO pjur = (CPJurDTO) dto;
			// Verify if CPF exists in database
			if (udao.findOneByCnpj(pjur.getCnpj()) != null)
				throw new RESTRuntimeException("user.type.pjur.cnpj.exists");
		} else {
			throw new RESTRuntimeException("user.type.data.missing");
		}
		
		// All above conditions are satisfied
		model.getAccount().setPassword(encoder.encode(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()));
		model.getAddress().setCity(city);
		model.getAddress().setUser(model);
		model.getAccount().setUser(model);
		model.getAccount().setProfile(profile);
		if (model.getIndividual() != null)
			model.getIndividual().setUser(model);
		else
			model.getLegal().setUser(model);
		return RUserDTO.toDTO(udao.save(model));
	}

	public RUserDTO update(UUserBaseDTO dto) {
		User 		model 		= dto.getModel();
		User 		entity		= Optional.ofNullable(udao.findOne(model.getUuid())).orElseThrow(() -> new RESTRuntimeException("user.notfound"));
		Profile		profile		= Optional.ofNullable(pdao.findOne(IDCoder.decode(dto.getProfile()))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		City		city		= Optional.ofNullable(cdao.findCityByID(dto.getAddressDTO().getCityId())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));

		// If email not equal to persited and email exists
		if(!dto.getEmail().equalsIgnoreCase(entity.getEmail()) && udao.findOneByEmail(dto.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");

		// Verify types
		if (dto.getType() == EUserType.PFIS) {
			// Verify instance type
			if (dto instanceof UPFisDTO) {
				// Verify if CPF exists in database
				String ecpf = entity.getIndividual() != null ? ((UPFisDTO) dto).getCpf() : "";
				String ucpf = ((UPFisDTO) dto).getCpf();
				if (!ecpf.equalsIgnoreCase(ucpf) && udao.findOneByCpf(ucpf) != null)
					throw new RESTRuntimeException("user.type.pfis.cpf.exists");
			} else {
				throw new RESTRuntimeException("user.type.mismatch");
			}
		} else if (dto.getType() == EUserType.PJUR) {
			if (dto instanceof UPJurDTO) {
				// Verify if CNPJ exists in database
				String ecnpj = entity.getLegal() != null? ((UPJurDTO) dto).getCnpj() : "";
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

		// All above conditions are satisfied
		entity.setEmail(model.getEmail());
		entity.setName(model.getName());
		entity.getAccount().setProfile(profile);
		entity.getAddress().setCity(city);
		entity.getAccount().setEnabled(dto.isActive());
		entity.getAccount().setPassword(dto.getPassword() != null && !dto.getPassword().isEmpty()? encoder.encode(Hashing.sha256().hashString(dto.getPassword(), StandardCharsets.UTF_8).toString()) : entity.getAccount().getPassword());

		if (dto instanceof UPJurDTO) {
			entity.setType(EUserType.PJUR);
			entity.setLegal(model.getLegal());
			entity.getLegal().setId(entity.getId());
			entity.getLegal().setUser(entity);
			entity.setIndividual(null);
		} else if (dto instanceof UPFisDTO) {
			entity.setType(EUserType.PFIS);
			entity.setIndividual(model.getIndividual());
			entity.getIndividual().setId(entity.getId());
			entity.getIndividual().setUser(entity);
			entity.setLegal(null);
		}
		return RUserDTO.toDTO(udao.save(entity));
	}
}
