package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.entity.City;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.rest.dto.community.CRUCommunityDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunityService extends BService<Community, CRUCommunityDTO, CommunityDAO, Long> {
	private CityDAO cdao;

	@Autowired
	public CommunityService(CommunityDAO dao, CityDAO cdao) {
		super(dao, Community.class, CRUCommunityDTO.class);
		this.cdao = cdao;
	}

	@Override
	public CRUCommunityDTO save(CRUCommunityDTO communityDTO, UserDetails account) {
		Community	model		= communityDTO.getModel();
		City		city		= Optional.ofNullable(cdao.findOne(IDCoder.decode(communityDTO.getCityId()))).orElseThrow(() -> new RESTRuntimeException("city.notfound"));
		model.setCity(city);
		return new CRUCommunityDTO(dao.save(model), true);
	}

	@Override
	public CRUCommunityDTO update(CRUCommunityDTO communityDTO, UserDetails account) {
		Community	model		= communityDTO.getModel();
		Community	community	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("community.notfound"));
		City		city		= Optional.ofNullable(cdao.findOne(IDCoder.decode(communityDTO.getCityId()))).orElseThrow(() -> new RESTRuntimeException("city.notfound"));

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

		return new CRUCommunityDTO(dao.save(community), true);
	}
}
