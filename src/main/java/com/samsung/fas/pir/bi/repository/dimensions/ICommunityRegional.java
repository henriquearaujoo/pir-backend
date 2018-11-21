package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityRegionalDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityRegionalDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityRegional")
public interface ICommunityRegional extends IBase<CommunityRegionalDimension, Long, QCommunityRegionalDimension> {
	@Query("SELECT data FROM CommunityRegionalDimension data WHERE data.name = :name")
	CommunityRegionalDimension findOne(String name);
}