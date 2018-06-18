package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.VisitDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.ChildDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Getter
	@Setter
	private		VisitDAO	visitDAO;

	@Getter
	@Setter
	private		VisitBO		visitBO;

	@Autowired
	public ChildBO(ChildDAO dao, VisitDAO visitDAO, VisitBO visitBO) {
		super(dao);
		setVisitBO(visitBO);
		setVisitDAO(visitDAO);
	}

	@Override
	public ChildDTO save(ChildDTO create, Device device, UserDetails account) {
		return null;
	}

	@Override
	public ChildDTO update(ChildDTO update, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, Device device, UserDetails account) {
		return null;
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, Device device, UserDetails details) {
		return null;
	}

	// region Responsible
	Child setupChild(Child model, Responsible responsible, User agent) {
		model.getResponsible().add(responsible);
		model.setAgent(agent);
		if (model.getResponsible().stream().filter(item -> item.getMobileId() - responsible.getMobileId() == 0).findAny().orElse(null) == null) {
			model.getResponsible().add(responsible);
		}
		model.setVisits(setupVisit(model, model.getVisits(), agent));
		return model;
	}

	Child setupChild(Child child, Child model, Responsible responsible, User agent) {
		setupChild(child, model);
		if (child.getResponsible().stream().filter(item -> item.getUuid().compareTo(responsible.getUuid()) == 0).findAny().orElse(null) == null) {
			child.getResponsible().add(responsible);
		}
		child.setVisits(setupVisit(child, model.getVisits(), agent));
		return child;
	}
	// endregion

	// region Mother
	Child setupChild(Child model, Mother mother, User agent) {
		model.setMother(mother);
		model.setAgent(agent);
		model.setVisits(setupVisit(model, model.getVisits(), agent));
		return model;
	}

	Child setupChild(Child child, Child model, Mother mother, User agent) {
		setupChild(child, model);
		child.setMother(mother);
		child.setVisits(setupVisit(child, model.getVisits(), agent));
		return child;
	}
	// endregion

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

	@SuppressWarnings("Duplicates")
	private Collection<Visit> setupVisit(Child child, Collection<Visit> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Visit		visit		= uuid != null? getVisitDAO().findOne(uuid) : null;
			if (visit != null) {
				return getVisitBO().setupVisit(visit, item, child, agent);
			} else {
				return getVisitBO().setupVisit(item, child, agent);
			}
		}).collect(Collectors.toList());
	}
}