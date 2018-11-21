package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QAgentDimension;
import com.samsung.fas.pir.bi.persistence.community.*;
import com.samsung.fas.pir.bi.persistence.family.*;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IFamilyFact extends IBase<FamilyFact, Long, QFamilyFact> {
	@Query("SELECT data FROM FamilyFact data WHERE data.date = :date AND data.data = :data AND data.name = :name " +
			"AND data.income = :income AND data.waterTreatment = :waterTreatment AND data.communityAccess = :access " +
			"AND data.communityData = :communityData AND data.culturalProduction = :cultural " +
			"AND data.hasGarbageDestination = :garbageDestination AND data.hasHealthService = :healthService " +
			"AND data.communityIncome = :communityIncome AND data.waterSupply = :waterSupply AND data.regional = :regional " +
			"AND data.unity = :unity AND data.city = :city")
	FamilyFact findOne(DateDimension date, FamilyDataDimension data, NameDimension name, FamilyIncomeDimension income,
					   FamilyWaterTreatmentDimension waterTreatment, CommunityAccessDimension access, CommunityDataDimension communityData,
					   CommunityCulturalProductionDimension cultural, CommunityGarbageDestinationDimension garbageDestination,
					   CommunityHealthServiceDimension healthService, CommunityIncomeDimension communityIncome,
					   CommunityWaterSupplyDimension waterSupply, CommunityRegionalDimension regional, CommunityUnityDimension unity,
					   CommunityCityDimension city);
}