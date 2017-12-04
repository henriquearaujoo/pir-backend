package com.samsung.fas.pir.models.bo;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.CityDAO;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.user.CUserDTO;
import com.samsung.fas.pir.models.dto.user.RUserDTO;
import com.samsung.fas.pir.models.dto.user.UUserDTO;
import com.samsung.fas.pir.models.entity.City;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.models.enums.EUserType;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {
	private 	UsersDAO 	udao;
	private		CityDAO		cdao;
	private		ProfileDAO	pdao;

	@Autowired
	public UsersService(UsersDAO udao, CityDAO cdao, ProfileDAO pdao) {
		this.udao		= udao;
		this.pdao		= pdao;
		this.cdao		= cdao;
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

	public RUserDTO findByID(String id) {
		User user = udao.findOne(IDCoder.decode(id));
		if (user == null)
			throw new RESTRuntimeException("profile.notfound");
		return RUserDTO.toDTO(user);
	}

	// region create and update model
	public void save(CUserDTO user) {
		// Login already exists
		if(udao.findOneByLogin(user.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");

		if(udao.findOneByEmail(user.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");
		
		if (user.getType() == EUserType.PFIS) {
			if (user.getPjur() != null)
				throw new RESTRuntimeException("user.type.pfis.data.mismatch");
			
			if (user.getPfis() == null)
				throw new RESTRuntimeException("user.type.pfis.data.missing");

			// Verify if CPF exists in database
			if (udao.findOneByCpf(user.getPfis().getCpf()) != null)
				throw new RESTRuntimeException("user.type.pfis.cpf.exists");
		}
		
		if (user.getType() == EUserType.PJUR) {
			if (user.getPfis() != null)
				throw new RESTRuntimeException("user.type.pjur.data.mismatch");
			
			if (user.getPjur() == null)
				throw new RESTRuntimeException("user.type.pjur.data.missing");

			// Verify if CPF exists in database
			if (udao.findOneByCnpj(user.getPjur().getCnpj()) != null)
				throw new RESTRuntimeException("user.type.pjur.cnpj.exists");
		}
		
		// Password validation
		if (user.getPassword() == null) {
			throw new RESTRuntimeException("user.login.password.empty");
		}
		
		if (user.getPassword().replaceAll("\\s+","").isEmpty()) {
			throw new RESTRuntimeException("user.login.password.empty");
		}
		
		// City Validation
		City city = cdao.findCityByID(user.getAddressDTO().getCityId());
		if (city == null) 
			throw new RESTRuntimeException("user.address.city.invalid");
		
		// Profile Validation
		Profile profile = pdao.findOne(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(user.getProfile()), StandardCharsets.UTF_8)));
		if (profile == null)
			throw new RESTRuntimeException("user.profile.notfound");
		
		// All above conditions are satisfied
		User model = user.getModel();
		model.getAddress().setCity(city);
		model.setProfile(profile);
		udao.save(model);
	}
	
	public void update(UUserDTO user) {
		User 	model = udao.findOne(user.getModel().getGuid());
		
		// Verify if user exists
		if (model == null) 
			throw new RESTRuntimeException("user.id.notfound");

		if(!user.getEmail().equalsIgnoreCase(model.getEmail()) && udao.findOneByEmail(user.getEmail()) != null)
			throw new RESTRuntimeException("user.email.exists");
		
		// If PFIS
		if (user.getType() == EUserType.PFIS) {
			
			// Verify type mismatch
			if (user.getPjur() != null) {
				throw new RESTRuntimeException("user.type.pfis.data.mismatch");
			}
			
			// Verify if PFIS data is missing
			if(user.getPfis() == null)
				throw new RESTRuntimeException("user.type.pfis.data.missing");

			// Verify if CPF exists in database
			String 	mcpf 	= model.getPerson() == null? "" : model.getPerson().getCpf();
			String 	ucpf 	= user.getPfis().getCpf();
			if (!mcpf.equalsIgnoreCase(ucpf) && udao.findOneByCpf(ucpf) != null)
				throw new RESTRuntimeException("user.type.pfis.cpf.exists");
		}
		
		// If PJUR
		if (user.getType() == EUserType.PJUR) {
			
			// Verify type mismatch
			if (user.getPfis() != null) {
				throw new RESTRuntimeException("user.type.pjur.data.mismatch");
			}
			
			// Verify PJUR data is missing
			if(user.getPjur() == null)
				throw new RESTRuntimeException("user.type.pjur.data.missing");

			// Verify if CNPJ exists in database
			String 	mcnpj 	= model.getOrganization() == null? "" : model.getOrganization().getCnpj();
			String 	ucnpj 	= user.getPjur().getCnpj();
			if (!mcnpj.equalsIgnoreCase(ucnpj) && udao.findOneByCnpj(ucnpj) != null)
				throw new RESTRuntimeException("user.type.organization.cnpj.exists");
			
		}
		
		// Verify if login already exists
		if(!model.getLogin().equalsIgnoreCase(user.getLogin()) && udao.findOneByLogin(user.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");
		
		// Password validation
		if (user.getPassword() != null && user.getPassword().replaceAll("\\s+","").isEmpty())
			throw new RESTRuntimeException("user.login.password.empty");
		
		// If password
		if (user.getPassword() == null || user.getPassword().isEmpty())
			user.setPassword(model.getPassword());
		
		// City Validation
		City city = cdao.findCityByID(user.getAddressDTO().getCityId());
		if (city == null) 
			throw new RESTRuntimeException("user.address.city.invalid");
		
		// Profile Validation
		Profile profile = pdao.findOne(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(user.getProfile()), StandardCharsets.UTF_8)));
		if (profile == null)
			throw new RESTRuntimeException("user.profile.notfound");

		// All above conditions are satisfied
		User toUpdate = user.getModel();
		toUpdate.getAddress().setCity(city);
		toUpdate.setProfile(profile);
		udao.update(toUpdate, model.getId());
	}
	// endregion
}
