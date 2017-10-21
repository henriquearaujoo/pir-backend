package com.samsung.fas.pir.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.dto.UserDTO;
import com.samsung.fas.pir.enums.UserType;
import com.samsung.fas.pir.models.User;

@Service
public class UsersService {
	@Autowired
	private UsersDAO udao;
	
	public UserDTO save(UserDTO user) {
		// Type mismatch
		if(user.getPersonDTO() != null && user.getType() == UserType.PJUR)
			throw new RuntimeException("user.type.organization.type.mismatch");
		
		if (user.getOrgDTO() != null && user.getType() == UserType.PFIS)
			throw new RuntimeException("user.type.person.type.mismatch");
		
		// Details missing
		if(user.getPersonDTO() == null && user.getType() == UserType.PFIS)
			throw new RuntimeException("user.type.person.details.missing");
		
		if(user.getOrgDTO() == null && user.getType() == UserType.PJUR)
			throw new RuntimeException("user.type.organization.details.missing");
		
		// Valid CPF & CNPJ
		if(user.getPersonDTO() != null && !CNP.isValidCPF(user.getPersonDTO().getCpf())) {
			throw new RuntimeException("user.type.person.cpf.invalid");
		}
		
		if(user.getOrgDTO() != null && !CNP.isValidCNPJ(user.getOrgDTO().getCnpj())) {
			throw new RuntimeException("user.type.organization.cnpj.invalid");
		}
		
		// Primary keys already exists
		if(udao.findByLogin(user.getLogin()) != null)
			throw new RuntimeException("user.login.exists");
		
		if ((user.getType() == UserType.PFIS) && (udao.findByCpf(user.getPersonDTO().getCpf()) != null)) {
			throw new RuntimeException("user.type.person.cpf.exists");
		} else if (user.getType() == UserType.PJUR && udao.findByCnpj(user.getOrgDTO().getCnpj()) != null) {
			throw new RuntimeException("user.type.organization.cnpj.exists");
		}
		
		// Password validation
		if (user.getPassword() == null && user.getPassword().replaceAll("\\s+","").isEmpty()) {
			throw new RuntimeException("user.login.password.empty");
		}
		
		// All above conditions are satisfied
		return UserDTO.toDTO(udao.save(user.getModel()));
	}
	
	public UserDTO updateUser(UserDTO user) {
		User model = udao.findByID(user.getId());
		
		// Verify if user exists
		if (model == null) 
			throw new RuntimeException("user.id.notfound");
		
		// Verify if is PJUR and PJUR data is missing
		if(user.getPersonDTO() != null && user.getType() == UserType.PJUR)
			throw new RuntimeException("user.type.organization.type.mismatch");
		
		// Verify if is PFIS and PFIS data is missing
		if (user.getOrgDTO() != null && user.getType() == UserType.PFIS)
			throw new RuntimeException("user.type.person.type.mismatch");
		
		// If PFIS
		if (user.getType() == UserType.PFIS) {
			
			// Verify if PFIS data is missing
			if(user.getPersonDTO() == null)
				throw new RuntimeException("user.type.person.data.missing");
			
			String 	mcpf 	= model.getPerson() == null? "" : model.getPerson().getCpf();
			String 	ucpf 	= user.getPersonDTO().getCpf();
			
			// Validate CPF
			if(!CNP.isValidCPF(ucpf)) 
				throw new RuntimeException("user.type.person.cpf.invalid");
			
			// Verify if CPF exists in database
			if (!mcpf.equalsIgnoreCase(ucpf) && udao.findByCpf(ucpf) != null)
				throw new RuntimeException("user.type.person.cpf.exists");
		}
		
		// If PJUR
		if (user.getType() == UserType.PJUR) {
			
			// Verify PJUR data is missing
			if(user.getOrgDTO() == null)
				throw new RuntimeException("user.type.organization.data.missing");
			
			String 	mcnpj 	= model.getOrganization() == null? "" : model.getOrganization().getCnpj();
			String 	ucnpj 	= user.getOrgDTO().getCnpj();
			
			// Validate CNPJ
			if(!CNP.isValidCNPJ(ucnpj))
				throw new RuntimeException("user.type.organization.cnpj.invalid");
			
			// Verify if CNPJ exists in database
			if (!mcnpj.equalsIgnoreCase(ucnpj) && udao.findByCnpj(ucnpj) != null)
				throw new RuntimeException("user.type.organization.cnpj.exists");
			
		}
		
		// Verify if login already exists
		if(!model.getLogin().equalsIgnoreCase(user.getLogin()) && udao.findByLogin(user.getLogin()) != null)
			throw new RuntimeException("user.login.exists");
		
		// Password validation
		if (user.getPassword() != null && user.getPassword().replaceAll("\\s+","").isEmpty())
			throw new RuntimeException("user.login.password.empty");
		
		// If password
		if (user.getPassword().isEmpty()) {
			user.setPassword(model.getPassword());
		}

		return UserDTO.toDTO(udao.updateUser(user.getModel(), user.getId()));
	}
	
	public List<UserDTO> findAll() {
		return udao.findAll().stream().map(m -> UserDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public UserDTO findByID(UUID id) {
		return UserDTO.toDTO(udao.findByID(id));
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
