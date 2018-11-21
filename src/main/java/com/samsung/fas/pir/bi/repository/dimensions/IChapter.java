package com.samsung.fas.pir.bi.repository.dimensions;

import com.samsung.fas.pir.bi.persistence.base.common.NameDimension;
import com.samsung.fas.pir.bi.persistence.base.common.QNameDimension;
import com.samsung.fas.pir.bi.persistence.visit.ChapterDimension;
import com.samsung.fas.pir.bi.persistence.visit.QChapterDimension;
import com.samsung.fas.pir.bi.repository.base.IBase;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("BIChapter")
public interface IChapter extends IBase<ChapterDimension, Long, QChapterDimension> {
	@Query("SELECT data FROM ChapterDimension data WHERE data.title = :title AND data.number = :number")
	ChapterDimension findOne(String title, Short number);
}