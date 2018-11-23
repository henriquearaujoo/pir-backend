package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.family.FamilySocialDimension;
import com.samsung.fas.pir.bi.persistence.family.QFamilySocialDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("BIFamilySocialDimension")
public interface IFamilySocialDimension extends IBase<FamilySocialDimension, Long, QFamilySocialDimension> {
	@Query("SELECT dimension FROM FamilySocialDimension dimension WHERE " +
			"dimension.name 				= :name " +
			"AND dimension.birth 			= :birth " +
			"AND dimension.gender 			= :gender " +
			"AND dimension.civilState 		= :civilState " +
			"AND dimension.habitationType 	= :habitationType " +
			"AND dimension.membersCount		= :membersCount " +
			"AND dimension.childCount		= :childCount " +
			"AND dimension.income			= :income")
	FamilySocialDimension findOne(String name,
								  Date birth,
								  EGender gender,
								  ECivilState civilState,
								  EHabitationType habitationType,
								  Short membersCount,
								  Short childCount,
								  String income);
}
