package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponsibleBO extends BaseBO<Responsible, ResponsibleDAO, ResponsibleDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ChildDAO		childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		CommunityDAO	communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		UserDAO 		userDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		MotherDAO		motherDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PregnancyDAO	pregnancyDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PregnancyBO		pregnancyBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ChildBO			childBO;

	@Autowired
	public ResponsibleBO(ResponsibleDAO dao, ChildDAO childDAO, PregnancyDAO pregnancyDAO, PregnancyBO pregnancyBO, ChildBO childBO) {
		super(dao);
		setChildDAO(childDAO);
		setPregnancyDAO(pregnancyDAO);
		setChildBO(childBO);
		setPregnancyBO(pregnancyBO);
	}

	public Collection<ResponsibleDTO> findAllResponsible() {
		return getDao().findAllResponsible().stream().map(item -> new ResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Collection<ResponsibleDTO> findAllResponsible(Predicate predicate) {
		return getDao().findAllResponsible(predicate).stream().map(item -> new ResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Page<ResponsibleDTO> findAllResponsible(Pageable pageable) {
		return getDao().findAllResponsible(pageable).map(item -> new ResponsibleDTO(item, false));
	}

	public Page<ResponsibleDTO> findAllResponsible(Pageable pageable, Predicate predicate) {
		return getDao().findAllResponsible(predicate, pageable).map(item -> new ResponsibleDTO((Responsible) item, false));
	}

	@Override
	public ResponsibleDTO save(ResponsibleDTO create, UserDetails account) {
		Responsible		model		= create.getModel();
		Responsible		responsible	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Community		community	= getCommunityDAO().findOne(create.getCommunityUUID());
		return responsible != null? new ResponsibleDTO(getDao().save(setupResponsible(responsible, community, ((Account) account).getUser())), true) : new ResponsibleDTO(getDao().save(setupResponsible(model, community, ((Account) account).getUser())), true);
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO update, UserDetails account) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Community		community	= getCommunityDAO().findOne(update.getCommunityUUID());
		return responsible != null? new ResponsibleDTO(getDao().save(setupResponsible(responsible, community, ((Account) account).getUser())), true) : new ResponsibleDTO(getDao().save(setupResponsible(model, community, ((Account) account).getUser())), true);
	}

	@Override
	public Collection<ResponsibleDTO> save(Collection<ResponsibleDTO> collection, UserDetails details) {
		return collection.stream().map(item -> save(item, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<ResponsibleDTO> update(Collection<ResponsibleDTO> collection, UserDetails details) {
		return collection.stream().map(item -> update(item, details)).collect(Collectors.toList());
	}

	Responsible setupResponsible(Responsible model, Community community, User agent) {
		model.setCommunity(community);
		model.setAgent(agent);

		if (model.getChildren() != null) {
			model.setChildren(setupChild(model, model.getChildren(), agent));
		}

		if (model.getMother() != null) {
			model.getMother().setResponsible(model);
			if (model.getChildren() != null && model.getMother().getChildren() != null) {
				model.getMother().setChildren(model.getMother().getChildren().stream().map(motherChild -> model.getChildren().stream().filter(respChild -> respChild.getMobileId() == motherChild.getMobileId()).findAny().orElse(motherChild)).collect(Collectors.toList()));
			}
			if (model.getMother().getPregnancies() != null) {
				model.getMother().setPregnancies(setupPregnancy(model.getMother(), model.getMother().getPregnancies(), agent));
			}
		}

		return model;
	}

	Responsible setupResponsible(Responsible responsible, Responsible model, Community community, User agent) {
		responsible.setFamilyHasChildren(model.isFamilyHasChildren());
		responsible.setName(model.getName());
		responsible.setCommunity(community);
		responsible.setBirth(model.getBirth());
		responsible.setInSocialProgram(model.isInSocialProgram());
		responsible.setHabitationType(model.getHabitationType());
		responsible.setHabitationMembersCount(model.getHabitationMembersCount());
		responsible.setLiveWith(model.getLiveWith());
		responsible.setFamilyIncome(model.getFamilyIncome());
		responsible.setIncomeParticipation(model.getIncomeParticipation());
		responsible.setDrinkingWaterTreatment(model.getDrinkingWaterTreatment());
		responsible.setHasHospital(model.isHasHospital());
		responsible.setHasSanitation(model.isHasSanitation());
		responsible.setHasWaterTreatment(model.isHasWaterTreatment());
		responsible.setObservations(model.getObservations());
		responsible.setMobileId(model.getMobileId());
		responsible.setAgent(agent);

		if (model.getMother() != null) {
			if (responsible.getMother() == null) {
				responsible.setMother(model.getMother());
			}
			model.getMother().setResponsible(responsible);
			responsible.getMother().setPregnant(model.getMother().isPregnant());
			responsible.getMother().getChildren().forEach(item -> item.setMother(null));
			responsible.getMother().setChildren(setupChild(responsible.getMother(), model.getMother().getChildren(), agent));
			responsible.getMother().setPregnancies(setupPregnancy(responsible.getMother(), model.getMother().getPregnancies(), agent));
		} else {
			responsible.setMother(null);
		}

		if (model.getChildren() != null) {
			List<UUID>	collection	= model.getChildren().stream().map(Base::getUuid).filter(Objects::nonNull).collect(Collectors.toList());
			responsible.getChildren().forEach(item -> {
				if (collection.stream().filter(uuid -> item.getUuid().compareTo(uuid) == 0).findAny().orElse(null) == null) {
					item.setResponsible(null);
				}
			});
			responsible.setChildren(new ArrayList<>(setupChild(responsible, model.getChildren(), agent)));
		}

		return responsible;
	}

	private Collection<Pregnancy> setupPregnancy(Mother mother, Collection<Pregnancy> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Pregnancy	pregnancy	= uuid != null? getPregnancyDAO().findOne(uuid) : null;
			if (pregnancy != null) {
				return getPregnancyBO().setupPregnancy(pregnancy, item, mother, agent);
			} else {
				return getPregnancyBO().setupPregnancy(item, mother, agent);
			}
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("Duplicates")
	private Collection<Child> setupChild(Responsible responsible, Collection<Child> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Child		child		= uuid != null? getChildDAO().findOne(uuid) : null;
			if (child != null) {
				return getChildBO().setupChild(child, item, responsible, agent);
			} else {
				return getChildBO().setupChild(item, responsible, agent);
			}
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("Duplicates")
	private Collection<Child> setupChild(Mother mother, Collection<Child> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Child		child		= uuid != null? getChildDAO().findOne(uuid) : null;
			if (child != null) {
				return getChildBO().setupChild(child, item, mother, agent);
			} else {
				return getChildBO().setupChild(item, mother, agent);
			}
		}).collect(Collectors.toList());
	}
}