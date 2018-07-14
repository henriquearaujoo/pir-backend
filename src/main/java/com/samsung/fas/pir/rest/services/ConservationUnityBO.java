package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.CityDAO;
import com.samsung.fas.pir.persistence.dao.ConservationUnityDAO;
import com.samsung.fas.pir.persistence.models.ConservationUnity;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.ConservationUnityDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConservationUnityBO extends BaseBO<ConservationUnity, ConservationUnityDAO, ConservationUnityDTO, Long> {
	@Getter
	@Setter
	private		CityDAO			cityDAO;

	protected ConservationUnityBO(ConservationUnityDAO dao, CityDAO cityDAO) {
		super(dao);
		setCityDAO(cityDAO);
	}

	@Override
	public ConservationUnityDTO save(ConservationUnityDTO create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public ConservationUnityDTO update(ConservationUnityDTO update, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ConservationUnityDTO> save(Collection<ConservationUnityDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ConservationUnityDTO> update(Collection<ConservationUnityDTO> update, Device device, UserDetails details) {
		return null;
	}

	@SuppressWarnings("unchecked")
	ConservationUnity setupUnity(ConservationUnity model, Regional regional, User user) {
		model.setRegional(regional);
		model.setCities(getCityDAO().findAllIn((List) model.getCities().stream().map(Base::getUuid).collect(Collectors.toList())));
		return model;
	}

	@SuppressWarnings("unchecked")
	ConservationUnity setupUnity(ConservationUnity unity, ConservationUnity model, Regional regional, User user) {
		unity.setRegional(regional);
		unity.setName(model.getName());
		unity.setAbbreviation(model.getAbbreviation());
		unity.setCities(getCityDAO().findAllIn((List) model.getCities().stream().map(Base::getUuid).collect(Collectors.toList())));
		return unity;
	}
}
