package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.QUser;
import com.samsung.fas.pir.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Query(value="select * from \"user\" where lower(\"login\") = lower(?1)", nativeQuery=true)
	User findByLogin(String login);

	User findByEmail(String email);
	User findByOrganizationCnpj(String cnpj);
	User findByPersonCpf(String cpf);

	List<User> findAll();
	List<User> findByPersonRg(String rg);
	List<User> findByOrganizationIe(String ie);
	List<User> findByProfileGuid(UUID id);

	/*
	 * Ignore password search
	 */
	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.password);
	}
}
