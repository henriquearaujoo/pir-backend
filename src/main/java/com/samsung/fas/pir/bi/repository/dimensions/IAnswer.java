package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIAnswer")
public interface IAnswer extends IBase<AnswerDimension, Long, QAnswerDimension> {
	@Query("SELECT data FROM AnswerDimension data WHERE data.value = :value AND data.type = :type")
	AnswerDimension findOne(String value, EAnswerType type);
}