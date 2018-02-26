package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.dao.RuleDAO;
import com.samsung.fas.pir.persistence.dao.UsersDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.page.RCompletePageDTO;
import com.samsung.fas.pir.rest.dto.profile.CProfileDTO;
import com.samsung.fas.pir.rest.dto.profile.RProfileDTO;
import com.samsung.fas.pir.rest.dto.profile.UProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileService {
	private 	ProfileDAO 	pdao;
	private		RuleDAO		rdao;
	private 	PageDAO		pagedao;

	@Autowired
	public ProfileService(ProfileDAO pdao, UsersDAO udao, RuleDAO rdao, PageDAO pagedao) {
		this.pdao		= pdao;
		this.rdao		= rdao;
		this.pagedao	= pagedao;
	}

	public RProfileDTO findOne(UUID id) {
		Profile profile = pdao.findOne(id);
		if (profile == null)
			throw new RESTRuntimeException("profile.notfound");
		return RProfileDTO.toDTO(profile);
	}

	public List<RProfileDTO> findAll() {
		return pdao.findAll().stream().map(RProfileDTO::toDTO).collect(Collectors.toList());
	}

	public org.springframework.data.domain.Page<RProfileDTO> findAll(Pageable pageable) {
		return pdao.findAll(pageable).map(RProfileDTO::toDTO);
	}

	public List<RProfileDTO> findAll(Predicate predicate) {
		return pdao.findAll(predicate).stream().map(RProfileDTO::toDTO).collect(Collectors.toList());
	}

	public org.springframework.data.domain.Page<RProfileDTO> findAll(Predicate predicate, Pageable pageable) {
		return pdao.findAll(predicate, pageable).map(RProfileDTO::toDTO);
	}

	public List<RCompletePageDTO> findPagesByProfileID(UUID id) {
		return null;
//		return rdao.findByProfileID(id).stream().map(m -> RCompletePageDTO.toDTO(m.getPage())).collect(Collectors.toList());
	}
	
	public RProfileDTO save(CProfileDTO profile, Account account) {
		// Verify if title exists, if exists, may the user want to update the profile
		if (pdao.findOneByTitle(profile.getTitle()) != null) 
			throw new RESTRuntimeException("profile.title.exists");

		// Create automatic rules for all pages
		Profile		model	= profile.getModel();
		List<Page>	pages	= pagedao.findAll();
		Set<Rule> 	rules	= new HashSet<>();

		for (Page page : pages) {
			Rule rule = new Rule();
			rule.setPage(page);
			rule.setProfile(model);
			rule.setCreate(false);
			rule.setDelete(false);
			rule.setRead(false);
			rule.setUpdate(false);
			rules.add(rule);
			page.setRules(rules);
		}

		model.setWhoCreated(account.getUser());
		model.setWhoUpdated(account.getUser());
		model.setRules(rules);
		return RProfileDTO.toDTO(pdao.save(model));
	}
	
	public RProfileDTO update(UProfileDTO dto, Account account) {
		Profile	model	= dto.getModel();
		Profile profile	= pdao.findOne(model.getUuid());

		if (profile == null)
			throw new RESTRuntimeException("profile.id.notfound");
		
		// Verify if title exists in another profile
		Profile	title	= pdao.findOneByTitle(model.getTitle());
		if (title != null && title.getId() != profile.getId())
			throw new RESTRuntimeException("profile.title.exists");

		profile.setActive(model.isActive());
		profile.setDescription(model.getDescription());
		profile.setTitle(model.getTitle());
		profile.setWhoUpdated(account.getUser());
		
		// If all OK
		return RProfileDTO.toDTO(pdao.save(profile));
	}
}