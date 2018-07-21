package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.FamilyDAO;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.Pregnant;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.FamilyDTO;
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
public class FamilyBO extends BaseBO<Family, FamilyDAO, FamilyDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper		mapper;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		CommunityBO		communityBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PregnantBO		pregnantBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ChildBO			childBO;

	@Autowired
	public FamilyBO(FamilyDAO dao, CommunityBO communityBO, PregnantBO pregnantBO, ChildBO childBO, ModelMapper mapper) {
		super(dao);
		setCommunityBO(communityBO);
		setPregnantBO(pregnantBO);
		setChildBO(childBO);
		setMapper(mapper);
	}

	@Override
	public FamilyDTO save(FamilyDTO create, Device device, UserDetails account) {
		Family 			model		= create.getModel();
		Family			family 		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Community		community	= getCommunityBO().getDao().findOne(create.getCommunityUUID());
		return family != null? new FamilyDTO(getDao().save(setupFamily(family, model, community)), device, true) : new FamilyDTO(getDao().save(setupFamily(model, community)), device, true);
	}

	@Override
	public FamilyDTO update(FamilyDTO update, Device device, UserDetails account) {
		return save(update, device, account);
	}

	@Override
	public Collection<FamilyDTO> save(Collection<FamilyDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> save(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<FamilyDTO> update(Collection<FamilyDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> save(item, device, details)).collect(Collectors.toList());
	}

	private Family setupFamily(Family model, Community community) {
		model.setCode(getDao().getSequentialCode(community.getUnity().getAbbreviation().toUpperCase().concat("F")));
		model.setCommunity(community);
		model.setPregnant(setupPregnant(model, model.getPregnant()));
		model.setChildren(setupChild(model, model.getChildren()));
		return model;
	}

	private Family setupFamily(Family family, Family model, Community community) {
		getMapper().map(model, family);
		family.setCommunity(community);
		family.setPregnant(setupPregnant(family, model.getPregnant()));
		family.setChildren(setupChild(family, model.getChildren()));
		return family;
	}

	private Collection<Pregnant> setupPregnant(Family mother, Collection<Pregnant> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Pregnant	pregnant	= uuid != null? getPregnantBO().getDao().findOne(uuid) : null;
			if (pregnant != null) {
				return getPregnantBO().setupPregnant(pregnant, item, mother);
			} else {
				return getPregnantBO().setupPregnant(item, mother);
			}
		}).collect(Collectors.toList());
	}

	private Collection<Child> setupChild(Family family, Collection<Child> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Child		child		= uuid != null? getChildBO().getDao().findOne(uuid) :  null;
			if (child != null) {
				return getChildBO().setupChild(child, item, family);
			} else {
				return getChildBO().setupChild(item, family);
			}
		}).collect(Collectors.toList());
	}
}