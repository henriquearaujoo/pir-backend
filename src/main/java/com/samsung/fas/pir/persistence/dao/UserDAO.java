package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.QUser;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO extends BaseDAO<User, Long, IUserRepository, QUser> {
	@Autowired
	public UserDAO(IUserRepository repository) {
		super(repository);
	}
}
