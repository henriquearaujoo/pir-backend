package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.answer.AnswerFact;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IAnswerFact extends IBase<AnswerFact, Long, QAnswerFact> {
	@Query("SELECT fact FROM AnswerFact fact WHERE " +
			"fact.date.id 					= :dateID " +
			"AND fact.agent.id				= :agentID " +
			"AND fact.answer.id				= :answerID " +
			"AND fact.question.id			= :questionID " +
			"AND fact.data.id				= :visitDataID " +
			"AND fact.pregnancyMeasure.id	= :pregnancyMeasureID " +
			"AND fact.pregnantSocial.id		= :pregnantSocialID " +
			"AND fact.childSocial.id		= :childSocialID " +
			"AND fact.familyService.id 		= :familyServiceID " +
			"AND fact.familySocial.id 		= :familySocialID " +
			"AND fact.communityLocation.id	= :communityLocationID " +
			"AND fact.communityService.id 	= :communityServiceID " +
			"AND fact.communitySocial.id 	= :communitySocialID")
	AnswerFact findOne(Long dateID,
					      Long agentID,
						  Long answerID,
						  Long questionID,
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