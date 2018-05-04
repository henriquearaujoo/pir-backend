package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.persistence.models.Responsible;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResponsibleBO extends BaseBO<Responsible, ResponsibleDAO, ResponsibleDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CommunityDAO		communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE, onMethod = @__({@Autowired}))
	private		CityDAO				cityDAO;

	@Autowired
	public ResponsibleBO(ResponsibleDAO dao) {
		super(dao);
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
		return new ResponsibleDTO(getDao().save(persist(create, account)), true);
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO update, UserDetails account) {
		return new ResponsibleDTO(getDao().save(patch(update, account)), true);
	}

	@Override
	public Collection<ResponsibleDTO> save(Collection<ResponsibleDTO> collection, UserDetails details) {
		ArrayList<Responsible>		models		= new ArrayList<>();

		for (ResponsibleDTO item : collection) {
			if (item.getUuid() == null) {
				models.add(persist(item, details));
			} else {
				models.add(patch(item, details));
			}
		}

		return getDao().save(models).stream().map(responsible -> new ResponsibleDTO(responsible, true)).collect(Collectors.toList());
	}

	@Override
	public Collection<ResponsibleDTO> update(Collection<ResponsibleDTO> update, UserDetails details) {
		return null;
	}

	protected Responsible persist(ResponsibleDTO create, UserDetails details) {
		Responsible		model		= create.getModel();

		if (model.getCommunity() != null) {
			if (model.getCommunity().getUuid() != null) {
				model.setCommunity(CommunityBO.setupCommunity(getCommunityDAO().findOne(model.getCommunity().getUuid()), model.getCommunity(), getCityDAO().findOne(create.getCommunity().getCityUUID())));
			} else {
				model.getCommunity().setCity(getCityDAO().findOne(create.getCommunity().getCityUUID()));
			}
		}
		return model;
	}

	protected Responsible patch(ResponsibleDTO update, UserDetails details) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= getDao().findOne(model.getUuid());
		Community 		community	= update.getCommunity().getModel();

		community.setId(community.getUuid() != null? getCommunityDAO().findOne(community.getUuid()).getId() : 0L);
		community.setCity(getCityDAO().findOne(update.getCommunity().getCityUUID()));

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

		return responsible;
	}
}
