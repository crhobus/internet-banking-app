package br.com.app.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RepositoryBase<T extends EntityBase> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {

}
