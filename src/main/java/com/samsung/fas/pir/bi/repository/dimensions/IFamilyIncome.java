package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyIncomeDimension;
import com.samsung.fas.pir.bi.persistence.family.QFamilyIncomeDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIFamilyIncome")
public interface IFamilyIncome extends IBase<FamilyIncomeDimension, Long, QFamilyIncomeDimension> {
	@Query("SELECT data FROM FamilyIncomeDimension data WHERE data.value = :value")
	FamilyIncomeDimension findOne(String value);
}