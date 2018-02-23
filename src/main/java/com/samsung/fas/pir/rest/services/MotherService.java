package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.MotherDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.rest.dto.mother.CRUMotherDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MotherService extends BService<Mother, CRUMotherDTO, CRUMotherDTO, CRUMotherDTO, MotherDAO, Long> {
	private CommunityDAO cdao;

	@Autowired
	public MotherService(MotherDAO dao, CommunityDAO cdao) {
		super(dao, Mother.class, CRUMotherDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CRUMotherDTO save(CRUMotherDTO create, Account account) {
		Mother			model		= create.getModel();
		Community 		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(create.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));
		model.setCommunity(community);
		return new CRUMotherDTO(dao.save(model));
	}

	@Override
	public CRUMotherDTO update(CRUMotherDTO update, Account account) {
		Mother			model		= update.getModel();
		Mother			mother		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));
		Community		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(update.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));

		mother.setFamilyHasChildren(model.isFamilyHasChildren());
		mother.setCommunity(community);
		mother.setBirth(model.getBirth());
		mother.setInSocialProgram(model.isInSocialProgram());
		mother.setHabitationType(model.getHabitationType());
		mother.setHabitationMembersCount(model.getHabitationMembersCount());
		mother.setLiveWith(model.getLiveWith());
		mother.setFamilyIncome(model.getFamilyIncome());
		mother.setIncomeParticipation(model.getIncomeParticipation());
		mother.setDrinkingWaterTreatment(model.getDrinkingWaterTreatment());
		mother.setHasHospital(model.isHasHospital());
		mother.setHasSanitation(model.isHasSanitation());
		mother.setHasWaterTreatment(model.isHasWaterTreatment());
		mother.setObservations(model.getObservations());
		mother.setName(model.getName());
		mother.setCivilState(model.getCivilState());
		mother.setChildren(model.getChildren());

		return new CRUMotherDTO(dao.save(mother));
	}
}
