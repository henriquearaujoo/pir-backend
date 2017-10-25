package com.samsung.fas.pir.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.dto.ProfileDTO;
import com.samsung.fas.pir.models.Profile;
import com.samsung.fas.pir.models.User;

@Service
public class ProfileService {
	@Autowired
	private 	ProfileDAO 	pdao;
	@Autowired
	private		UsersDAO	udao;

	public List<ProfileDTO> findAll() {
		return pdao.findAll().stream().map(m -> ProfileDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public void save(ProfileDTO profile) {
		User whoCreate = udao.findOne(profile.getCreatedBy());
		
		if (whoCreate == null) 
			throw new RuntimeException("profile.user.notfound");
		
		// TODO: Check permissions
		//if (whoUpdate.getProfile().getTitle() != "ADM")
		//	throw new RuntimeException("profile.user.nopermission");
		
		// Verify if title exists, if exists, may the user want to update the profile
		if (pdao.findOneByTitle(profile.getTitle()) != null) 
			throw new RuntimeException("profile.title.exists");
		
		// TODO: Get from active session
		Profile model = profile.getModel();
		model.setWhoCreated(whoCreate);
		model.setWhoUpdated(whoCreate);
		pdao.save(model);
	}
	
	public void update(ProfileDTO profile) {
		User whoUpdate = udao.findOne(profile.getModifiedBy());
		
		if (whoUpdate == null) 
			throw new RuntimeException("profile.user.notfound");
		
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
		model.setWhoCreated(whoUpdate);
		model.setWhoUpdated(whoUpdate);
		pdao.update(model, profile.getId());
	}
}