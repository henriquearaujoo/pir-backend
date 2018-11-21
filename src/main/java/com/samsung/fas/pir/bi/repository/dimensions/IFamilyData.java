package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyDataDimension;
import com.samsung.fas.pir.bi.persistence.family.QFamilyDataDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("BIFamilyData")
public interface IFamilyData extends IBase<FamilyDataDimension, Long, QFamilyDataDimension> {
	@Query("SELECT data FROM FamilyDataDimension data WHERE data.gender = :gender AND data.civilState = :state " +
			"AND data.habitationType = :type AND data.socialProgram = :socialProgram " +
			"AND data.nearbyBasicUnity = :nearbyBasicUnity AND data.sanitation = :sanitation " +
			"AND data.waterTreatment = :waterTreatment AND data.membersCount = membersCount " +
			"AND data.childCount = childCount")
	FamilyDataDimension findOne(EGender gender, ECivilState state, EHabitationType habitation, boolean socialProgram,
								boolean nearbyBasicUnity, boolean sanitation, boolean waterTreatment, short membersCount,
								short childCount, Date birth);
}