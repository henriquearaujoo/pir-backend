package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.PageDAO;
import com.samsung.fas.pir.persistence.dao.ProfileDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.rest.dto.ProfileDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class ProfileBO extends BaseBO<Profile, ProfileDAO, ProfileDTO, Long> {
	private	final PageDAO pdao;

	@Autowired
	public ProfileBO(ProfileDAO dao, PageDAO pdao) {
		super(dao);
		this.pdao 	= pdao;
	}

	@Override
	public ProfileDTO save(ProfileDTO create, Device device, UserDetails account) {
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
			if (page.getRules() == null)
				page.setRules(new ArrayList<>());
			page.getRules().clear();
			page.getRules().addAll(rules);
		});

		model.setWhoCreated(((Account) account).getUser());
		model.setWhoUpdated(((Account) account).getUser());
		model.setRules(rules);

		return new ProfileDTO(getDao().save(model), true);
	}

	@Override
	public ProfileDTO update(ProfileDTO update, Device device, UserDetails account) {
		Profile				model		= update.getModel();
		Profile				profile		= getDao().findOne(model.getUuid());

		profile.setActive(model.isActive());
		profile.setDescription(model.getDescription());
		profile.setTitle(model.getTitle());
		profile.setType(model.getType());
		profile.setWhoUpdated(((Account) account).getUser());

		return new ProfileDTO(getDao().save(profile), true);
	}

	@Override
	public Collection<ProfileDTO> save(Collection<ProfileDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ProfileDTO> update(Collection<ProfileDTO> update, Device device, UserDetails details) {
		return null;
	}
}