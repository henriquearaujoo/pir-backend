package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommunityBO extends BaseBO<Community, CommunityDAO, CommunityDTO, Long> {
	private	final CityDAO cdao;

	@Autowired
	public CommunityBO(CommunityDAO dao, @Autowired CityDAO cdao) {
		super(dao);
		this.cdao = cdao;
	}

	@Override
	public CommunityDTO save(CommunityDTO create, UserDetails account) {
		Community	model		= create.getModel();
		City 		city		= cdao.findOne(create.getCityUUID());
		model.setCity(city);
		return new CommunityDTO(getDao().save(model), true);
	}

	@Override
	public CommunityDTO update(CommunityDTO update, UserDetails account) {
		Community	model		= update.getModel();
		Community	community	= getDao().findOne(model.getUuid());
		City		city		= cdao.findOne(update.getCityUUID());

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
		community.hasEletricity(model.hasEletricity());
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

		return new CommunityDTO(getDao().save(community), true);
	}
}
