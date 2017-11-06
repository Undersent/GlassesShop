package com.example.Glasses.persistence.repositories;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.model.QUser;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>,
        QueryDslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser>{

    @Override
    default void customize(final QuerydslBindings bindings, final QUser root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        //bindings.excluding(...);
    }

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(int id);
    Page<User> findAll(Pageable pageable);
}
