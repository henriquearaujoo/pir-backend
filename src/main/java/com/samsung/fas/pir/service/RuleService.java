package com.samsung.fas.pir.service;

import com.samsung.fas.pir.dao.PageDAO;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.RuleDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.RuleDTO;
import com.samsung.fas.pir.models.entity.Page;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.models.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
	
	public List<RuleDTO> findByProfileID(UUID id) {
		return rdao.findByProfileID(id).stream().map(m -> RuleDTO.toDTO(m)).collect(Collectors.toList());
	}
	
	public RuleDTO findOne(UUID id) {
		Rule rule = rdao.findOne(id);
		if (rule == null)
			throw new RESTRuntimeException("rule.notfound");
		return RuleDTO.toDTO(rule);
	}
	
	public void delete(UUID id) {
		if (rdao.findOne(id) == null)
			throw new RESTRuntimeException("rule.notfound");
		rdao.delete(id);
	}
	
	public void save(RuleDTO rule) {
		Profile		profile		= prodao.findOne(rule.getProfile());
		Page		page		= pgdao.findOne(rule.getPage());
		Rule		model		= rule.getModel();
		
		if (profile == null) 
			throw new RESTRuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RESTRuntimeException("rule.page.notfound");
		
		if (rdao.findByProfileAndPageIDs(rule.getProfile(), rule.getPage()) != null)
			throw new RESTRuntimeException("rule.exists");
		
		model.setPage(page);
		model.setProfile(profile);
		// TODO: get from session login
//		model.setWhoCreated(udao.findOne(rule.getWhoCreated()));
//		model.setWhoUpdated(udao.findOne(rule.getWhoUpdated()));
		rdao.save(model);
	}
	
	public void update(RuleDTO rule) {
		Profile		profile		= prodao.findOne(rule.getProfile());
		Page		page		= pgdao.findOne(rule.getPage());
		Rule		model		= rule.getModel();
		
		if (rdao.findOne(rule.getId()) == null)
			throw new RESTRuntimeException("rule.id.notfound");
		
		if (profile == null) 
			throw new RESTRuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RESTRuntimeException("rule.page.notfound");
		
		model.setPage(page);
		model.setProfile(profile);
		// TODO: get from session login
		model.setWhoCreated(udao.findOne(rule.getWhoCreated()));
		model.setWhoUpdated(udao.findOne(rule.getWhoUpdated()));
		rdao.update(model, rule.getId());
	}
}
