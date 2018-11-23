package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.visit.QVisitDataDimension;
import com.samsung.fas.pir.bi.persistence.visit.VisitDataDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIVisitDataDimension")
public interface IVisitDataDimension extends IBase<VisitDataDimension, Long, QVisitDataDimension> {
	@Query("SELECT dimension FROM VisitDataDimension dimension WHERE " +
			"dimension.title 			= :title " +
			"AND dimension.number 		= :number " +
			"AND dimension.agentRating 	= :agentRating")
	VisitDataDimension findOne(String title, Short number, Short agentRating);
}
