package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.rest.dto.responsible.CRUResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResponsibleService extends BService<Responsible, CRUResponsibleDTO, ResponsibleDAO, Long> {
	private		CommunityDAO		cdao;

	@Autowired
	public ResponsibleService(ResponsibleDAO dao, CommunityDAO cdao) {
		super(dao, Responsible.class, CRUResponsibleDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CRUResponsibleDTO save(CRUResponsibleDTO create, UserDetails account) {
		Responsible		model		= create.getModel();
		Community		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(create.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));
		model.setCommunity(community);
		return new CRUResponsibleDTO(dao.save(model), true);
	}

	@Override
	public CRUResponsibleDTO update(CRUResponsibleDTO update, UserDetails account) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));
		Community		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(update.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));

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

		return new CRUResponsibleDTO(dao.save(responsible), true);
	}

	public Collection<CRUResponsibleDTO> findAllResponsible() {
		return dao.findAllResponsible().stream().map(item -> new CRUResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Collection<CRUResponsibleDTO> findAllResponsible(Predicate predicate) {
		return dao.findAllResponsible(predicate).stream().map(item -> new CRUResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Page<CRUResponsibleDTO> findAllResponsible(Pageable pageable) {
		return dao.findAllResponsible(pageable).map(item -> new CRUResponsibleDTO(item, false));
	}

	public Page<CRUResponsibleDTO> findAllResponsible(Pageable pageable, Predicate predicate) {
		return dao.findAllResponsible(predicate, pageable).map(item -> new CRUResponsibleDTO(item, false));
	}
}
