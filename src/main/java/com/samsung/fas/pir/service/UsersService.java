package com.samsung.fas.pir.service;

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
import com.samsung.fas.pir.models.enums.UserType;
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
	
	public void save(CUserDTO user) {
		// Login already exists
		if(udao.findOneByLogin(user.getLogin()) != null)
			throw new RESTRuntimeException("user.login.exists");
		
		if (user.getType() == UserType.PFIS) {
			if (user.getPjur() != null)
				throw new RESTRuntimeException("user.type.pfis.data.mismatch");
			
			if (user.getPfis() == null)
				throw new RESTRuntimeException("user.type.pfis.data.missing");
			
			// Validate CPF
			String 	ucpf 	= user.getPfis().getCpf();
			if(!CNP.isValidCPF(ucpf)) 
				throw new RESTRuntimeException("user.type.pfis.cpf.invalid");
			
			// Verify if CPF exists in database
			if (udao.findOneByCpf(ucpf) != null)
				throw new RESTRuntimeException("user.type.pfis.cpf.exists");
		}
		
		if (user.getType() == UserType.PJUR) {
			if (user.getPfis() != null)
				throw new RESTRuntimeException("user.type.pjur.data.mismatch");
			
			if (user.getPjur() == null)
				throw new RESTRuntimeException("user.type.pjur.data.missing");
			
			// Validate CNPJ
			String 	ucnpj 	= user.getPjur().getCnpj();
//			if(!CNP.isValidCPF(ucnpj))
//				throw new RESTRuntimeException("user.type.pjur.cnpj.invalid");
			
			// Verify if CPF exists in database
			if (udao.findOneByCnpj(ucnpj) != null)
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
		Profile profile = pdao.findOne(UUID.fromString(user.getProfile()));
		if (profile == null)
			throw new RESTRuntimeException("user.profile.notfound");
		
		// All above conditions are satisfied
		User model = user.getModel();
		model.getAddress().setCity(city);
		model.setProfile(profile);
		udao.save(model);
	}
	
	public void update(UUserDTO user) {
		User model = udao.findOne(user.getModel().getGuid());
		
		// Verify if user exists
		if (model == null) 
			throw new RESTRuntimeException("user.id.notfound");
		
		// If PFIS
		if (user.getType() == UserType.PFIS) {
			
			// Verify type mismatch
			if (user.getPjur() != null) {
				throw new RESTRuntimeException("user.type.person.data.mismatch");
			}
			
			// Verify if PFIS data is missing
			if(user.getPfis() == null)
				throw new RESTRuntimeException("user.type.person.data.missing");
			
			String 	mcpf 	= model.getPerson() == null? "" : model.getPerson().getCpf();
			String 	ucpf 	= user.getPfis().getCpf();
			
			// Validate CPF
			if(!CNP.isValidCPF(ucpf)) 
				throw new RESTRuntimeException("user.type.person.cpf.invalid");
			
			// Verify if CPF exists in database
			if (!mcpf.equalsIgnoreCase(ucpf) && udao.findOneByCpf(ucpf) != null)
				throw new RESTRuntimeException("user.type.person.cpf.exists");
		}
		
		// If PJUR
		if (user.getType() == UserType.PJUR) {
			
			// Verify type mismatch
			if (user.getPfis() != null) {
				throw new RESTRuntimeException("user.type.organization.data.mismatch");
			}
			
			// Verify PJUR data is missing
			if(user.getPjur() == null)
				throw new RESTRuntimeException("user.type.organization.data.missing");
			
			String 	mcnpj 	= model.getOrganization().getCnpj() == null? "" : model.getOrganization().getCnpj();
			String 	ucnpj 	= user.getPjur().getCnpj();
			
			// Validate CNPJ
			if(!CNP.isValidCNPJ(ucnpj))
				throw new RESTRuntimeException("user.type.organization.cnpj.invalid");
			
			// Verify if CNPJ exists in database
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
	
	public List<RUserDTO> findAll() {
		return udao.findAll().stream().map(RUserDTO::toDTO).collect(Collectors.toList());
	}
	
	public List<RUserDTO> findByProfileID(UUID id) {
		return udao.findAll().stream().map(RUserDTO::toDTO).collect(Collectors.toList());
	}
	
	public Page<RUserDTO> findAll(Pageable pageable) {
		return udao.findAllByPage(pageable).map(RUserDTO::toDTO);
	}
	
	public RUserDTO findByID(String id) {
		User user = udao.findOne(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(id), StandardCharsets.UTF_8)));
		if (user == null)
			throw new RESTRuntimeException("profile.notfound");
		return RUserDTO.toDTO(user);
	}
}


// TODO: Rewrite this
class CNP {
	private static final int[] pCPF 	= {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private static final int[] pCNPJ 	= {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	private static int calcularDigito(String str, int[] peso) {
	  int soma = 0;
	  for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	     digito = Integer.parseInt(str.substring(indice,indice+1));
	     soma += digito*peso[peso.length-str.length()+indice];
	  }
	  soma = 11 - soma % 11;
	  return soma > 9 ? 0 : soma;
	}

	static boolean isValidCPF(String cpf) {
	  if ((cpf==null) || (cpf.length()!=11)) return false;
	
	  Integer digito1 = calcularDigito(cpf.substring(0,9), pCPF);
	  Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pCPF);
	  return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	static boolean isValidCNPJ(String cnpj) {
	  if ((cnpj==null)||(cnpj.length()!=14)) return false;
	
	  Integer digito1 = calcularDigito(cnpj.substring(0,12), pCNPJ);
	  Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pCNPJ);
	  return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	}
}
