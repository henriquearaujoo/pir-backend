package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.family.FamilyFact;
import com.samsung.fas.pir.bi.persistence.family.QFamilyFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIFamilyFact")
public interface IFamilyFact extends IBase<FamilyFact, Long, QFamilyFact> {
	@Query("SELECT fact FROM FamilyFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.service.id 			= :serviceID " +
			"AND fact.social.id 			= :socialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	FamilyFact findOne(Long dateID,
					   Long serviceID,
					   Long socialID,
					   Long communityLocationID,
					   Long communityServiceID,
					   Long communitySocialID);
}
