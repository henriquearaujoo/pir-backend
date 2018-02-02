package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
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
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

	public RProfileDTO findOne(String id) {
		Profile profile = pdao.findOne(IDCoder.decodeUUID(id));
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

	public List<RCompletePageDTO> findPagesByProfileID(String id) {
		return rdao.findByProfileID(IDCoder.decodeUUID(id)).stream().map(m -> RCompletePageDTO.toDTO(m.getPage())).collect(Collectors.toList());
	}
	
	public void save(CProfileDTO profile) {
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
		model.setRules(rules);
		pdao.save(model);
		rdao.save(rules);
	}
	
	public void update(UProfileDTO profile) {
		Profile model = pdao.findOne(profile.getModel().getGuid());

		if (model == null)
			throw new RESTRuntimeException("profile.id.notfound");
		
		// Verify if title exists in another profile
		Profile ptitle = pdao.findOneByTitle(profile.getTitle());
		if (ptitle != null && ptitle.getId() == model.getId())
			throw new RESTRuntimeException("profile.title.exists");
		
		// If all OK
		pdao.update(profile.getModel(), model.getId());
	}
}