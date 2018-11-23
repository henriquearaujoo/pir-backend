package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswerDimension")
public interface IAnswerDimension extends IBase<AnswerDimension, Long, QAnswerDimension> {
	@Query("SELECT dimension FROM AnswerDimension dimension WHERE dimension.value = :value AND dimension.type = :type")
	AnswerDimension findOne(String value, EAnswerType type);
}
