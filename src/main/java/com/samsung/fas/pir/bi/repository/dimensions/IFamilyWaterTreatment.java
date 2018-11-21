package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyWaterTreatmentDimension;
import com.samsung.fas.pir.bi.persistence.family.QFamilyWaterTreatmentDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIFamilyWaterTreatment")
public interface IFamilyWaterTreatment extends IBase<FamilyWaterTreatmentDimension, Long, QFamilyWaterTreatmentDimension> {
	@Query("SELECT data FROM FamilyWaterTreatmentDimension data WHERE data.value = :value")
	FamilyWaterTreatmentDimension findOne(short value);
}