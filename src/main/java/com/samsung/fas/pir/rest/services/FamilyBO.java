package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.FamilyDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.FamilyDTO;
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

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		UserBO			userBO;

	@Autowired
	public FamilyBO(FamilyDAO dao, CommunityBO communityBO, UserBO userBO, PregnantBO pregnantBO, ChildBO childBO, @Lazy ModelMapper mapper) {
		super(dao);
		setCommunityBO(communityBO);
		setPregnantBO(pregnantBO);
		setChildBO(childBO);
		setUserBO(userBO);
		setMapper(mapper);
	}

	public Collection<FamilyDTO> findAllByAgent(UUID uuid, Device device, UserDetails details) {
		return getDao().findAllByAgent(uuid).stream().map(item -> new FamilyDTO(item, device, false)).collect(Collectors.toList());
	}

	@Override
	public FamilyDTO save(FamilyDTO create, Device device, UserDetails account) {
		Family 			model		= create.getModel();
		Family			family 		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		Agent			agent		= getUserBO().getAgentDAO().findOne(create.getAgentUUID());
		Community		community	= getCommunityBO().getDao().findOne(create.getCommunityUUID());
		return family != null? new FamilyDTO(getDao().save(setupFamily(family, model, agent, community, device)), device, true) : new FamilyDTO(getDao().save(setupFamily(model, agent, community, device)), device, true);
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

	private Family setupFamily(Family model, Agent agent, Community community, Device device) {
		model.setCode(getDao().getSequentialCode(community.getUnity().getAbbreviation().toUpperCase().concat("F")));
		model.setCommunity(community);
		model.setAgent(agent);
		model.setPregnant(setupPregnant(model, model.getPregnant(), device));
		model.setChildren(setupChild(model, model.getChildren(), agent));
		return model;
	}

	private Family setupFamily(Family family, Family model, Agent agent, Community community, Device device) {
		getMapper().map(model, family);
		family.setCommunity(community);
		family.setAgent(agent);
		family.setPregnant(setupPregnant(family, model.getPregnant(), device));
		family.setChildren(setupChild(family, model.getChildren(), agent));
		return family;
	}

	private Collection<Pregnant> setupPregnant(Family mother, Collection<Pregnant> collection, Device device) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Pregnant	pregnant	= uuid != null? getPregnantBO().getDao().findOne(uuid) : null;
			if (pregnant != null) {
				return getPregnantBO().setupPregnant(pregnant, item, mother, device);
			} else {
				return getPregnantBO().setupPregnant(item, mother, device);
			}
		}).collect(Collectors.toList());
	}

	private Collection<Child> setupChild(Family family, Collection<Child> collection, Agent agent) {
		Collection<UUID>		modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Child		child		= uuid != null? getChildBO().getDao().findOne(uuid) :  null;
			if (child != null) {
				return getChildBO().setupChild(child, item, family, agent);
			} else {
				return getChildBO().setupChild(item, family, agent);
			}
		}).collect(Collectors.toList());
	}
}