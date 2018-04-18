package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.QChapter;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface IChapter extends IBaseRepository<Chapter, Long, QChapter> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update chapter set in_use = false where pirdb.public.chapter.number = ?1", nativeQuery = true)
	void invalidateAll(long number);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update chapter set in_use = false where pirdb.public.chapter.id = ?1", nativeQuery = true)
	void invalidateOne(long id);

	Collection<Chapter> findAllByChapter(int chapter);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QChapter root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.chapter).as("number").withDefaultBinding();
		bindings.bind(root.estimatedTime).as("estimated_time").withDefaultBinding();
		bindings.bind(root.timeUntilNext).as("time_next_visit").withDefaultBinding();
		bindings.bind(root.valid).as("status").withDefaultBinding();
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
