package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.QProfile;
import com.samsung.fas.pir.persistence.repositories.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileDAO extends BaseDAO<Profile, Long, QProfile> {
	@Autowired
	public ProfileDAO(IProfileRepository repository) {
		super(repository);
	}
}

