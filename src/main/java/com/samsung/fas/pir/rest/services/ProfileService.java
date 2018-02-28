package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.profile.CRUProfileDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService extends BService<Profile, CRUProfileDTO, ProfileDAO, Long> {
	private 	PageDAO 	pdao;

	@Autowired
	public ProfileService(ProfileDAO dao, PageDAO pdao) {
		super(dao, Profile.class, CRUProfileDTO.class);
		this.pdao 	= pdao;
	}

	@Override
	public CRUProfileDTO save(CRUProfileDTO create, Account account) {
		Profile				model		= create.getModel();
		Collection<Page>	pages		= pdao.findAll();
		Collection<Rule>	rules		= new HashSet<>();

		pages.forEach(page -> {
			Rule rule = new Rule();
			rule.setPage(page);
			rule.setProfile(model);
			rule.canCreate(false);
			rule.canRead(false);
			rule.canUpdate(false);
			rule.canDelete(false);
			rules.add(rule);
			page.setRules((Set<Rule>) rules);
		});

		model.setWhoCreated(account.getUser());
		model.setWhoUpdated(account.getUser());
		model.setRules(rules);

		return new CRUProfileDTO(dao.save(model));
	}

	@Override
	public CRUProfileDTO update(CRUProfileDTO update, Account account) {
		Profile				model		= update.getModel();
		Profile				profile		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));

		profile.setActive(model.isActive());
		profile.setDescription(model.getDescription());
		profile.setTitle(model.getTitle());
		profile.setWhoUpdated(account.getUser());

		return new CRUProfileDTO(dao.save(profile));
	}
}