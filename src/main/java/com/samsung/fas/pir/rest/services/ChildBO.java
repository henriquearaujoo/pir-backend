package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Autowired
	public ChildBO(ChildDAO dao) {
		super(dao);
	}

	@Override
	public ChildDTO save(ChildDTO create, UserDetails account) {
		return null;
	}

	@Override
	public ChildDTO update(ChildDTO update, UserDetails account) {
		return null;
	}

	@Override
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, UserDetails account) {
		return null;
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, UserDetails details) {
		return null;
	}

	Child setupChild(Child model, Responsible responsible, User agent) {
		model.getResponsible().add(responsible);
		model.setAgent(agent);
		return model;
	}

	Child setupChild(Child child, Child model, Responsible responsible) {
		setupChild(child, model);
		if (child.getResponsible().stream().filter(item -> item.getUuid().compareTo(responsible.getUuid()) == 0).findAny().orElse(null) == null) {
			child.getResponsible().add(responsible);
		}
		return child;
	}

	Child setupChild(Child model, Mother mother, User agent) {
		model.setMother(mother);
		model.setAgent(agent);
//		mother.getChildren().add(model);
		return model;
	}

	Child setupChild(Child child, Child model, Mother mother) {
		setupChild(child, model);
		child.setMother(mother);
//		if (mother.getChildren().stream().filter(item -> item.getUuid().compareTo(child.getUuid()) == 0).findAny().orElse(null) == null) {
//			mother.getChildren().add(child);
//		}
		return child;
	}

	private void setupChild(Child child, Child model) {
		child.setMobileId(model.getMobileId());
		child.setName(model.getName());
		child.setFatherName(model.getFatherName());
		child.setGender(model.getGender());
		child.setHasCivilRegistration(model.isHasCivilRegistration());
		child.setCivilRegistrationJustification(model.getCivilRegistrationJustification());
		child.setHasEducationDifficulty(model.isHasEducationDifficulty());
		child.setEducationDifficultySpecification(model.getEducationDifficultySpecification());
		child.setPrematureBorn(model.isPrematureBorn());
		child.setBornWeek(model.getBornWeek());
		child.setWhoTakeCare(model.getWhoTakeCare());
		child.setPlaysWithWho(model.getPlaysWithWho());
		child.setMonthlyWeighted(model.isMonthlyWeighted());
		child.setSocialEducationalPrograms(model.isSocialEducationalPrograms());
		child.setVaccinationUpToDate(model.isVaccinationUpToDate());
		child.setRelationDifficulties(model.isHasEducationDifficulty());
	}
}