package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Authority;
import com.samsung.fas.pir.login.persistence.repository.IAuthorityRepository;
import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.RuleDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.rule.RRuleDTO;
import com.samsung.fas.pir.rest.dto.rule.URuleDTO;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService {
	private		RuleDAO					rdao;
	private		ProfileDAO				prodao;
	private		PageDAO					pgdao;
	private 	IAuthorityRepository	arepository;

	@Autowired
	public RuleService(RuleDAO rdao, ProfileDAO prodao, PageDAO pgdao, IAuthorityRepository arepository) {
		this.pgdao			= pgdao;
		this.prodao			= prodao;
		this.rdao			= rdao;
		this.arepository	= arepository;
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

		if (data.isCreate()) {
			Authority	authority	= arepository.findByAuthority("CREATE::" + page.getTitle().toUpperCase());
			if (authority != null) {
				authority.addProfile(profile);
				profile.addAuthority(authority);
			} else {
				authority = new Authority();
				authority.setAuthority("CREATE::" + page.getTitle().toUpperCase());
				authority.addProfile(profile);
				profile.addAuthority(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("CREATE::" + page.getTitle().toUpperCase()));
		}

		if (data.isRead()) {
			Authority	authority	= arepository.findByAuthority("READ::" + page.getTitle().toUpperCase());
			if (authority != null) {
				authority.addProfile(profile);
				profile.addAuthority(authority);
			} else {
				authority = new Authority();
				authority.setAuthority("READ::" + page.getTitle().toUpperCase());
				authority.addProfile(profile);
				profile.addAuthority(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("READ::" + page.getTitle().toUpperCase()));
		}

		if (data.isUpdate()) {
			Authority	authority	= arepository.findByAuthority("UPDATE::" + page.getTitle().toUpperCase());
			if (authority != null) {
				authority.addProfile(profile);
				profile.addAuthority(authority);
			} else {
				authority = new Authority();
				authority.setAuthority("UPDATE::" +page.getTitle().toUpperCase());
				authority.addProfile(profile);
				profile.addAuthority(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("UPDATE::" +page.getTitle().toUpperCase()));
		}

		if (data.isDelete()) {
			Authority	authority	= arepository.findByAuthority("DELETE::" + page.getTitle().toUpperCase());
			if (authority != null) {
				authority.addProfile(profile);
				profile.addAuthority(authority);
//				arepository.save(authority);
			} else {
				authority = new Authority();
				authority.setAuthority("DELETE::" + page.getTitle().toUpperCase());
				authority.addProfile(profile);
				profile.addAuthority(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("DELETE::" + page.getTitle().toUpperCase()));
		}
		
		data.setPage(page);
		data.setProfile(profile);
		data.setId(model.getId());
		rdao.save(data);
	}
}
