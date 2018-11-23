package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunitySocialDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunitySocialDimension")
public interface ICommunitySocialDimension extends IBase<CommunitySocialDimension, Long, QCommunitySocialDimension> {
	@Query("SELECT dimension FROM CommunitySocialDimension dimension WHERE " +
			"dimension.culturalProduction 		= :culturalProduction " +
			"AND dimension.income 				= :income " +
			"AND dimension.hasCivicCenter		= :hasCivicCenter " +
			"AND dimension.hasCulturalEvent		= :hasCulturalEvent " +
			"AND dimension.hasLeader			= :hasLeader " +
			"AND dimension.hasPatron			= :hasPatron " +
			"AND dimension.hasReligiousPlace	= :hasReligiousPlace")
	CommunitySocialDimension findOne(String culturalProduction,
									 String income,
									 boolean hasCivicCenter,
									 boolean hasCulturalEvent,
									 boolean hasLeader,
									 boolean hasPatron,
									 boolean hasReligiousPlace);
}
