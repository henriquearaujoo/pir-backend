package com.samsung.fas.pir.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.PageDAO;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.RuleDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.dto.RuleDTO;
import com.samsung.fas.pir.models.Page;
import com.samsung.fas.pir.models.Profile;
import com.samsung.fas.pir.models.Rule;

@Service
public class RuleService {
	@Autowired
	private		RuleDAO		rdao;
	@Autowired	
	private		ProfileDAO	prodao;
	@Autowired
	private		PageDAO		pgdao;
	@Autowired
	private		UsersDAO	udao;
	
	public List<RuleDTO> findAll() {
		return rdao.findAll().stream().map(m -> RuleDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public RuleDTO findOne(UUID id) {
		return RuleDTO.toDTO(rdao.findOne(id));
	}
	
	public void delete(UUID id) {
		rdao.delete(id);
	}
	
	public void save(RuleDTO rule) {
		Profile		profile		= prodao.findOne(rule.getProfile());
		Page		page		= pgdao.findOne(rule.getPage());
		Rule		model		= rule.getModel();
		
		if (profile == null) 
			throw new RuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RuntimeException("rule.page.notfound");
		
		if (rdao.findByProfileAndPageIDs(rule.getProfile(), rule.getPage()) != null)
			throw new RuntimeException("rule.exists");
		
		model.setPage(page);
		model.setProfile(profile);
		// TODO: get from session login
		model.setWhoCreated(udao.findOne(rule.getWhoCreated()));
		model.setWhoUpdated(udao.findOne(rule.getWhoUpdated()));
		rdao.save(model);
	}
	
	public void update(RuleDTO rule) {
		Profile		profile		= prodao.findOne(rule.getProfile());
		Page		page		= pgdao.findOne(rule.getPage());
		Rule		model		= rule.getModel();
		
		if (rdao.findOne(rule.getId()) == null)
			throw new RuntimeException("rule.id.notfound");
		
		if (profile == null) 
			throw new RuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RuntimeException("rule.page.notfound");
		
		model.setPage(page);
		model.setProfile(profile);
		// TODO: get from session login
		model.setWhoCreated(udao.findOne(rule.getWhoCreated()));
		model.setWhoUpdated(udao.findOne(rule.getWhoUpdated()));
		rdao.update(model, rule.getId());
	}
}
