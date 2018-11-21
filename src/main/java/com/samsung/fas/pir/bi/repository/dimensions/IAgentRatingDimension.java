package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.visit.AgentRatingDimension;
import com.samsung.fas.pir.bi.persistence.visit.QAgentRatingDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAgentRating")
public interface IAgentRatingDimension extends IBase<AgentRatingDimension, Long, QAgentRatingDimension> {
	@Query("SELECT data FROM AgentRatingDimension data WHERE data.value = :value")
	AgentRatingDimension findOne(Short value);
}