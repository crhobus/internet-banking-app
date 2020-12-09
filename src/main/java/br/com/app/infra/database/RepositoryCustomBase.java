package br.com.app.infra.database;

public interface RepositoryCustomBase<T> {

    void refresh(T entity);
}
