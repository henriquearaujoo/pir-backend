package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Mother;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.rest.dto.ResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ResponsibleService extends BService<Responsible, ResponsibleDTO, ResponsibleDAO, Long> {
	private	final CommunityDAO cdao;

	@Autowired
	public ResponsibleService(ResponsibleDAO dao, CommunityDAO cdao) {
		super(dao, Responsible.class, ResponsibleDTO.class);
		this.cdao = cdao;
	}

	public Collection<ResponsibleDTO> findAllResponsible() {
		return dao.findAllResponsible().stream().map(item -> new ResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Collection<ResponsibleDTO> findAllResponsible(Predicate predicate) {
		return dao.findAllResponsible(predicate).stream().map(item -> new ResponsibleDTO(item, false)).collect(Collectors.toSet());
	}

	public Page<ResponsibleDTO> findAllResponsible(Pageable pageable) {
		return dao.findAllResponsible(pageable).map(item -> new ResponsibleDTO(item, false));
	}

	public Page<ResponsibleDTO> findAllResponsible(Pageable pageable, Predicate predicate) {
		return dao.findAllResponsible(predicate, pageable).map(item -> new ResponsibleDTO((Mother) item, false));
	}

	@Override
	public ResponsibleDTO save(ResponsibleDTO create, UserDetails account) {
		Responsible		model		= create.getModel();
		Community 		community	= cdao.findOne(IDCoder.decode(create.getCommunityID()));
		model.setCommunity(community);
		return new ResponsibleDTO(dao.save(model), true);
	}

	@Override
	public ResponsibleDTO update(ResponsibleDTO update, UserDetails account) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= dao.findOne(model.getUuid());
		Community		community	= cdao.findOne(IDCoder.decode(update.getCommunityID()));

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

		return new ResponsibleDTO(dao.save(responsible), true);
	}
}
