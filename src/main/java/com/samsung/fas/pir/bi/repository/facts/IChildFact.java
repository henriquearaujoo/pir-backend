package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.child.ChildFact;
import com.samsung.fas.pir.bi.persistence.child.QChildFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIChildFact")
public interface IChildFact extends IBase<ChildFact, Long, QChildFact> {
	@Query("SELECT fact FROM ChildFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.agent.id				= :agentID " +
			"AND fact.social.id				= :socialID " +
			"AND fact.familyService.id 		= :familyServiceID " +
			"AND fact.familySocial.id 		= :familySocialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	ChildFact findOne(Long dateID,
					   Long agentID,
					   Long socialID,
					   Long familyServiceID,
					   Long familySocialID,
					   Long communityLocationID,
					   Long communityServiceID,
					   Long communitySocialID);
}