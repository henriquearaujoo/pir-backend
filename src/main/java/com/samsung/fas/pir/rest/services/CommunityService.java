package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommunityService extends BService<Community, CommunityDTO, CommunityDAO, Long> {
	private	final CityDAO cdao;

	@Autowired
	public CommunityService(CommunityDAO dao, @Autowired CityDAO cdao) {
		super(dao, Community.class, CommunityDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CommunityDTO save(CommunityDTO communityDTO, UserDetails account) {
		Community	model		= communityDTO.getModel();
		City 		city		= cdao.findOne(IDCoder.decode(communityDTO.getCityId()));
		model.setCity(city);
		return new CommunityDTO(dao.save(model), true);
	}

	@Override
	public CommunityDTO update(CommunityDTO communityDTO, UserDetails account) {
		Community	model		= communityDTO.getModel();
		Community	community	= dao.findOne(model.getUuid());
		City		city		= cdao.findOne(IDCoder.decode(communityDTO.getCityId()));

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

		return new CommunityDTO(dao.save(community), true);
	}
}
