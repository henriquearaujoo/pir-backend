package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.*;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class ChildBO extends BaseBO<Child, ChildDAO, ChildDTO, Long> {
	@Getter
	@Setter
	private		VisitDAO			visitDAO;

	@Getter
	@Setter
	private 	UserDAO 			userDAO;

	@Getter
	@Setter
	private 	ResponsibleDAO		responsibleDAO;

	@Getter
	@Setter
	private 	SAnswerDAO			sAnswerDAO;

	@Getter
	@Setter
	private 	SAnswerBO			sAnswerBO;

	@Getter
	@Setter
	private		VisitBO				visitBO;
	private List<Responsible> responsible;

	@Autowired
	public ChildBO(ChildDAO dao, ResponsibleDAO responsibleDAO, UserDAO userDAO, VisitDAO visitDAO, SAnswerDAO sAnswerDAO, SAnswerBO sAnswerBO, VisitBO visitBO) {
		super(dao);
		setVisitBO(visitBO);
		setVisitDAO(visitDAO);
		setResponsibleDAO(responsibleDAO);
		setUserDAO(userDAO);
		setSAnswerDAO(sAnswerDAO);
		setSAnswerBO(sAnswerBO);
	}

	@Override
	public ChildDTO save(ChildDTO create, Device device, UserDetails account) {
		Child				model		= create.getModel();
		Child				child		= create.getUuid() != null? getDao().findOne(create.getUuid()) : null;
		List<Responsible>	responsible	= create.getResponsibleDTO() != null? create.getResponsibleDTO().stream().map(item -> getResponsibleDAO().findOne(item.getUuid())).collect(Collectors.toList()) : new ArrayList<>();

		if (child == null) {
			model.setAgent(((Account) account).getUser());
//			model.setResponsible(responsible);
//			responsible.forEach(resp -> resp.getChildren().add(model));
//			model.setVisits(setupVisit(model, model.getVisits(), agent));
			return new ChildDTO(getDao().save(model), device, true);
		} else {
			setupChild(child, model);
//			responsible.forEach(resp -> resp.getChildren().remove(resp.getChildren().stream().filter(item -> item.getUuid().compareTo(child.getUuid()) == 0).findAny().orElse(new Child())));
//			child.setResponsible(responsible);
//			responsible.forEach(resp -> resp.getChildren().add(model));
			return new ChildDTO(getDao().save(child), device, true);
		}
	}

	@Override
	public ChildDTO update(ChildDTO update, Device device, UserDetails account) {
		Child				model		= update.getModel();
		Child				child		= update.getUuid() != null? getDao().findOne(update.getUuid()) : null;
		List<Responsible>	responsible	= update.getResponsibleDTO() != null? update.getResponsibleDTO().stream().map(item -> getResponsibleDAO().findOne(item.getUuid())).collect(Collectors.toList()) : new ArrayList<>();
		User				agent		= update.getAgentUUID() != null? getUserDAO().findOne(update.getAgentUUID()) : null;

		if (child == null) {
//			if (agent == null)
//				throw new RESTException("agent.missing");
//			model.setAgent(agent);
//			model.setResponsible(responsible);
//			responsible.forEach(resp -> resp.getChildren().add(model));
			return new ChildDTO(getDao().save(model), device, true);
		} else {
			setupChild(child, model);
//			responsible.forEach(resp -> resp.getChildren().remove(resp.getChildren().stream().filter(item -> item.getUuid().compareTo(child.getUuid()) == 0).findAny().orElse(new Child())));
//			child.setResponsible(responsible);
//			responsible.forEach(resp -> resp.getChildren().add(model));
			return new ChildDTO(getDao().save(child), device, true);
		}
	}

	@Override
	public Collection<ChildDTO> save(Collection<ChildDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> save(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<ChildDTO> update(Collection<ChildDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> update(item, device, details)).collect(Collectors.toList());
	}

	Child setupChild(Child model, Responsible responsible, User agent) {
		model.getResponsible().add(responsible);
		model.setAgent(agent);
		if (model.getResponsible().stream().filter(item -> item.getMobileId() - responsible.getMobileId() == 0).findAny().orElse(null) == null) {
			model.getResponsible().add(responsible);
		}
		model.setVisits(setupVisit(model, model.getVisits(), agent));
		model.setAnswers(setupAnswer(model, model.getAnswers(), agent));
		return model;
	}

	Child setupChild(Child child, Child model, Responsible responsible, User agent) {
		setupChild(child, model);
		if (child.getResponsible().stream().filter(item -> responsible.getUuid() != null && item.getUuid().compareTo(responsible.getUuid()) == 0).findAny().orElse(null) == null) {
			child.getResponsible().add(responsible);
		}
		child.setVisits(setupVisit(child, model.getVisits(), agent));
		child.setAnswers(setupAnswer(child, model.getAnswers(), agent));
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


	private Collection<SAnswer> setupAnswer(Child child, Collection<SAnswer> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			SAnswer		sAnswer		= uuid != null? getSAnswerDAO().findOne(uuid) : null;
			if (sAnswer != null) {
				return getSAnswerBO().setupAnswer(sAnswer, item, child, agent);
			} else {
				return getSAnswerBO().setupAnswer(item, child, agent);
			}
		}).collect(Collectors.toList());
	}

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