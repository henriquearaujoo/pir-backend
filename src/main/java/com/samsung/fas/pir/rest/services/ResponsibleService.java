package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.rest.dto.responsible.CUResponsibleDTO;
import com.samsung.fas.pir.rest.dto.responsible.RResponsibleDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponsibleService extends BService<Responsible, CUResponsibleDTO, RResponsibleDTO, CUResponsibleDTO, ResponsibleDAO, Long> {
	private		CommunityDAO		cdao;

	@Autowired
	public ResponsibleService(ResponsibleDAO dao, CommunityDAO cdao) {
		super(dao, Responsible.class, RResponsibleDTO.class);
		this.cdao = cdao;
	}

	@Override
	public RResponsibleDTO save(CUResponsibleDTO create, Account account) {
		Responsible		model		= create.getModel();
		Community		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(create.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));
		model.setCommunity(community);
		return new RResponsibleDTO(dao.save(model));
	}

	@Override
	public RResponsibleDTO update(CUResponsibleDTO update, Account account) {
		Responsible		model		= update.getModel();
		Responsible		responsible	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("responsible.notfound"));
		Community		community	= Optional.ofNullable(cdao.findOne(IDCoder.decode(update.getCommunityID()))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));

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

		return new RResponsibleDTO(dao.save(responsible));
	}
}
