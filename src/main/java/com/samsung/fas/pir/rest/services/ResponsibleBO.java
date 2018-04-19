package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ResponsibleBO extends BaseBO<Responsible, ResponsibleDAO, ResponsibleDTO, Long> {
	private	final CommunityDAO cdao;

	@Autowired
	public ResponsibleBO(ResponsibleDAO dao, CommunityDAO cdao) {
		super(dao);
		this.cdao = cdao;
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
		return getDao().findAllResponsible(predicate, pageable).map(item -> new ResponsibleDTO((Mother) item, false));
	}

	@Override
	public ResponsibleDTO save(ResponsibleDTO create, UserDetails account) {
		return new ResponsibleDTO(persist(create, account), true);
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO update, UserDetails account) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= getDao().findOne(model.getUuid());
		Community		community	= cdao.findOne(update.getCommunityUUID());

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

		return new ResponsibleDTO(getDao().save(responsible), true);
	}

	protected Responsible persist(ResponsibleDTO create, UserDetails details) {
		Responsible		model		= create.getModel();
		Community 		community	= cdao.findOne(create.getCommunityUUID());
		model.setCommunity(community);
		return getDao().save(model);
	}
}
