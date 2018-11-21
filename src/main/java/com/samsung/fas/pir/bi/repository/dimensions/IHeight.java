package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.HeightDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.QHeightDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIHeight")
public interface IHeight extends IBase<HeightDimension, Long, QHeightDimension> {
	@Query("SELECT data FROM HeightDimension data WHERE data.value = :value")
	HeightDimension findOne(String value);
}