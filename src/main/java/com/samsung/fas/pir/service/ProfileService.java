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
		return ProfileDTO.toDTO(pdao.findOne(id));
	}
	
	public void save(ProfileDTO profile) {
//		User whoCreate = udao.findOne(profile.getCreatedBy());
//		
//		if (whoCreate == null) 
//			throw new RuntimeException("profile.user.notfound");
		
		// TODO: Check permissions
		//if (whoUpdate.getProfile().getTitle() != "ADM")
		//	throw new RuntimeException("profile.user.nopermission");
		
		// Verify if title exists, if exists, may the user want to update the profile
		if (pdao.findOneByTitle(profile.getTitle()) != null) 
			throw new RuntimeException("profile.title.exists");
		
		// TODO: Get from active session
		Profile model = profile.getModel();
//		model.setWhoCreated(whoCreate);
//		model.setWhoUpdated(whoCreate);
		pdao.save(model);
	}
	
	public void update(ProfileDTO profile) {
//		User whoUpdate = udao.findOne(profile.getModifiedBy());
//		
//		if (whoUpdate == null) 
//			throw new RuntimeException("profile.user.notfound");
		
		// TODO: Check permissions
		//if (whoUpdate.getProfile().getTitle() != "ADM")
		//	throw new RuntimeException("profile.user.nopermission");
			
		if (pdao.findOne(profile.getId()) == null)
			throw new RuntimeException("profile.id.notfound");
		
		// Verify if title exists in another profile
		Profile p = pdao.findOneByTitle(profile.getTitle());
		if (p != null && p.getId().compareTo(profile.getId()) != 0) 
			throw new RuntimeException("profile.title.exists");
		
		// TODO: Get from active session
		Profile model = profile.getModel();
//		model.setWhoCreated(whoUpdate);
//		model.setWhoUpdated(whoUpdate);
		pdao.update(model, profile.getId());
	}
}