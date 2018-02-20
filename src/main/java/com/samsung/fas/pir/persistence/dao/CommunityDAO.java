package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.entity.QCommunity;
import com.samsung.fas.pir.persistence.repository.ICommunityRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityDAO extends BaseDAO<Community, Long, QCommunity> {
	public Community findOne(String name, long id) {
		return ((ICommunityRepository) repository).findOneByNameIgnoreCaseAndCityId(name, id);
	}
}