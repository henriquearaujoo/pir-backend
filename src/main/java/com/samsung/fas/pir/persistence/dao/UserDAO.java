package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.dao.utils.SBPage;
import com.samsung.fas.pir.persistence.models.QUser;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.persistence.repositories.IUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.function.Function;

@Service
public class UserDAO extends BaseDAO<User, Long, IUser, QUser> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Autowired
	public UserDAO(IUser repository, EntityManager manager) {
		super(repository);
		setManager(manager);
	}

	public Collection<User> findAllAgents() {
		JPAQuery<User>		query 		= new JPAQuery<>(manager);
		QUser 				users		= new QUser("users");
		return query.select(users).from(users).where(users.person.agent.isNotNull()).fetch();
	}

	public Collection<User> findAllAgents(Predicate predicate) {
		JPAQuery<User>		query 		= new JPAQuery<>(manager);
		QUser 				users		= new QUser("users");
		return query.select(users).from(users).where(users.person.agent.isNotNull().and(predicate)).fetch();
	}

	public Page<?> findAllAgents(Pageable pageable) {
		JPAQuery<User>				query 		= new JPAQuery<>(manager);
		QUser 						users		= new QUser("users");
		JPAQuery<User>				result		= query.select(users).from(users).where(users.person.agent.isNotNull());
		Query						page		= SBPage.setupPage(result, pageable, new PathBuilder<>(User.class, users.getMetadata().getName()));
		Function<User, String>		getUser		= User::getName;
		return SBPage.getPageList(pageable, page, getUser);
	}

	public Page<?> findAllAgents(Predicate predicate, Pageable pageable) {
		JPAQuery<User>				query 		= new JPAQuery<>(manager);
		QUser 						users		= new QUser("users");
		JPAQuery<User>				result		= query.select(users).from(users).where(users.person.agent.isNotNull().and(predicate));
		Query						page		= SBPage.setupPage(result, pageable, new PathBuilder<>(User.class, users.getMetadata().getName()));
		Function<User, String>		getUser		= User::getName;
		return SBPage.getPageList(pageable, page, getUser);
	}
}
