package com.samsung.fas.pir.models.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.PageDAO;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.RuleDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.rule.CRuleDTO;
import com.samsung.fas.pir.models.dto.rule.RRuleDTO;
import com.samsung.fas.pir.models.dto.rule.URuleDTO;
import com.samsung.fas.pir.models.entity.Page;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.models.entity.Rule;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService {
	private		RuleDAO		rdao;
	private		ProfileDAO	prodao;
	private		PageDAO		pgdao;

	@Autowired
	public RuleService(RuleDAO rdao, ProfileDAO prodao, PageDAO pgdao) {
		this.pgdao		= pgdao;
		this.prodao		= prodao;
		this.rdao		= rdao;
	}
	
	public List<RRuleDTO> findAll() {
		return rdao.findAll().stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}

	public List<RRuleDTO> findAll(Predicate predicate) {
		return rdao.findAll(predicate).stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}

	public org.springframework.data.domain.Page<RRuleDTO> findAll(Pageable pageable) {
		return rdao.findAll(pageable).map(RRuleDTO::toDTO);
	}

	public org.springframework.data.domain.Page<RRuleDTO> findAll(Predicate predicate, Pageable pageable) {
		return rdao.findAll(predicate, pageable).map(RRuleDTO::toDTO);
	}
	
	public List<RRuleDTO> findByProfileID(String id) {
		return rdao.findByProfileID(IDCoder.decodeUUID(id)).stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}
	
	public RRuleDTO findOne(String id) {
		Rule rule = rdao.findOne(IDCoder.decodeUUID(id));
		if (rule == null)
			throw new RESTRuntimeException("rule.notfound");
		return RRuleDTO.toDTO(rule);
	}

	public void delete(String id) {
		if (rdao.findOne(IDCoder.decodeUUID(id)) == null)
			throw new RESTRuntimeException("rule.notfound");
		rdao.delete(IDCoder.decodeUUID(id));
	}
	
	public void save(CRuleDTO rule) {
		Profile		profile		= prodao.findOne(IDCoder.decodeUUID(rule.getProfile()));
		Page		page		= pgdao.findOne(IDCoder.decodeUUID(rule.getPage()));
		Rule		data		= rule.getModel();
		
		if (profile == null) 
			throw new RESTRuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RESTRuntimeException("rule.page.notfound");

		if (rdao.findByProfileAndPageIDs(profile.getId(), page.getId()) != null)
			throw new RESTRuntimeException("rule.exists");

		data.setPage(page);
		data.setProfile(profile);
		rdao.save(data);
	}
	
	public void update(URuleDTO rule) {
		Profile		profile		= prodao.findOne(IDCoder.decodeUUID(rule.getProfile()));
		Page		page		= pgdao.findOne(IDCoder.decodeUUID(rule.getPage()));
		Rule		model		= rdao.findOne(IDCoder.decodeUUID(rule.getId()));
		Rule		data		= rule.getModel();
		
		if (model == null)
			throw new RESTRuntimeException("rule.id.notfound");
		
		if (profile == null) 
			throw new RESTRuntimeException("rule.profile.notfound");
		
		if (page == null)
			throw new RESTRuntimeException("rule.page.notfound");
		
		data.setPage(page);
		data.setProfile(profile);
		rdao.update(data, model.getId());
	}
}
