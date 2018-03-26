package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.QCity;
import com.samsung.fas.pir.persistence.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDAO extends BaseDAO<City, Long, QCity> {
	@Autowired
	public CityDAO(ICityRepository repository) {
		super(repository);
	}
}