package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityIncomeDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityIncomeDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityIncome")
public interface ICommunityIncome extends IBase<CommunityIncomeDimension, Long, QCommunityIncomeDimension> {
	@Query("SELECT data FROM CommunityIncomeDimension data WHERE data.value = :value")
	CommunityIncomeDimension findOne(String value);
}