package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.ChildDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.FamilyDAO;
import com.samsung.fas.pir.persistence.dao.PregnancyDAO;
import com.samsung.fas.pir.persistence.models.*;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FamilyBO extends BaseBO<Family, FamilyDAO, FamilyDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ChildDAO		childDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		CommunityDAO	communityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PregnancyDAO	pregnancyDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper		mapper;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		PregnancyBO		pregnancyBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ChildBO			childBO;

	@Autowired
	public FamilyBO(FamilyDAO dao, ChildDAO childDAO, PregnancyDAO pregnancyDAO, CommunityDAO communityDAO, ModelMapper mapper, PregnancyBO pregnancyBO, ChildBO childBO) {
		super(dao);
		setChildDAO(childDAO);
		setPregnancyDAO(pregnancyDAO);
		setChildBO(childBO);
		setPregnancyBO(pregnancyBO);
		setCommunityDAO(communityDAO);
		setMapper(mapper);
	}

	@Override
	public FamilyDTO save(FamilyDTO create, Device device, UserDetails account) {
		Family 			model		= create.getModel();
		Family			family 		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Community		community	= getCommunityDAO().findOne(create.getCommunityUUID());
		return family != null? new FamilyDTO(getDao().save(setupFamily(family, community)), device, true) : new FamilyDTO(getDao().save(setupFamily(model, community)), device, true);
	}

	@Override
	public FamilyDTO update(FamilyDTO update, Device device, UserDetails account) {
		Family 			model		= update.getModel();
		Family			family 		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Community		community	= getCommunityDAO().findOne(update.getCommunityUUID());
		return family != null? new FamilyDTO(getDao().save(setupFamily(family, model, community, device)), device, true) : new FamilyDTO(getDao().save(setupFamily(model, community)), device, true);
	}

	@Override
	public Collection<FamilyDTO> save(Collection<FamilyDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> save(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<FamilyDTO> update(Collection<FamilyDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> update(item, device, details)).collect(Collectors.toList());
	}

	Family setupFamily(Family model, Community community) {
		model.setCommunity(community);
		model.setChildren(setupChild(model, model.getChildren()));
		model.setPregnant(setupPregnant(model, model.getPregnant()));
		return model;
	}

	Family setupFamily(Family family, Family model, Community community, Device device) {
		getMapper().map(model, family);
		family.setPregnant(setupPregnant(family, model.getPregnant()));
		family.setChildren(setupChild(family, model.getChildren()));
		return family;
	}

	private Collection<Pregnant> setupPregnant(Family mother, Collection<Pregnant> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Pregnancy	pregnancy	= uuid != null? getPregnancyDAO().findOne(uuid) : null;
			if (pregnancy != null) {
//				return getPregnancyBO().setupPregnancy(pregnancy, item, mother, agent);
				return new Pregnant();
			} else {
//				return getPregnancyBO().setupPregnancy(item, mother, agent);
				return new Pregnant();
			}
		}).collect(Collectors.toList());
	}

	private Collection<Child> setupChild(Family family, Collection<Child> collection) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Child		child		= uuid != null? getChildDAO().findOne(uuid) :  null;
			if (child != null) {
				return getChildBO().setupChild(child, item, family);
			} else {
				return getChildBO().setupChild(item, family);
			}
		}).collect(Collectors.toList());
	}
}