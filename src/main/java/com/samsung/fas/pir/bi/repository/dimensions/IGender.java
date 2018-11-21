package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.GenderDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QGenderDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIGender")
public interface IGender extends IBase<GenderDimension, Long, QGenderDimension> {
	@Query("SELECT data FROM GenderDimension data WHERE data.value = :value")
	GenderDimension findOne(EGender value);
}