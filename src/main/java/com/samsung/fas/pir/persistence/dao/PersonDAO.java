package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Person;
import com.samsung.fas.pir.persistence.models.QPerson;
import com.samsung.fas.pir.persistence.repositories.IPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDAO extends BaseDAO<Person, Long, IPerson, QPerson> {
	@Autowired
	public PersonDAO(IPerson repository) {
		super(repository);
	}
}
