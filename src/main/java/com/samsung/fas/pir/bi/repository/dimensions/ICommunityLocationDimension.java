package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityLocationDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityLocationDimension")
public interface ICommunityLocationDimension extends IBase<CommunityLocationDimension, Long, QCommunityLocationDimension> {
	@Query("SELECT dimension FROM CommunityLocationDimension dimension WHERE " +
			"dimension.name 			= :name " +
			"AND dimension.zone 		= :zone " +
			"AND dimension.access		= :access " +
			"AND dimension.city			= :city " +
			"AND dimension.unity		= :unity " +
			"AND dimension.regional		= :regional " +
			"AND dimension.state		= :state")
	CommunityLocationDimension findOne(String name, ECommunityZone zone, String access, String city, String unity, String regional, String state);
}
