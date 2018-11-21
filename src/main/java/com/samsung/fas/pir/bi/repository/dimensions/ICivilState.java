package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.CivilStateDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QCivilStateDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICivilState")
public interface ICivilState extends IBase<CivilStateDimension, Long, QCivilStateDimension> {
	@Query("SELECT data FROM CivilStateDimension data WHERE data.value = :value")
	CivilStateDimension findOne(ECivilState value);
}