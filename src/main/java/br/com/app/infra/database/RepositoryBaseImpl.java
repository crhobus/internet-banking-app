package br.com.app.infra.database;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class RepositoryBaseImpl<T> {

    @PersistenceContext
    protected EntityManager ctx;

    public <U> JPAQuery<U> select(Expression<U> expr) {
        return new JPAQuery<>(ctx).select(expr);
    }

    public JPAQuery<Tuple> select(Expression<?>... exprs) {
        return new JPAQuery<>(ctx).select(exprs);
    }

    protected <U> JPAQuery<U> from(EntityPath<U> entityPath) {
        return select(entityPath).from(entityPath);
    }

    public void refresh(T entity) {
        ctx.refresh(entity);
    }

}
