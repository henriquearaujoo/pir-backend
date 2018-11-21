package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityGarbageDestinationDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityGarbageDestinationDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityGarbageDestination")
public interface ICommunityGarbageDestination extends IBase<CommunityGarbageDestinationDimension, Long, QCommunityGarbageDestinationDimension> {
	@Query("SELECT data FROM CommunityGarbageDestinationDimension data WHERE data.value = :value")
	CommunityGarbageDestinationDimension findOne(String value);
}