package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.common.QDateDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("BIDateDimension")
public interface IDateDimension extends IBase<DateDimension, Long, QDateDimension> {
	@Query("SELECT dimension FROM DateDimension dimension WHERE dimension.datetime = :date")
	DateDimension findOne(Date date);
}