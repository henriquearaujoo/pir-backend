package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.visit.QVisitFact;
import com.samsung.fas.pir.bi.persistence.visit.VisitFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIVisitFact")
public interface IVisitFact extends IBase<VisitFact, Long, QVisitFact> {
	@Query("SELECT fact FROM VisitFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.agent.id				= :agentID " +
			"AND fact.data.id				= :visitDataID " +
			"AND fact.pregnancyMeasure.id	= :pregnancyMeasureID " +
			"AND fact.pregnantSocial.id		= :pregnantSocialID " +
			"AND fact.childSocial.id		= :childSocialID " +
			"AND fact.familyService.id 		= :familyServiceID " +
			"AND fact.familySocial.id 		= :familySocialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	VisitFact findOne(Long dateID,
					   Long agentID,
					   Long visitDataID,
					   Long pregnancyMeasureID,
					   Long pregnantSocialID,
					   Long childSocialID,
					   Long familyServiceID,
					   Long familySocialID,
					   Long communityLocationID,
					   Long communityServiceID,
					   Long communitySocialID);
}