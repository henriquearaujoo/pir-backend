package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.QChapter;
import com.samsung.fas.pir.persistence.repository.IChapterRepository;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChapterDAO extends BaseDAO<Chapter, Long, QChapter> {
	private 	EntityManager			emanager;

	@Autowired
	public ChapterDAO( EntityManager emanager) {
		this.emanager	= emanager;
	}

	public Set<Chapter> findAllValid() {
		return ((IChapterRepository) repository).findAllByValid();
	}

	public Collection<Chapter> findAllByChapter(int chapter) {
		return ((IChapterRepository) repository).findAllByChapter(chapter);
	}

	public List<Chapter> findAllValid(Predicate predicate) {
		JPAQuery<Chapter>	queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>	queryChapter 	= new JPAQuery<>(emanager);
		QChapter 			chapter			= QChapter.chapter1;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		return queryChapter.from(chapter).where(chapter.chapter.in(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate)).fetch();
	}

	public Page<Chapter> findAllValid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>		queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>		queryChapter 	= new JPAQuery<>(emanager);
		PathBuilder<Chapter>	entityPath 		= new PathBuilder<>(Chapter.class, "chapter");
		QChapter				chapter			= QChapter.chapter1;
		JPAQuery<Chapter>		result;
		Query					query;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		result	= queryChapter.from(chapter).where(chapter.chapter.in(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate));
		query 	= Tools.setupPage(result, pageable, entityPath);
		//noinspection unchecked
		return getChaptersList(pageable, query);
	}

	public Collection<Chapter> findAllInvalid() {
		return ((IChapterRepository) repository).findAllByChapterNotIn(findAllValid().stream().map(Chapter::getChapter).collect(Collectors.toSet()));
	}

	public List<Chapter> findAllInvalid(Predicate predicate) {
		JPAQuery<Chapter>	queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>	queryChapter 	= new JPAQuery<>(emanager);
		QChapter			chapter			= QChapter.chapter1;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		return queryChapter.from(chapter).where(chapter.chapter.notIn(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate)).fetch();
	}

	public Page<Chapter> findAllInvalid(Predicate predicate, Pageable pageable) {
		JPAQuery<Chapter>		queryActive 	= new JPAQuery<>(emanager);
		JPAQuery<Chapter>		queryChapter 	= new JPAQuery<>(emanager);
		PathBuilder<Chapter>	entityPath 		= new PathBuilder<>(Chapter.class, "chapter");
		QChapter				chapter			= QChapter.chapter1;
		JPAQuery<Chapter>		result;
		Query					query;
		// SELECT * FROM chapter WHERE chapter.number IN (SELECT chapter.number FROM chapter WHERE chapter.in_use = true)
		result 	= queryChapter.from(chapter).where(chapter.chapter.notIn(queryActive.from(chapter).select(chapter.chapter).where(chapter.valid.eq(true)).fetch()).and(predicate));
		query 	= Tools.setupPage(result, pageable, entityPath);
		return getChaptersList(pageable, query);
	}

	public Page<Chapter> findAllValid(Pageable pageable) {
		return ((IChapterRepository) repository).findAllByValid(pageable);
	}

	public Page<Chapter> findAllInvalid(Pageable pageable) {
		return ((IChapterRepository) repository).findAllByChapterNotIn(findAllValid().stream().map(Chapter::getChapter).collect(Collectors.toSet()), pageable);
	}

	public void invalidateAllChapters(long chapter) {
		((IChapterRepository) repository).invalidateAll(chapter);
	}

	public void invalidateOne(long id) {
		((IChapterRepository) repository).invalidateOne(id);
	}

	@SuppressWarnings("unchecked")
	private Page<Chapter> getChaptersList(Pageable pageable, Query query) {
		try {
			if (pageable.getPageSize() > query.getResultList().size())
				return new PageImpl<Chapter>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + query.getResultList().size()), pageable, query.getResultList().size());
			return new PageImpl<Chapter>(query.getResultList().subList(pageable.getOffset(), pageable.getOffset() + pageable.getPageSize()), pageable, query.getResultList().size());
		} catch (Exception e) {
			return new PageImpl<>(new ArrayList<>(), pageable, query.getResultList().size());
		}
	}
}
