package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.QChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IChapterRepository extends JpaRepository<Chapter, Long>, PagingAndSortingRepository<Chapter, Long>, QueryDslPredicateExecutor<Chapter>, QuerydslBinderCustomizer<QChapter> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update chapter set in_use = false", nativeQuery = true)
	void invalidateAll();

	Chapter findOneByChapterAndVersion(int chapter, int version);
	List<Chapter> findAllByChapter(int chapter);

	@Override
	default void customize(QuerydslBindings bindings, QChapter root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.chapter).as("number").withDefaultBinding();
//		bindings.bind(root.title).as("title").withDefaultBinding();
		bindings.bind(root.estimatedTime).as("estimated_time").withDefaultBinding();
		bindings.bind(root.timeUntilNext).as("time_next_visit").withDefaultBinding();
		bindings.bind(root.valid).as("status").withDefaultBinding();
//		bindings.bind(root.version).as("version").withDefaultBinding();
		bindings.excluding(
				root.id,
				root.description,
				root.content,
				root.purpose,
				root.familyTasks,
				root.conclusion,
				root.intervention,
				root.greetings,
				root.subtitle
		);
	}
}
