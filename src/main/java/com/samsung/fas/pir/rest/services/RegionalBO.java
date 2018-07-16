package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.dao.RegionalDAO;
import com.samsung.fas.pir.persistence.models.ConservationUnity;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.rest.dto.RegionalDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
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
public class RegionalBO extends BaseBO<Regional, RegionalDAO, RegionalDTO, Long> {
	@Getter
	@Setter
	private		ConservationUnityBO		conservationUnityBO;

	@Autowired
	protected RegionalBO(RegionalDAO dao, ConservationUnityBO conservationUnityBO) {
		super(dao);
		setConservationUnityBO(conservationUnityBO);
	}

	@Override
	public RegionalDTO save(RegionalDTO create, Device device, UserDetails details) {
		Regional		model		= create.getModel();
		Regional		regional	= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;
		if (regional != null) {
			regional.setName(model.getName());
			regional.setUnities(setupUnity(regional, model.getUnities(), ((Account) details).getUser()));
			return new RegionalDTO(getDao().save(regional), device, true);
		}
		model.setUnities(setupUnity(model, model.getUnities(), null/*((Account) details).getUser())*/));
		return new RegionalDTO(getDao().save(model), device, true);
	}

	@Override
	public RegionalDTO update(RegionalDTO update, Device device, UserDetails details) {
		return save(update, device, details);
	}

	@Override
	public Collection<RegionalDTO> save(Collection<RegionalDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> trySave(item, device, details)).collect(Collectors.toList());
	}

	@Override
	public Collection<RegionalDTO> update(Collection<RegionalDTO> collection, Device device, UserDetails details) {
		return collection.stream().map(item -> tryUpdate(item, device, details)).collect(Collectors.toList());
	}

	private RegionalDTO tryUpdate(RegionalDTO update, Device device, UserDetails details) {
		try {
			return update(update, device, details);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private RegionalDTO trySave(RegionalDTO save, Device device, UserDetails details) {
		try {
			return save(save, device, details);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Collection<ConservationUnity> setupUnity(Regional regional, Collection<ConservationUnity> collection, User user) {
		List<UUID> 			modelIDs		= collection.stream().map(Base::getUuid).collect(Collectors.toList());
		return collection.stream().map(item -> {
			UUID					uuid		= modelIDs.stream().filter(id -> item.getUuid() != null && id != null && id.compareTo(item.getUuid()) == 0).findAny().orElse(null);
			ConservationUnity		unity		= uuid != null? getConservationUnityBO().getDao().findOne(uuid) : null;
			if (unity != null) {
				return getConservationUnityBO().setupUnity(unity, item, regional, user);
			} else {
				return getConservationUnityBO().setupUnity(item, regional, user);
			}
		}).collect(Collectors.toList());
	}
}
