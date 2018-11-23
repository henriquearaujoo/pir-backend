package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyFact;
import com.samsung.fas.pir.bi.persistence.pregnancy.QPregnancyFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIPregnancyFact")
public interface IPregnancyFact extends IBase<PregnancyFact, Long, QPregnancyFact> {
	@Query("SELECT fact FROM PregnancyFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.agent.id				= :agentID " +
			"AND fact.measure.id			= :measureID " +
			"AND fact.social.id				= :socialID " +
			"AND fact.familyService.id 		= :familyServiceID " +
			"AND fact.familySocial.id 		= :familySocialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	PregnancyFact findOne(Long dateID,
					  Long measureID,
					  Long agentID,
					  Long socialID,
					  Long familyServiceID,
					  Long familySocialID,
					  Long communityLocationID,
					  Long communityServiceID,
					  Long communitySocialID);
}
