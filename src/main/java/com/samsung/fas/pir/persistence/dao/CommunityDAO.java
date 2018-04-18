package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.QCommunity;
import com.samsung.fas.pir.persistence.repositories.ICommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityDAO extends BaseDAO<Community, Long, ICommunity,  QCommunity> {
	@Autowired
	public CommunityDAO(ICommunity repository) {
		super(repository);
	}

	public Community findOne(String name, long id) {
		return getRepository().findOneByNameIgnoreCaseAndCityId(name, id);
	}
}