package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.CommunityDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.rest.dto.CommunityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CommunityBO extends BaseBO<Community, CommunityDAO, CommunityDTO, Long> {
	@Getter
	@Setter(onMethod = @__({@Autowired}))
	private		CityDAO		cityDAO;

	@Autowired
	public CommunityBO(CommunityDAO dao, @Autowired CityDAO cdao) {
		super(dao);
	}

	@Override
	public CommunityDTO save(CommunityDTO create, UserDetails account) {
		Community	model		= create.getModel();
		City 		city		= getCityDAO().findOne(create.getCityUUID());
		model.setCity(city);
		return new CommunityDTO(getDao().save(model), true);
	}

	@Override
	public CommunityDTO update(CommunityDTO update, UserDetails account) {
		Community	model		= update.getModel();
		Community	community	= getDao().findOne(model.getUuid());
		City		city		= getCityDAO().findOne(update.getCityUUID());

		return new CommunityDTO(getDao().save(setupCommunity(community, model, city)), true);
	}

	@Override
	public Collection<CommunityDTO> save(Collection<CommunityDTO> collection, UserDetails account) {
		ArrayList<Community>	response	= new ArrayList<>();
		ArrayList<City>			cities		= (ArrayList<City>) getCityDAO().findAllIn(collection.stream().map(CommunityDTO::getCityUUID).collect(Collectors.toList()));

		for (CommunityDTO item : collection) {
			Community	model		= item.getModel();
			City		city		= cities.stream().filter(c -> c.getUuid().compareTo(item.getCityUUID()) == 0).findAny().orElseThrow(() -> new RESTException("not.found"));

			if (model.getUuid() == null) {
				model.setCity(city);
			} else {
				model = setupCommunity(getDao().findOne(model.getUuid()), model, city);
			}

			try {
				Community 	community	= getDao().findOne(model.getName(), city.getId());
				if (community != null) {
					response.add(getDao().save(setupCommunity(community, model, city)));
				} else {
					response.add(getDao().save(model));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return response.stream().map(item -> new CommunityDTO(item, true)).collect(Collectors.toList());
	}

	@Override
	public Collection<CommunityDTO> update(Collection<CommunityDTO> update, UserDetails details) {
		return null;
	}

	protected static Community setupCommunity(Community community, Community model, City city) {
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
		return community;
	}
}
