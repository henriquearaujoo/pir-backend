package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QScholarityDimension;
import com.samsung.fas.pir.bi.persistence.base.common.ScholarityDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIScholarity")
public interface IScholarity extends IBase<ScholarityDimension, Long, QScholarityDimension> {
	@Query("SELECT data FROM ScholarityDimension data WHERE data.value = :value")
	ScholarityDimension findOne(String value);
}