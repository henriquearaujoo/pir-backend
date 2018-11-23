package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.pregnant.PregnantFact;
import com.samsung.fas.pir.bi.persistence.pregnant.QPregnantFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIPregnantFact")
public interface IPregnantFact extends IBase<PregnantFact, Long, QPregnantFact> {
	@Query("SELECT fact FROM PregnantFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.agent.id				= :agentID " +
			"AND fact.social.id				= :socialID " +
			"AND fact.familyService.id 		= :familyServiceID " +
			"AND fact.familySocial.id 		= :familySocialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	PregnantFact findOne(Long dateID,
					  Long agentID,
					  Long socialID,
					  Long familyServiceID,
					  Long familySocialID,
					  Long communityLocationID,
					  Long communityServiceID,
					  Long communitySocialID);
}
