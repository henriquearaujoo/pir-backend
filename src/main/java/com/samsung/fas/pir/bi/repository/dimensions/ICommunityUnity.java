package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityUnityDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityUnityDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityUnity")
public interface ICommunityUnity extends IBase<CommunityUnityDimension, Long, QCommunityUnityDimension> {
	@Query("SELECT data FROM CommunityUnityDimension data WHERE data.name = :name")
	CommunityUnityDimension findOne(String name);
}