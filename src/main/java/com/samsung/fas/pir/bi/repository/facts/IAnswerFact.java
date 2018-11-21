package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.answer.AnswerFact;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerFact;
import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QAgentDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IAnswerFact extends IBase<AnswerFact, Long, QAnswerFact> {
	//
}