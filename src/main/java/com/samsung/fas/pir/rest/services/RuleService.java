package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.entity.Authority;
import com.samsung.fas.pir.persistence.dao.RuleDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.rule.RRuleDTO;
import com.samsung.fas.pir.rest.dto.rule.URuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuleService {
	private		RuleDAO					rdao;

	@Autowired
	public RuleService(RuleDAO rdao) {
		this.rdao			= rdao;
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
	
	public RRuleDTO findOne(UUID id) {
		Rule rule = rdao.findOne(id);
		if (rule == null)
			throw new RESTRuntimeException("rule.notfound");
		return RRuleDTO.toDTO(rule);
	}

	public void delete(UUID id) {
		if (rdao.findOne(id) == null)
			throw new RESTRuntimeException("rule.notfound");
		rdao.delete(id);
	}
	
	public RRuleDTO update(URuleDTO dto, Account account) {
		final	Rule					model		= dto.getModel();
		final	Rule					rule		= Optional.ofNullable(rdao.findOne(model.getUuid())).orElseThrow(() -> new RESTRuntimeException("rule.id.notfound"));
		final	Profile					profile		= Optional.ofNullable(rule.getProfile()).orElseThrow(() -> new RESTRuntimeException("rule.profile.notfound"));
		final	Page					page		= Optional.ofNullable(rule.getPage()).orElseThrow(() -> new RESTRuntimeException("rule.page.notfound"));
		final	Collection<Authority>	authorities	= Optional.ofNullable(profile.getAuthorities()).orElse(new ArrayList<>());

		if (model.isCreate()) {
//			Authority	authority	= arepository.findByAuthority("CREATE::" + page.getTitle().toUpperCase());
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("CREATE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
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

		if (model.isRead()) {
//			Authority	authority	= arepository.findByAuthority("READ::" + page.getTitle().toUpperCase());
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("READ::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
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

		if (model.isUpdate()) {
//			Authority	authority	= arepository.findByAuthority("UPDATE::" + page.getTitle().toUpperCase());
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("UPDATE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
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
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("UPDATE::" + page.getTitle().toUpperCase()));
		}

		if (model.isDelete()) {
//			Authority	authority	= arepository.findByAuthority("DELETE::" + page.getTitle().toUpperCase());
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("DELETE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
			if (authority != null) {
				authority.addProfile(profile);
				profile.addAuthority(authority);
			} else {
				authority = new Authority();
				authority.setAuthority("DELETE::" + page.getTitle().toUpperCase());
				authority.addProfile(profile);
				profile.addAuthority(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("DELETE::" + page.getTitle().toUpperCase()));
		}

		rule.setPage(page);
		rule.setProfile(profile);
		rule.setCreate(model.isCreate());
		rule.setDelete(model.isDelete());
		rule.setRead(model.isRead());
		rule.setUpdate(model.isUpdate());
		rule.getProfile().setWhoUpdated(account.getUser());
		return RRuleDTO.toDTO(rdao.save(rule));
	}
}
