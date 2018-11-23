package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.family.FamilyServiceDimension;
import com.samsung.fas.pir.bi.persistence.family.QFamilyServiceDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIFamilyServiceDimension")
public interface IFamilyServiceDimension extends IBase<FamilyServiceDimension, Long, QFamilyServiceDimension> {
	@Query("SELECT dimension FROM FamilyServiceDimension dimension WHERE " +
			"dimension.inSocialProgram 			= :inSocialProgram " +
			"AND dimension.hasNearbyBasicUnity 	= :hasNearbyBasicUnity " +
			"AND dimension.hasSanitation		= :hasSanitation " +
			"AND dimension.hasWaterTreatment	= :hasWaterTreatment " +
			"AND dimension.waterTreatment		= :waterTreatment")
	FamilyServiceDimension findOne(String waterTreatment,
								   boolean inSocialProgram,
								   boolean hasNearbyBasicUnity,
								   boolean hasSanitation,
								   boolean hasWaterTreatment);
}
