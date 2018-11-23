package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.common.QAgentDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAgentDimension")
public interface IAgentDimension extends IBase<AgentDimension, Long, QAgentDimension> {
	@Query("SELECT dimension FROM AgentDimension dimension WHERE dimension.cpf = :cpf")
	AgentDimension findOne(String cpf);
}
