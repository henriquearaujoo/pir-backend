package com.samsung.fas.pir.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.CityDAO;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.models.dto.UserDTO;
import com.samsung.fas.pir.models.entity.City;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.models.entity.User;
import com.samsung.fas.pir.models.enums.UserType;

@Service
public class UsersService {
	@Autowired
	private 	UsersDAO 	udao;
	@Autowired
	private		CityDAO		cdao;
	@Autowired
	private		ProfileDAO	pdao;
	
	public void save(UserDTO user) {
		// Login already exists
		if(udao.findOneByLogin(user.getLogin()) != null)
			throw new RuntimeException("user.login.exists");
		
		if (user.getType() == UserType.PFIS) {
			if (user.getOrgDTO() != null)
				throw new RuntimeException("user.type.pfis.data.mismatch");
			
			if (user.getPersonDTO() == null)
				throw new RuntimeException("user.type.pfis.data.missing");
			
			// Validate CPF
			String 	ucpf 	= user.getPersonDTO().getCpf();
			if(!CNP.isValidCPF(ucpf)) 
				throw new RuntimeException("user.type.pfis.cpf.invalid");
			
			// Verify if CPF exists in database
			if (udao.findOneByCpf(ucpf) != null)
				throw new RuntimeException("user.type.pfis.cpf.exists");
		}
		
		if (user.getType() == UserType.PJUR) {
			if (user.getPersonDTO() != null)
				throw new RuntimeException("user.type.pjur.data.mismatch");
			
			if (user.getOrgDTO() == null)
				throw new RuntimeException("user.type.pjur.data.missing");
			
			// Validate CNPJ
			String 	ucnpj 	= user.getOrgDTO().getCnpj();
			if(!CNP.isValidCPF(ucnpj)) 
				throw new RuntimeException("user.type.pjur.cnpj.invalid");
			
			// Verify if CPF exists in database
			if (udao.findOneByCnpj(ucnpj) != null)
				throw new RuntimeException("user.type.pjur.cnpj.exists");
		}
		
		// Password validation
		if (user.getPassword() == null) {
			throw new RuntimeException("user.login.password.empty");
		}
		
		if (user.getPassword().replaceAll("\\s+","").isEmpty()) {
			throw new RuntimeException("user.login.password.empty");
		}
		
		// City Validation
		City city = cdao.findCityByID(user.getAddressDTO().getCityId());
		if (city == null) 
			throw new RuntimeException("user.address.city.invalid");
		
		// Profile Validation
		Profile profile = pdao.findOne(user.getProfile());
		if (profile == null)
			throw new RuntimeException("user.profile.notfound");
		
		// All above conditions are satisfied
		User model = user.getModel();
		model.getAddress().setCity(city);
		model.setProfile(profile);
		udao.save(model);
	}
	
	public void update(UserDTO user) {
		User model = udao.findOne(user.getId());
		
		// Verify if user exists
		if (model == null) 
			throw new RuntimeException("user.id.notfound");
		
		// If PFIS
		if (user.getType() == UserType.PFIS) {
			
			// Verify type mismatch
			if (user.getOrgDTO() != null) {
				throw new RuntimeException("user.type.person.data.mismatch");
			}
			
			// Verify if PFIS data is missing
			if(user.getPersonDTO() == null)
				throw new RuntimeException("user.type.person.data.missing");
			
			String 	mcpf 	= model.getPerson() == null? "" : model.getPerson().getCpf();
			String 	ucpf 	= user.getPersonDTO().getCpf();
			
			// Validate CPF
			if(!CNP.isValidCPF(ucpf)) 
				throw new RuntimeException("user.type.person.cpf.invalid");
			
			// Verify if CPF exists in database
			if (!mcpf.equalsIgnoreCase(ucpf) && udao.findOneByCpf(ucpf) != null)
				throw new RuntimeException("user.type.person.cpf.exists");
		}
		
		// If PJUR
		if (user.getType() == UserType.PJUR) {
			
			// Verify type mismatch
			if (user.getPersonDTO() != null) {
				throw new RuntimeException("user.type.organization.data.mismatch");
			}
			
			// Verify PJUR data is missing
			if(user.getOrgDTO() == null)
				throw new RuntimeException("user.type.organization.data.missing");
			
			String 	mcnpj 	= model.getOrganization().getCnpj() == null? "" : model.getOrganization().getCnpj();
			String 	ucnpj 	= user.getOrgDTO().getCnpj();
			
			// Validate CNPJ
			if(!CNP.isValidCNPJ(ucnpj))
				throw new RuntimeException("user.type.organization.cnpj.invalid");
			
			// Verify if CNPJ exists in database
			if (!mcnpj.equalsIgnoreCase(ucnpj) && udao.findOneByCnpj(ucnpj) != null)
				throw new RuntimeException("user.type.organization.cnpj.exists");
			
		}
		
		// Verify if login already exists
		if(!model.getLogin().equalsIgnoreCase(user.getLogin()) && udao.findOneByLogin(user.getLogin()) != null)
			throw new RuntimeException("user.login.exists");
		
		// Password validation
		if (user.getPassword() != null && user.getPassword().replaceAll("\\s+","").isEmpty())
			throw new RuntimeException("user.login.password.empty");
		
		// If password
		if (user.getPassword().isEmpty()) {
			user.setPassword(model.getPassword());
		}
		
		// City Validation
		City city = cdao.findCityByID(user.getAddressDTO().getCityId());
		if (city == null) 
			throw new RuntimeException("user.address.city.invalid");
		
		// Profile Validation
		Profile profile = pdao.findOne(user.getProfile());
		if (profile == null)
			throw new RuntimeException("user.profile.notfound");

		// All above conditions are satisfied
		User toUpdate = user.getModel();
		toUpdate.getAddress().setCity(city);
		toUpdate.setProfile(profile);
		udao.update(toUpdate, user.getId());
	}
	
	public List<UserDTO> findAll() {
		return udao.findAll().stream().map(m -> UserDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public UserDTO findByID(UUID id) {
		return UserDTO.toDTO(udao.findOne(id));
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

	public static boolean isValidCPF(String cpf) {
	  if ((cpf==null) || (cpf.length()!=11)) return false;
	
	  Integer digito1 = calcularDigito(cpf.substring(0,9), pCPF);
	  Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pCPF);
	  return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCNPJ(String cnpj) {
	  if ((cnpj==null)||(cnpj.length()!=14)) return false;
	
	  Integer digito1 = calcularDigito(cnpj.substring(0,12), pCNPJ);
	  Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pCNPJ);
	  return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	}
}
