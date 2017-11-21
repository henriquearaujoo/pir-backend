package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.QUser;
import com.samsung.fas.pir.models.entity.User;
import ext.java.util.QDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUsersRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID>, QueryDslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
	User findByLoginIgnoreCase(String login);
	User findOneByGuid(UUID guid);
	User findByEmail(String email);
	User findByOrganizationCnpj(String cnpj);
	User findByPersonCpf(String cpf);

	List<User> findAll();
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileGuid(UUID id);

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.guid).as("id").withDefaultBinding();
		bindings.bind(root.active).as("status").withDefaultBinding();
		bindings.bind(root.organization).as("pjur").withDefaultBinding();
		bindings.bind(root.person).as("pfis").withDefaultBinding();
		bindings.bind(root.profile.guid).as("profile").withDefaultBinding();
		bindings.bind(root.address.city.name).as("city").withDefaultBinding();
		bindings.bind(root.registerDate).as("date").all(QDate::between);
		bindings.excluding	(
								root.password,
								root.profile.whoCreated,
								root.profile.whoUpdated,
								root.profile.rules
							);
	}
}
