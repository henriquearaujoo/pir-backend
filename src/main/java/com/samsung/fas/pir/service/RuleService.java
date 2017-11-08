package com.samsung.fas.pir.service;

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
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
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
	
	public List<RRuleDTO> findByProfileID(String id) {
		return rdao.findByProfileID(IDCoder.decode(id)).stream().map(RRuleDTO::toDTO).collect(Collectors.toList());
	}
	
	public RRuleDTO findOne(String id) {
		Rule rule = rdao.findOne(IDCoder.decode(id));
		if (rule == null)
			throw new RESTRuntimeException("rule.notfound");
		return RRuleDTO.toDTO(rule);
	}
	
	public void delete(String id) {
		if (rdao.findOne(IDCoder.decode(id)) == null)
			throw new RESTRuntimeException("rule.notfound");
		rdao.delete(UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(id), StandardCharsets.UTF_8)));
	}
	
	public void save(CRuleDTO rule) {
		Profile		profile		= prodao.findOne(IDCoder.decode(rule.getProfile()));
		Page		page		= pgdao.findOne(IDCoder.decode(rule.getPage()));
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
		Profile		profile		= prodao.findOne(IDCoder.decode(rule.getProfile()));
		Page		page		= pgdao.findOne(IDCoder.decode(rule.getPage()));
		Rule		model		= rdao.findOne(IDCoder.decode(rule.getId()));
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
