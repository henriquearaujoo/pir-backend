package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.child.ChildSocialDimension;
import com.samsung.fas.pir.bi.persistence.child.QChildSocialDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.EGender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIChildSocialDimension")
public interface IChildSocialDimension extends IBase<ChildSocialDimension, Long, QChildSocialDimension> {
	@Query("SELECT dimension FROM ChildSocialDimension dimension WHERE " +
			"dimension.name 			= :name " +
			"AND dimension.gender 		= :gender " +
			"AND dimension.fatherName	= :fatherName " +
			"AND dimension.motherName	= :motherName")
	ChildSocialDimension findOne(String name, EGender gender, String fatherName, String motherName);
}
