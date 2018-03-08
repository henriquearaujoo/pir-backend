package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.entity.Authority;
import com.samsung.fas.pir.persistence.dao.RuleDAO;
import com.samsung.fas.pir.persistence.models.entity.Page;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.rest.dto.rule.CRURuleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class RuleService extends BService<Rule, CRURuleDTO, RuleDAO, Long> {

	@Autowired
	public RuleService(RuleDAO dao) {
		super(dao, Rule.class, CRURuleDTO.class);
	}

	@Override
	public CRURuleDTO save(CRURuleDTO create, UserDetails account) {
		return null;
	}

	@Override
	public CRURuleDTO update(CRURuleDTO update, UserDetails account) {
		Rule					model			= update.getModel();
		UUID					ruleID			= Optional.ofNullable(update.getId() != null && !update.getId().trim().isEmpty()? IDCoder.decode(update.getId()) : null).orElseThrow(() -> new RESTRuntimeException("id.missing"));
		Rule					rule			= Optional.ofNullable(dao.findOne(ruleID)).orElseThrow(() -> new RESTRuntimeException("rule.notfound"));
		Profile					profile			= Optional.ofNullable(rule.getProfile()).orElseThrow(() -> new RESTRuntimeException("profile.notfound"));
		Page					page			= Optional.ofNullable(rule.getPage()).orElseThrow(() -> new RESTRuntimeException("rule.page.notfound"));
		Collection<Authority>	authorities		= Optional.ofNullable(profile.getAuthorities()).orElse(new ArrayList<>());

		if (model.canCreate()) {
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

		if (model.canRead()) {
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

		if (model.canUpdate()) {
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

		if (model.canDelete()) {
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
		rule.canCreate(model.canCreate());
		rule.canRead(model.canRead());
		rule.canUpdate(model.canUpdate());
		rule.canDelete(model.canDelete());
		rule.getProfile().setWhoUpdated(((Account) account).getUser());

		return new CRURuleDTO(dao.save(rule), true);
	}
}
