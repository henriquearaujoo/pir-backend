package com.samsung.fas.pir.dto;

import java.util.UUID;

import com.samsung.fas.pir.models.City;

public class UsersDTO {
	// Must
	private		String			login;
	private		String			password;
	
	// Optional
	private		UUID			id;
	private		String			name;
	private		String			rg;
	private		String			cpf;
	private		String			cnpj;
	private		String			districtAddr;
	private		String			streetNameAddr;
	private		String			complementAddr;
	private		String			numberAddr;
	private		City			city;
	private		boolean			active;
	private		String			type;
	
	private UsersDTO(Builder builder) {
		login				= builder.login;
		password			= builder.password;
		name				= builder.name;
		rg					= builder.rg;
		cpf					= builder.cpf;
		cnpj				= builder.cnpj;
		districtAddr		= builder.districtAddr;
		streetNameAddr		= builder.streetNameAddr;
		complementAddr		= builder.complementAddr;
		numberAddr			= builder.numberAddr;
		city				= builder.city;
		active				= builder.active;
		type				= builder.type;
	}
	
	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRg() {
		return rg;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getDistrictAddr() {
		return districtAddr;
	}

	public String getStreetNameAddr() {
		return streetNameAddr;
	}

	public String getComplementAddr() {
		return complementAddr;
	}

	public String getNumberAddr() {
		return numberAddr;
	}

	public City getCity() {
		return city;
	}

	public boolean isActive() {
		return active;
	}

	public String getType() {
		return type;
	}

	public static class Builder {
		private		String			login;
		private		String			password;
		private		String			name;
		private		String			rg;
		private		String			cpf;
		private		String			cnpj;
		private		String			districtAddr;
		private		String			streetNameAddr;
		private		String			complementAddr;
		private		String			numberAddr;
		private		City			city;
		private		boolean			active;
		private		String			type;
		
		public Builder(String login, String password) {
			this.login		= login;
			this.password	= password;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder rg(String rg) {
			this.rg = rg;
			return this;
		}
		
		public Builder cpf(String cpf) {
			this.cpf = cpf;
			return this;
		}
		
		public Builder cnpj(String cnpj) {
			this.cnpj = cnpj;
			return this;
		}
		
		public Builder districtAddress(String district) {
			this.districtAddr = district;
			return this;
		}
		
		public Builder streetAddress(String streetName) {
			this.streetNameAddr = streetName;
			return this;
		}
		
		public Builder complementAddress(String complement) {
			this.complementAddr = complement;
			return this;
		}
		
		public Builder numberAddress(String number) {
			this.numberAddr = number;
			return this;
		}
		
		public Builder city(City city) {
			this.city = city;
			return this;
		}
		
		public Builder isActive(boolean active) {
			this.active = active;
			return this;
		}
		
		public Builder type(String type) {
			this.type = type;
			return this;
		}
		
		public UsersDTO build() {
			return new UsersDTO(this);
		}
	}
	
	
	
}
