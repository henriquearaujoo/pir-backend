package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.entity.City;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.rest.dto.community.CCommunityDTO;
import com.samsung.fas.pir.rest.dto.community.RCommunityDTO;
import com.samsung.fas.pir.rest.dto.community.UCommunityDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunityService extends BService<Community, CCommunityDTO, RCommunityDTO, UCommunityDTO, CommunityDAO, Long> {
	private CityDAO cdao;

	@Autowired
	public CommunityService(CommunityDAO dao, CityDAO cdao) {
		super(dao, RCommunityDTO.class);
		this.cdao = cdao;
	}

	@Override
	public RCommunityDTO save(CCommunityDTO communityDTO, Account account) {
		Community	model		= communityDTO.getModel();
		City		city		= Optional.ofNullable(cdao.findCityByID(communityDTO.getCity())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));
		Community	community	= dao.findOne(model.getName(), city.getId());

		if (community != null)
			throw new RESTRuntimeException("community.found");

		model.setCity(city);

		return new RCommunityDTO().toDTO(dao.save(model));
	}

	@Override
	public RCommunityDTO update(UCommunityDTO communityDTO, Account account) {
		Community	model		= communityDTO.getModel();
		Community	community	= Optional.ofNullable(dao.findOne(model.getUuid())).orElseThrow(() -> new RESTRuntimeException("community.notfound"));
		City		city		= Optional.ofNullable(cdao.findCityByID(communityDTO.getCity())).orElseThrow(() -> new RESTRuntimeException("city.notfound"));
		Community	temp		= dao.findOne(model.getName(), city.getId());

		if (temp.getId() != community.getId())
			throw new RESTRuntimeException("community.found");

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
		community.hasCulturalProductions(model.hasCulturalProductions());
		community.hasCommunityLeaders(model.hasCommunityLeaders());
		community.setCommunityZone(model.getCommunityZone());
		community.setCity(city);

		return new RCommunityDTO().toDTO(dao.save(community));
	}
}
