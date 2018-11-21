package com.samsung.fas.pir.bi.repository.facts;

import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QAgentDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyFact;
import com.samsung.fas.pir.bi.persistence.pregnancy.QPregnancyFact;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerFact")
public interface IPregnancyFact extends IBase<PregnancyFact, Long, QPregnancyFact> {
	//
}