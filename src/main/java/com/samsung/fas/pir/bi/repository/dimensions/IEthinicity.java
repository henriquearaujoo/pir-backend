package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.EthinicityDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QEthinicityDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIEthnicity")
public interface IEthinicity extends IBase<EthinicityDimension, Long, QEthinicityDimension> {
	@Query("SELECT data FROM EthinicityDimension data WHERE data.value = :value")
	EthinicityDimension findOne(String value);
}