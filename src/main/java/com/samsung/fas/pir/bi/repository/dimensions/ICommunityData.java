package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.QAnswerDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityDataDimension;
import com.samsung.fas.pir.bi.persistence.community.QCommunityDataDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BICommunityData")
public interface ICommunityData extends IBase<CommunityDataDimension, Long, QCommunityDataDimension> {
	@Query("SELECT data FROM CommunityDataDimension data WHERE data.zone = :zone " +
			"AND data.hasElectricity = :electricity AND data.hasCivicCenter = :civic AND data.hasCulturalEvent = :cultural " +
			"AND data.hasLeader = :leader AND data.hasPatron = :patron AND data.hasReligiousPlace = :religious " +
			"AND data.hasKindergarten = :kindergarten AND data.hasElementarySchool = :elementary AND data.hasHighSchool = :high " +
			"AND data.hasCollege = :college")
	CommunityDataDimension findOne(ECommunityZone zone, boolean electricity, boolean civic, boolean cultural,
								   boolean leader, boolean patron, boolean religious, boolean kindergarten,
								   boolean elementary, boolean high, boolean college);
}