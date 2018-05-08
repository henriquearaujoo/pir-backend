package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.QProfile;
import com.samsung.fas.pir.persistence.repositories.IProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileDAO extends BaseDAO<Profile, Long, IProfile, QProfile> {
	@Autowired
	public ProfileDAO(IProfile repository) {
		super(repository);
	}
}

