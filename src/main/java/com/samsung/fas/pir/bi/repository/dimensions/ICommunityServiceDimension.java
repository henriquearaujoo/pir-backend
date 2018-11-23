package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityServiceDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityServiceDimension")
public interface ICommunityServiceDimension extends IBase<CommunityServiceDimension, Long, QCommunityServiceDimension> {
	@Query("SELECT dimension FROM CommunityServiceDimension dimension WHERE " +
			"dimension.healthService 			= :healthService " +
			"AND dimension.garbageDestination 	= :garbageDestination " +
			"AND dimension.waterSupply			= :waterSupply " +
			"AND dimension.hasElectricity		= :hasElectricity " +
			"AND dimension.hasKindergarten		= :hasKindergarten " +
			"AND dimension.hasElementarySchool	= :hasElementarySchool " +
			"AND dimension.hasHighSchool		= :hasHighSchool " +
			"AND dimension.hasCollege			= :hasCollege")
	CommunityServiceDimension findOne(String healthService,
									  String garbageDestination,
									  String waterSupply,
									  boolean hasElectricity,
									  boolean hasKindergarten,
									  boolean hasElementarySchool,
									  boolean hasHighSchool,
									  boolean hasCollege);
}