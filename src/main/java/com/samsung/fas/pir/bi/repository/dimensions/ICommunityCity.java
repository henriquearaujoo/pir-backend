package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityCityDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityCityDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityCity")
public interface ICommunityCity extends IBase<CommunityCityDimension, Long, QCommunityCityDimension> {
	@Query("SELECT data FROM CommunityCityDimension data WHERE data.name = :name AND data.stateAbbreviation = :abbreviation")
	CommunityCityDimension findOne(String name, String abbreviation);
}