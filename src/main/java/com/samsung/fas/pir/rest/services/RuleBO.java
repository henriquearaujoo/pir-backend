package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.persistence.models.Authority;
import com.samsung.fas.pir.persistence.dao.RuleDAO;
import com.samsung.fas.pir.persistence.models.Page;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.rest.dto.RuleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RuleBO extends BaseBO<Rule, RuleDAO, RuleDTO, Long> {
	@Autowired
	public RuleBO(RuleDAO dao) {
		super(dao);
	}

	@Override
	public RuleDTO save(RuleDTO create, Device device, UserDetails account) {
		return null;
	}

	@Override
	public RuleDTO update(RuleDTO update, Device device, UserDetails account) {
		Rule					model			= update.getModel();
		Rule					rule			= getDao().findOne(update.getUuid());
		Profile 				profile			= rule.getProfile();
		Page 					page			= rule.getPage();
		Collection<Authority> 	authorities		= profile.getAuthorities();

		if (model.isCanCreate()) {
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("CREATE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
			if (authority != null) {
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			} else {
				authority = new Authority();
				authority.setProfiles(new ArrayList<>());
				authority.setAuthority("CREATE::" + page.getTitle().toUpperCase());
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("CREATE::" + page.getTitle().toUpperCase()));
		}

		if (model.isCanRead()) {
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("READ::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
			if (authority != null) {
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			} else {
				authority = new Authority();
				authority.setProfiles(new ArrayList<>());
				authority.setAuthority("READ::" + page.getTitle().toUpperCase());
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("READ::" + page.getTitle().toUpperCase()));
		}

		if (model.isCanUpdate()) {
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("UPDATE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
			if (authority != null) {
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			} else {
				authority = new Authority();
				authority.setProfiles(new ArrayList<>());
				authority.setAuthority("UPDATE::" +page.getTitle().toUpperCase());
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("UPDATE::" + page.getTitle().toUpperCase()));
		}

		if (model.isCanDelete()) {
			Authority	authority	= authorities.stream().filter(item -> item.getAuthority().equalsIgnoreCase("DELETE::" + page.getTitle().toUpperCase())).findFirst().orElse(null);
			if (authority != null) {
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			} else {
				authority = new Authority();
				authority.setProfiles(new ArrayList<>());
				authority.setAuthority("DELETE::" + page.getTitle().toUpperCase());
				authority.getProfiles().add(profile);
				profile.getAuthorities().add(authority);
			}
		} else {
			profile.getAuthorities().removeIf(item -> item.getAuthority().equalsIgnoreCase("DELETE::" + page.getTitle().toUpperCase()));
		}

		rule.setPage(page);
		rule.setProfile(profile);
		rule.setCanCreate(model.isCanCreate());
		rule.setCanRead(model.isCanRead());
		rule.setCanUpdate(model.isCanUpdate());
		rule.setCanDelete(model.isCanDelete());
		rule.getProfile().setWhoUpdated(((Account) account).getUser());

		return new RuleDTO(getDao().save(rule), device, true);
	}

	@Override
	public Collection<RuleDTO> save(Collection<RuleDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<RuleDTO> update(Collection<RuleDTO> update, Device device, UserDetails details) {
		return null;
	}
}
