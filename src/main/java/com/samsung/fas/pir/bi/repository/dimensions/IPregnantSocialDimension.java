package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.pregnant.PregnantSocialDimension;
import com.samsung.fas.pir.bi.persistence.pregnant.QPregnantSocialDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIPregnantSocialDimension")
public interface IPregnantSocialDimension extends IBase<PregnantSocialDimension, Long, QPregnantSocialDimension> {
	@Query("SELECT dimension FROM PregnantSocialDimension dimension WHERE " +
			"dimension.name 				= :name " +
			"AND dimension.civilState 		= :civilState " +
			"AND dimension.ethinicity 		= :ethinicity " +
			"AND dimension.scholarity		= :scholarity")
	PregnantSocialDimension findOne(String name, ECivilState civilState, String ethinicity, String scholarity);
}