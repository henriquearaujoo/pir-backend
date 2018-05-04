package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.QChapter;
import com.samsung.fas.pir.persistence.repositories.IChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
public class ChapterDAO extends BaseDAO<Chapter, Long, IChapter, QChapter> {
	private	final EntityManager emanager;

	@Autowired
	public ChapterDAO(IChapter repository, EntityManager emanager) {
		super(repository);
		this.emanager = emanager;
	}

	public Collection<Chapter> findAllByChapter(int chapter) {
		return getRepository().findAllByChapter(chapter);
	}

	public Collection<Chapter> findAllValid() {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).orderBy(chapter.chapter.asc()).orderBy(chapter.version.asc()).fetch();
	}

	public List<Chapter> findAllValid(Predicate predicate) {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()).and(predicate)).fetch();
	}

	public Page<?> findAllValid(Pageable pageable) {
		JPAQuery<Chapter>			query 		= new JPAQuery<>(emanager);
		QChapter 					chapter		= QChapter.chapter1;
		QChapter 					achapter	= new QChapter("active_chapters");
		JPAQuery<Chapter>			result		= query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()));
		Query						page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, chapter.getMetadata().getName()));
		Function<Chapter, Integer> 	getChapter	= Chapter::getChapter;
		return SBPage.getPageList(pageable, page, getChapter);
	}

	public Page<?> findAllValid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()).and(predicate));
		Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, chapter.getMetadata().getName()));
		Function<Chapter, Integer> 	getChapter	= Chapter::getChapter;
		return SBPage.getPageList(pageable, page, getChapter);
	}

	public Collection<Chapter> findAllInvalid() {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapters	= new QChapter("chapters");
		QChapter 			actives		= new QChapter("active_chapters");
		return query.select(chapters).from(chapters).leftJoin(actives).on(actives.chapter.eq(chapters.chapter).and(actives.valid.isTrue())).where(actives.id.isNull()).fetch();
	}

	public List<Chapter> findAllInvalid(Predicate predicate) {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(achapter.id.isNull().and(predicate)).fetch();
	}

	public Page<?> findAllInvalid(Pageable pageable) {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(achapter.id.isNull());
		Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, chapter.getMetadata().getName()));
		Function<Chapter, Integer> 	getChapter	= Chapter::getChapter;
		return SBPage.getPageList(pageable, page, getChapter);
	}

	public Page<?> findAllInvalid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		QChapter 			chapter		= QChapter.chapter1;
		QChapter 			achapter	= new QChapter("active_chapters");
		JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(achapter.id.isNull().and(predicate));
		Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, chapter.getMetadata().getName()));
		Function<Chapter, Integer> 	getChapter	= Chapter::getChapter;
		return SBPage.getPageList(pageable, page, getChapter);
	}

	public void invalidateAllChapters(long chapter) {
		getRepository().invalidateAll(chapter);
	}

	public void invalidateOne(long id) {
		getRepository().invalidateOne(id);
	}
}
