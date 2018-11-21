package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.QWeightDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.WeightDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIWeight")
public interface IWeight extends IBase<WeightDimension, Long, QWeightDimension> {
	@Query("SELECT data FROM WeightDimension data WHERE data.value = :value")
	WeightDimension findOne(short value);
}