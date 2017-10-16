package com.samsung.fas.pir.dto;

import java.util.ArrayList;
import java.util.List;

import com.samsung.fas.pir.models.User;

public class UserListDTO {
	private		List<UserDTO>	list;
	
	public UserListDTO(List<User> list) {
		this.list = new ArrayList<>();	
		list.forEach((item) -> {
			this.list.add(new UserDTO(item));
		});
	}
	
	public List<UserDTO> getList() {
		return this.list;
	}
	
	private class UserDTO {
		private		String				name;
		private		boolean				isActive;
		
		UserDTO(User user) {
			this.name 		= user.getName();
			this.isActive 	= user.isActive();
		}

		public String getName() {
			return name;
		}

		public boolean isActive() {
			return isActive;
		}
	}
}
