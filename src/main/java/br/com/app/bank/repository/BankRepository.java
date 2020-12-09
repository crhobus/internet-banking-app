package br.com.app.bank.repository;

import br.com.app.bank.model.BankEntity;
import br.com.app.infra.database.RepositoryBase;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends RepositoryBase<BankEntity> {

}
