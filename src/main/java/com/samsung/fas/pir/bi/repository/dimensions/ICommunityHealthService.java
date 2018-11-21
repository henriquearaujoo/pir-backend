package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityHealthServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityHealthServiceDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityHealthService")
public interface ICommunityHealthService extends IBase<CommunityHealthServiceDimension, Long, QCommunityHealthServiceDimension> {
	@Query("SELECT data FROM CommunityHealthServiceDimension data WHERE data.value = :value")
	CommunityHealthServiceDimension findOne(String value);
}