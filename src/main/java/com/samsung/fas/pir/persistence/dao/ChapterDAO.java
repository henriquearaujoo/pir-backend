package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.QChapter;
import com.samsung.fas.pir.persistence.repositories.IChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Service
public class ChapterDAO extends BaseDAO<Chapter, Long, QChapter> {
	private	final EntityManager emanager;

	@Autowired
	public ChapterDAO(IChapterRepository repository, EntityManager emanager) {
		super(repository);
		this.emanager = emanager;
	}

	public Collection<Chapter> findAllByChapter(int chapter) {
		return ((IChapterRepository) repository).findAllByChapter(chapter);
	}

	public Collection<Chapter> findAllValid() {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).orderBy(chapter.chapter.asc()).orderBy(chapter.version.asc()).fetch();
	}

	public List<Chapter> findAllValid(Predicate predicate) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()).and(predicate)).fetch();
	}

	public Page<?> findAllValid(Pageable pageable) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		final	JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()));
		final	Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, "chapter"));
		return SBPage.getPageList(pageable, page);
	}

	public Page<?> findAllValid(Predicate predicate, Pageable pageable) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		final	JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).innerJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue()).and(predicate));
		final	Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, "chapter"));
		return SBPage.getPageList(pageable, page);
	}

	public Collection<Chapter> findAllInvalid() {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(chapter.id.isNull()).fetch();
	}

	public List<Chapter> findAllInvalid(Predicate predicate) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		return query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(chapter.id.isNull().and(predicate)).fetch();
	}

	public Page<?> findAllInvalid(Pageable pageable) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		final	JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(chapter.id.isNull());
		final	Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, "chapter"));
		return SBPage.getPageList(pageable, page);
	}

	public Page<?> findAllInvalid(Predicate predicate, Pageable pageable) {
		final 	JPAQuery<Chapter>	query 		= new JPAQuery<>(emanager);
		final	QChapter 			chapter		= new QChapter("chapters");
		final	QChapter 			achapter	= new QChapter("active_chapters");
		final	JPAQuery<Chapter>	result		= query.select(chapter).from(chapter).leftJoin(achapter).on(achapter.chapter.eq(chapter.chapter).and(achapter.valid.isTrue())).where(chapter.id.isNull().and(predicate));
		final	Query				page		= SBPage.setupPage(result, pageable, new PathBuilder<>(Chapter.class, "chapter"));
		return SBPage.getPageList(pageable, page);
	}

	public void invalidateAllChapters(long chapter) {
		((IChapterRepository) repository).invalidateAll(chapter);
	}

	public void invalidateOne(long id) {
		((IChapterRepository) repository).invalidateOne(id);
	}
}
