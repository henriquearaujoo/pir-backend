package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QAgentDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BIAgent")
public interface IAgent extends IBase<AgentDimension, Long, QAgentDimension> {
	@Query("SELECT data FROM AgentDimension data WHERE data.cpf = :cpf")
	AgentDimension findOne(String cpf);
}