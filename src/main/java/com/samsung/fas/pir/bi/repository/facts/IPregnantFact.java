package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QAgentDimension;
import com.samsung.fas.pir.bi.persistence.pregnant.PregnantFact;
import com.samsung.fas.pir.bi.persistence.pregnant.QPregnantFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IPregnantFact extends IBase<PregnantFact, Long, QPregnantFact> {
	//
}