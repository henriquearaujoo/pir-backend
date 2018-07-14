package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommunityBO extends BaseBO<Community, CommunityDAO, CommunityDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ConservationUnityBO		unityBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		FamilyBO				familyBO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	ModelMapper 			mapper;

	@Autowired
	public CommunityBO(CommunityDAO dao, ConservationUnityBO unityBO, FamilyBO familyBO, ModelMapper mapper) {
		super(dao);
		setUnityBO(unityBO);
		setFamilyBO(familyBO);
		setMapper(mapper);
	}

	@Override
	public CommunityDTO save(CommunityDTO create, Device device, UserDetails details) {
		Community				model		= create.getModel();
		Community				community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		ConservationUnity 		unity		= getUnityBO().getDao().findOne(model.getUnity().getUuid());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, unity, device, details)), device, true) : new CommunityDTO(getDao().save(setupCommunity(model, unity, device, details)), device, true);
	}

	@Override
	public CommunityDTO update(CommunityDTO update, Device device, UserDetails details) {
		Community				model		= update.getModel();
		Community				community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		ConservationUnity 		unity		= getUnityBO().getDao().findOne(model.getUnity().getUuid());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, unity, device, details)), device, true) : new CommunityDTO(getDao().save(setupCommunity(model, unity, device, details)), device, true);
	}

	@Override
	public Collection<CommunityDTO> save(Collection<CommunityDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> trySave(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<CommunityDTO> update(Collection<CommunityDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> tryUpdate(item, device, details)).collect(Collectors.toList());
	}

	private CommunityDTO tryUpdate(CommunityDTO update, Device device, UserDetails details) {
		try {
			return update(update, device, details);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private CommunityDTO trySave(CommunityDTO save, Device device, UserDetails details) {
		try {
			return save(save, device, details);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Community setupCommunity(Community model, ConservationUnity unity, Device device, UserDetails account) {
		model.setCity(unity.getCities().stream().filter(item -> item.getUuid().compareTo(model.getCity().getUuid()) == 0).findAny().orElseThrow(() -> new ServiceException("city.not.found.in.uc")));
		model.setUnity(unity);
		model.getFamily().clear();
		return model;
	}

	private Community setupCommunity(Community community, Community model, ConservationUnity unity, Device device, UserDetails account) {
		if (device.isNormal()) {
			getMapper().map(model, community);
			community.setCity(unity.getCities().stream().filter(item -> item.getUuid().compareTo(model.getCity().getUuid()) == 0).findAny().orElseThrow(() -> new ServiceException("city.not.found.in.uc")));
			community.setUnity(unity);
		}
		community.getFamily().clear();
		community.getFamily().addAll(setupFamily(community, model.getFamily(), ((Account) account).getUser(), device));
		return community;
	}

	private Collection<Family> setupFamily(Community community, Collection<Family> collection, User agent, Device device) {
		List<UUID>		modelIDs	= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID		uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Family		family		= uuid != null? getFamilyBO().getDao().findOne(uuid) : null;
			if (family != null) {
				return getFamilyBO().setupFamily(family, item, community, agent, device);
			} else {
				return getFamilyBO().setupFamily(item, community, agent);
			}
		}).collect(Collectors.toList());
	}
}