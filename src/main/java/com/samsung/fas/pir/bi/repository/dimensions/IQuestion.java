package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QQuestionDimension;
import com.samsung.fas.pir.bi.persistence.answer.QuestionDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIQuestion")
public interface IQuestion extends IBase<QuestionDimension, Long, QQuestionDimension> {
	@Query("SELECT data FROM QuestionDimension data WHERE data.question = :question AND data.type = :type")
	QuestionDimension findOne(String question, EAnswerType type);
}