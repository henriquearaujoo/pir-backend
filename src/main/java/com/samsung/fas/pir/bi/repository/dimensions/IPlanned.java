package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.PlannedDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.QPlannedDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIPlanned")
public interface IPlanned extends IBase<PlannedDimension, Long, QPlannedDimension> {
	@Query("SELECT data FROM PlannedDimension data WHERE data.value = :value")
	PlannedDimension findOne(boolean value);
}