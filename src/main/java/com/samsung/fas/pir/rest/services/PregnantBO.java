package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.PregnantDAO;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.FamilyDTO;
import com.samsung.fas.pir.rest.dto.PregnantDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
	private 	FamilyBO		familyBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	PregnancyBO		pregnancyBO;

	@Autowired
	protected PregnantBO(PregnantDAO dao, @Lazy FamilyBO familyBO, PregnancyBO pregnancyBO, @Lazy ModelMapper mapper) {
		super(dao);
		setMapper(mapper);
		setFamilyBO(familyBO);
		setPregnancyBO(pregnancyBO);
	}

	@Override
	public PregnantDTO save(PregnantDTO create, Device device, UserDetails details) {
		Pregnant		model		= create.getModel();
		Pregnant		pregnant	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Family			family		= getFamilyBO().getDao().findOne(create.getFamilyUUID());
		return new PregnantDTO(getDao().save(pregnant != null? setupPregnant(pregnant, model, family, device) : setupPregnant(model, family, device)), device, true);
	}

	@Override
	public PregnantDTO update(PregnantDTO update, Device device, UserDetails details) {
		return save(update, device, details);
	}

	@Override
	public Collection<PregnantDTO> save(Collection<PregnantDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<PregnantDTO> update(Collection<PregnantDTO> update, Device device, UserDetails details) {
		return null;
	}

	Pregnant setupPregnant(Pregnant model, Family family, Device device) {
		model.setCode(getDao().getSequentialCode(family.getCommunity().getUnity().getAbbreviation().toUpperCase().concat("G")));
		model.setFamily(family);
		if (!device.isNormal()) {
			model.setPregnancies(setupPregnancy(model, model.getPregnancies()));
		}
		return model;
	}

	Pregnant setupPregnant(Pregnant pregnant, Pregnant model, Family family, Device device) {
		getMapper().map(model, pregnant);
		pregnant.setFamily(family);
		if (!device.isNormal()) {
			pregnant.setPregnancies(setupPregnancy(pregnant, model.getPregnancies()));
		}
		return pregnant;
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