package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.QQuestionDimension;
import com.samsung.fas.pir.bi.persistence.answer.QuestionDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIQuestionDimension")
public interface IQuestionDimension extends IBase<QuestionDimension, Long, QQuestionDimension> {
	@Query("SELECT dimension FROM QuestionDimension dimension WHERE dimension.question = :question AND dimension.type = :type")
	QuestionDimension findOne(String question, EAnswerType type);
}