package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.QUser;
import com.samsung.fas.pir.persistence.models.entity.User;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import ext.java.util.QDate;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends BRepository<User, Long, QUser> {
	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.account.enabled).as("status").withDefaultBinding();
		bindings.bind(root.account.profile.uuid).as("profile").withDefaultBinding();
		bindings.bind(root.address.city.name).as("city").withDefaultBinding();
		bindings.bind(root.registerDate).as("date").all(QDate::between);
		bindings.excluding(
				root.account.password,
				root.account.profile.whoCreated,
				root.account.profile.whoUpdated,
				root.account.profile.rules
		);
	}
}