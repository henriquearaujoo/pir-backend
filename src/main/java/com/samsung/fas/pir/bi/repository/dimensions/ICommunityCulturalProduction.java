package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityCulturalProductionDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityCulturalProductionDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityCulturalProduction")
public interface ICommunityCulturalProduction extends IBase<CommunityCulturalProductionDimension, Long, QCommunityCulturalProductionDimension> {
	@Query("SELECT data FROM CommunityCulturalProductionDimension data WHERE data.value = :value")
	CommunityCulturalProductionDimension findOne(String value);
}