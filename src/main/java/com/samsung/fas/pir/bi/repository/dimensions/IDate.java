package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.base.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QDateDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("BIDate")
public interface IDate extends IBase<DateDimension, Long, QDateDimension> {
	@Query("SELECT data FROM DateDimension data WHERE data.datetime = :value")
	DateDimension findOne(Date value);
}