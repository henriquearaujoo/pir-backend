package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PregnantDAO;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.PregnantDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PregnantBO extends BaseBO<Pregnant, PregnantDAO, PregnantDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper		mapper;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	PregnancyBO		pregnancyBO;

	@Autowired
	protected PregnantBO(PregnantDAO dao, PregnancyBO pregnancyBO, ModelMapper mapper) {
		super(dao);
		setMapper(mapper);
		setPregnancyBO(pregnancyBO);
	}

	@Override
	public PregnantDTO save(PregnantDTO create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public PregnantDTO update(PregnantDTO update, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PregnantDTO> save(Collection<PregnantDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PregnantDTO> update(Collection<PregnantDTO> update, Device device, UserDetails details) {
		return null;
	}

	Pregnant setupPregnant(Pregnant model, Family family) {
		model.setCode(getDao().getSequentialCode(family.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("G")));
		model.setFamily(family);
		model.setPregnancies(setupPregnancy(model, model.getPregnancies()));
		return model;
	}

	Pregnant setupPregnant(Pregnant pregnant, Pregnant model, Family family) {
		getMapper().map(model, pregnant);
		pregnant.setPregnancies(setupPregnancy(pregnant, model.getPregnancies()));
		return model;
	}

	private Collection<Pregnancy> setupPregnancy(Pregnant pregnant, Collection<Pregnancy> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Pregnancy	pregnancy	= uuid != null? getPregnancyBO().getDao().findOne(uuid) : null;
			if (pregnancy != null) {
				return getPregnancyBO().setupPregnancy(pregnancy, item, pregnant);
			} else {
				return getPregnancyBO().setupPregnancy(item, pregnant);
			}
		}).collect(Collectors.toList());
	}
}