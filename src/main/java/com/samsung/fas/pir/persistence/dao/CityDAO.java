package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.QCity;
import com.samsung.fas.pir.persistence.repositories.ICity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class CityDAO extends BaseDAO<City, Long, ICity, QCity> {
	@Autowired
	public CityDAO(ICity repository) {
		super(repository);
	}

	public Collection<City> findAllIn(Collection<UUID> collection) {
		return getRepository().findAllByUuidIn(collection);
	}
}