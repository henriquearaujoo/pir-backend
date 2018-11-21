package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityWaterSupplyDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityWaterSupplyDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityWaterSupply")
public interface ICommunityWaterSupply extends IBase<CommunityWaterSupplyDimension, Long, QCommunityWaterSupplyDimension> {
	@Query("SELECT data FROM CommunityWaterSupplyDimension data WHERE data.value = :value")
	CommunityWaterSupplyDimension findOne(String value);
}