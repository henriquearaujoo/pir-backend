package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Greetings;
import com.samsung.fas.pir.persistence.models.QGreetings;
import com.samsung.fas.pir.persistence.repositories.IGreetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingsDAO extends BaseDAO<Greetings, Long, IGreetingsRepository, QGreetings> {
	@Autowired
	public GreetingsDAO(IGreetingsRepository repository) {
		super(repository);
	}
}
