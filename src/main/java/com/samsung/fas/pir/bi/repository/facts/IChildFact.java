package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.base.common.*;
import com.samsung.fas.pir.bi.persistence.child.ChildFact;
import com.samsung.fas.pir.bi.persistence.child.QChildFact;
import com.samsung.fas.pir.bi.persistence.community.*;
import com.samsung.fas.pir.bi.persistence.family.FamilyDataDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyFact;
import com.samsung.fas.pir.bi.persistence.family.FamilyIncomeDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyWaterTreatmentDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IChildFact extends IBase<ChildFact, Long, QChildFact> {
	@Query("SELECT data FROM ChildFact data WHERE data.date = :date AND data.agent = :agent AND data.name = :name " +
			"AND data.gender = :gender AND data.motherName = :mother AND data.fatherName = :father " +
			"AND data.data = :familyData AND data.familyName = :familyName AND data.income = :familyIncome " +
			"AND data.waterTreatment = :waterTreatment AND data.communityAccess = :access " +
			"AND data.communityData = :communityData AND data.culturalProduction = :culturalProduction " +
			"AND data.hasGarbageDestination = :garbageDestination AND data.hasHealthService = :healthService " +
			"AND data.communityIncome = :communityIncome AND data.waterSupply = :waterSupply AND data.regional = :regional " +
			"AND data.unity = :unity AND data.city = :city")
	ChildFact findOne(DateDimension date, AgentDimension agent, NameDimension name, GenderDimension gender, NameDimension mother,
					  NameDimension father, FamilyDataDimension familyData, NameDimension familyName, FamilyIncomeDimension familyIncome,
					  FamilyWaterTreatmentDimension waterTreatment, CommunityAccessDimension access, CommunityDataDimension communityData,
					  CommunityCulturalProductionDimension culturalProduction, CommunityGarbageDestinationDimension garbageDestination,
					  CommunityHealthServiceDimension healthService, CommunityIncomeDimension communityIncome, CommunityWaterSupplyDimension waterSupply,
					  CommunityRegionalDimension regional, CommunityUnityDimension unity, CommunityCityDimension city);
}