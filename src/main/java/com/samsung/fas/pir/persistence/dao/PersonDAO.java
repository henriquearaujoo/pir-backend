package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Person;
import com.samsung.fas.pir.persistence.models.QPerson;
import com.samsung.fas.pir.persistence.repositories.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDAO extends BaseDAO<Person, Long, IPersonRepository, QPerson> {
	@Autowired
	public PersonDAO(IPersonRepository repository) {
		super(repository);
	}
}
