package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommunityBO extends BaseBO<Community, CommunityDAO, CommunityDTO, Long> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		CityDAO				cityDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ResponsibleDAO		responsibleDAO;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		ResponsibleBO		responsibleBO;

	@Autowired
	public CommunityBO(CommunityDAO dao, CityDAO cityDAO, ResponsibleDAO responsibleDAO, ResponsibleBO responsibleBO) {
		super(dao);
		setCityDAO(cityDAO);
		setResponsibleDAO(responsibleDAO);
		setResponsibleBO(responsibleBO);
	}

	@Override
	public CommunityDTO save(CommunityDTO create, Device device, UserDetails details) {
		Community	model		= create.getModel();
		Community	community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		City 		city		= getCityDAO().findOne(create.getCityUUID());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, city, device, details)), device, true) : new CommunityDTO(getDao().save(setupCommunity(model, city, device, details)), device, true);
	}

	@Override
	public CommunityDTO update(CommunityDTO update, Device device, UserDetails details) {
		Community	model		= update.getModel();
		Community	community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		City 		city		= getCityDAO().findOne(update.getCityUUID());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, city, device, details)), device, true) : new CommunityDTO(getDao().save(setupCommunity(model, city, device, details)), device, true);
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

	private Community setupCommunity(Community model, City city, Device device, UserDetails account) {
		model.setCity(city);
		if (!device.isNormal()) {
			model.setResponsible(setupResponsible(model, model.getResponsible(), ((Account) account).getUser(), device));
		}
		return model;
	}

	private Community setupCommunity(Community community, Community model, City city, Device device, UserDetails account) {
		community.setMobileId(model.getMobileId());
		community.setName(model.getName());
		community.setWaterSupply(model.getWaterSupply());
		community.setGarbageDestination(model.getGarbageDestination());
		community.setAccess(model.getAccess());
		community.setHealthServices(model.getHealthServices());
		community.setMainIncome(model.getMainIncome());
		community.hasKindergarten(model.hasKindergarten());
		community.hasElementarySchool(model.hasElementarySchool());
		community.hasHighSchool(model.hasHighSchool());
		community.hasCollege(model.hasCollege());
		community.hasElectricity(model.hasElectricity());
		community.hasCommunityCenter(model.hasCommunityCenter());
		community.hasReligiousPlace(model.hasReligiousPlace());
		community.hasCulturalEvents(model.hasCulturalEvents());
		community.hasPatron(model.hasPatron());
		community.setCulturalProductions(model.getCulturalProductions());
		community.hasCommunityLeaders(model.hasCommunityLeaders());
		community.setCommunityZone(model.getCommunityZone());
		community.setCity(city);
		community.setUc(model.getUc());
		community.setRegional(model.getRegional());
		community.setLatitude(model.getLatitude());
		community.setLongitude(model.getLongitude());
		if (!device.isNormal()) {
			community.setResponsible(setupResponsible(community, model.getResponsible(), ((Account) account).getUser(), device));
		}
		return community;
	}

	private Collection<Responsible> setupResponsible(Community community, Collection<Responsible> collection, User agent, Device device) {
		List<UUID>			modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		setChild((List<Responsible>) collection);
		return collection.stream().map(item -> {
			UUID			uuid			= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			Responsible		responsible		= uuid != null? getResponsibleDAO().findOne(uuid) : null;
			if (responsible != null) {
				responsible.setCommunity(community);
				return getResponsibleBO().setupResponsible(responsible, item, community, agent, device);
			} else {
				item.setCommunity(community);
				return getResponsibleBO().setupResponsible(item, community, agent);
			}
		}).collect(Collectors.toList());
	}

	private void setChild(List<Responsible> responsible) {
		for (int i = 0; i < responsible.size(); i++) {
			for (int k = 0; k < responsible.size(); k++) {
				if (i != k) {
					responsible.get(i).setChildren(merge((List<Child>) responsible.get(i).getChildren(), (List<Child>) responsible.get(k).getChildren()));
				}
			}
		}
	}

	private List<Child> merge(List<Child> first, List<Child> second) {
		return first.stream().map(itemA -> second.stream().filter(itemB -> itemA.getMobileId() == itemB.getMobileId()).findAny().orElse(itemA)).collect(Collectors.toList());
	}
}