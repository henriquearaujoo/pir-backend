package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PregnancyDAO;
import com.samsung.fas.pir.persistence.dao.SAnswerDAO;
import com.samsung.fas.pir.persistence.dao.VisitDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.PregnancyDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PregnancyBO extends BaseBO<Pregnancy, PregnancyDAO, PregnancyDTO, Long> {
	@Getter
	@Setter
	private		VisitDAO		visitDAO;

	@Getter
	@Setter
	private 	SAnswerDAO 		sAnswerDAO;

	@Getter
	@Setter
	private		VisitBO			visitBO;

	@Getter
	@Setter
	private		SAnswerBO		sAnswerBO;

	protected PregnancyBO(PregnancyDAO dao, VisitDAO visitDAO, SAnswerDAO sAnswerDAO, SAnswerBO sAnswerBO, VisitBO visitBO) {
		super(dao);
		setVisitDAO(visitDAO);
		setVisitBO(visitBO);
		setSAnswerDAO(sAnswerDAO);
		setSAnswerBO(sAnswerBO);
	}

	@Override
	public PregnancyDTO save(PregnancyDTO create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public PregnancyDTO update(PregnancyDTO update, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PregnancyDTO> save(Collection<PregnancyDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PregnancyDTO> update(Collection<PregnancyDTO> update, Device device, UserDetails details) {
		return null;
	}

	Pregnancy setupPregnancy(Pregnancy model, Pregnant pregnant) {
//		model.setAgent(agent);
//		model.setPregnant(mother);
//		model.setRegisteredAt(model.getRegisteredAt());
		model.setVisits(setupVisit(model, model.getVisits()));
		model.setAnswers(setupAnswer(model, model.getAnswers()));
		return model;
	}

	Pregnancy setupPregnancy(Pregnancy pregnancy, Pregnancy model, Pregnant pregnant) {
//		pregnancy.setRegisteredAt(model.getRegisteredAt());
//		pregnancy.setPregnant(mother);
		pregnancy.setVisits(setupVisit(pregnancy, model.getVisits()));
		pregnancy.setAnswers(setupAnswer(pregnancy, model.getAnswers()));
		return pregnancy;
	}

	@SuppressWarnings("Duplicates")
	private Collection<SAnswer> setupAnswer(Pregnancy pregnancy, Collection<SAnswer> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			SAnswer		sAnswer		= uuid != null? getSAnswerDAO().findOne(uuid) : null;
			if (sAnswer != null) {
				return getSAnswerBO().setupAnswer(sAnswer, item, pregnancy);
			} else {
				return getSAnswerBO().setupAnswer(item, pregnancy);
			}
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("Duplicates")
	private Collection<Visit> setupVisit(Pregnancy pregnancy, Collection<Visit> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Visit		visit		= uuid != null? getVisitDAO().findOne(uuid) : null;
			if (visit != null) {
				return getVisitBO().setupVisit(visit, item, pregnancy);
			} else {
				return getVisitBO().setupVisit(item, pregnancy);
			}
		}).collect(Collectors.toList());
	}
}
