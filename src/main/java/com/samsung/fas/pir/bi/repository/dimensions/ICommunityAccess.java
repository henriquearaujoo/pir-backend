package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityAccessDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityAccessDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityAccess")
public interface ICommunityAccess extends IBase<CommunityAccessDimension, Long, QCommunityAccessDimension> {
	@Query("SELECT data FROM CommunityAccessDimension data WHERE data.value = :value")
	CommunityAccessDimension findOne(String value);
}