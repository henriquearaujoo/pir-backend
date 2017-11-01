package com.samsung.fas.pir.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.RuleDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.models.dto.PageDTO;
import com.samsung.fas.pir.models.dto.ProfileDTO;
import com.samsung.fas.pir.models.dto.UserDTO;
import com.samsung.fas.pir.models.entity.Profile;

@Service
public class ProfileService {
	@Autowired
	private 	ProfileDAO 	pdao;
	@Autowired
	private		UsersDAO	udao;
	@Autowired
	private		RuleDAO		rdao;

	public List<ProfileDTO> findAll() {
		return pdao.findAll().stream().map(m -> ProfileDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public List<ProfileDTO> findAllActive() {
		return pdao.findAllActive().stream().map(m -> ProfileDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public List<PageDTO> findPagesByProfileID(UUID id) {
		return rdao.findByProfileID(id).stream().map(m -> PageDTO.toDTO(m.getPage(), true)).collect(Collectors.toList());
	}
	
	public List<UserDTO> findUsersByProfileID(UUID id) {
		return udao.findByProfileID(id).stream().map(m -> UserDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public ProfileDTO findOne(UUID id) {
		Profile profile = pdao.findOne(id);
		if (profile == null) 
			throw new RuntimeException("profile.notfound");
		return ProfileDTO.toDTO(profile);
	}
	
	public void save(ProfileDTO profile) {		
		// Verify if title exists, if exists, may the user want to update the profile
		if (pdao.findOneByTitle(profile.getTitle()) != null) 
			throw new RuntimeException("profile.title." + profile.getTitle() + ".exists");
		
		// TODO: Get from active session
		Profile model = profile.getModel();
		pdao.save(model);
	}
	
	public void update(ProfileDTO profile) {
		if (pdao.findOne(profile.getId()) == null)
			throw new RuntimeException("profile.id.notfound");
		
		// Verify if title exists in another profile
		Profile model = pdao.findOneByTitle(profile.getTitle());
		if (model != null && model.getId().compareTo(profile.getId()) != 0) 
			throw new RuntimeException("profile.title.exists");
		
		// If all OK
		pdao.update(profile.getModel(), profile.getId());
	}
}