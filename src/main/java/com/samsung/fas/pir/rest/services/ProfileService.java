package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.rest.dto.ProfileDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class ProfileService extends BService<Profile, ProfileDTO, ProfileDAO, Long> {
	private	final PageDAO pdao;

	@Autowired
	public ProfileService(ProfileDAO dao, @Autowired PageDAO pdao) {
		super(dao, Profile.class, ProfileDTO.class);
		this.pdao 	= pdao;
	}

	@Override
	public ProfileDTO save(ProfileDTO create, UserDetails account) {
		Profile				model		= create.getModel();
		Collection<Page> 	pages		= pdao.findAll();
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
			page.setRules(rules);
		});

		model.setWhoCreated(((Account) account).getUser());
		model.setWhoUpdated(((Account) account).getUser());
		model.setRules(rules);

		return new ProfileDTO(dao.save(model), true);
	}

	@Override
	public ProfileDTO update(ProfileDTO update, UserDetails account) {
		Profile				model		= update.getModel();
		Profile				profile		= dao.findOne(model.getUuid());

		profile.setActive(model.isActive());
		profile.setDescription(model.getDescription());
		profile.setTitle(model.getTitle());
		profile.setWhoUpdated(((Account) account).getUser());

		return new ProfileDTO(dao.save(profile), true);
	}
}