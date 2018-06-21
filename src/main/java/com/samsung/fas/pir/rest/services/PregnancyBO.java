package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PregnancyDAO;
import com.samsung.fas.pir.persistence.dao.VisitDAO;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.models.Visit;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.PregnancyDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PregnancyBO extends BaseBO<Pregnancy, PregnancyDAO, PregnancyDTO, Long> {
	@Getter
	@Setter
	private		VisitDAO		visitDAO;

	@Getter
	@Setter
	private		VisitBO			visitBO;

	protected PregnancyBO(PregnancyDAO dao, VisitDAO visitDAO, VisitBO visitBO) {
		super(dao);
		setVisitDAO(visitDAO);
		setVisitBO(visitBO);
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

	Pregnancy setupPregnancy(Pregnancy model, Responsible mother, User agent) {
		model.setAgent(agent);
		model.setPregnant(mother);
		model.setRegisteredAt(new Date());
		model.setVisits(setupVisit(model, model.getVisits(), agent));
		return model;
	}

	Pregnancy setupPregnancy(Pregnancy pregnancy, Pregnancy model, Responsible mother, User agent) {
		pregnancy.setRegisteredAt(model.getRegisteredAt());
		pregnancy.setPregnant(mother);
		pregnancy.setVisits(setupVisit(pregnancy, model.getVisits(), agent));
		return pregnancy;
	}

	@SuppressWarnings("Duplicates")
	private Collection<Visit> setupVisit(Pregnancy pregnancy, Collection<Visit> collection, User agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Visit		visit		= uuid != null? getVisitDAO().findOne(uuid) : null;
			if (visit != null) {
				return getVisitBO().setupVisit(visit, item, pregnancy, agent);
			} else {
				return getVisitBO().setupVisit(item, pregnancy, agent);
			}
		}).collect(Collectors.toList());
	}
}
