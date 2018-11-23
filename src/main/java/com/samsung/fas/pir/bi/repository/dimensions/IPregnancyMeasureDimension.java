package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyMeasureDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.QPregnancyMeasureDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIPregnancyMeasureDimension")
public interface IPregnancyMeasureDimension extends IBase<PregnancyMeasureDimension, Long, QPregnancyMeasureDimension> {
	@Query("SELECT dimension FROM PregnancyMeasureDimension dimension WHERE " +
			"dimension.height 				= :height " +
			"AND dimension.weight 			= :weight " +
			"AND dimension.planned			= :planned")
	PregnancyMeasureDimension findOne(Short height, Short weight, Boolean planned);
}
