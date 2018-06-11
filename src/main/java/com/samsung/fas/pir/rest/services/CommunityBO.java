package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.dao.ResponsibleDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
	public CommunityDTO save(CommunityDTO create, UserDetails details) {
		Community	model		= create.getModel();
		Community	community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		City 		city		= getCityDAO().findOne(create.getCityUUID());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, city, details)), true) : new CommunityDTO(getDao().save(setupCommunity(model, city, details)), true);
	}

	@Override
	public CommunityDTO update(CommunityDTO update, UserDetails details) {
		Community	model		= update.getModel();
		Community	community	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		City 		city		= getCityDAO().findOne(update.getCityUUID());
		return community != null? new CommunityDTO(getDao().save(setupCommunity(community, model, city, details)), true) : new CommunityDTO(getDao().save(setupCommunity(model, city, details)), true);
	}

	@Override
	public Collection<CommunityDTO> save(Collection<CommunityDTO> collection, UserDetails details) {
		return collection.stream().map(item -> save(item, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<CommunityDTO> update(Collection<CommunityDTO> collection, UserDetails details) {
		return collection.stream().map(item -> update(item, details)).collect(Collectors.toList());
	}

	private Community setupCommunity(Community model, City city, UserDetails account) {
		model.setCity(city);
		model.setResponsible(setupResponsible(model, model.getResponsible(), account));
		return model;
	}

	private Community setupCommunity(Community community, Community model, City city, UserDetails account) {
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
		if (model.getResponsible() != null) {
			community.getResponsible().clear();
			community.getResponsible().addAll(setupResponsible(community, model.getResponsible(), account));
		}
		return community;
	}

	private Collection<Responsible> setupResponsible(Community community, Collection<Responsible> collection, UserDetails account) {
		Collection<Responsible>		persisted		= getResponsibleDAO().findAllIn(collection.stream().map(Base::getUuid).collect(Collectors.toList()));
		return collection.stream().map(item -> {
			Responsible		responsible		= persisted.stream().filter(entity -> entity.getUuid().compareTo(item.getUuid()) == 0).findAny().orElse(null);
			if (responsible != null) {
				responsible.setCommunity(community);
				return getResponsibleBO().setupResponsible(responsible, item, community, ((Account) account).getUser());
			} else {
				item.setCommunity(community);
				return getResponsibleBO().setupResponsible(item, community, ((Account) account).getUser());
			}
		}).collect(Collectors.toList());
	}
}