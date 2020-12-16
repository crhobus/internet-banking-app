package br.com.app.account.repository;

import br.com.app.account.model.AccountEntity;
import br.com.app.bank.model.BankEntity;
import br.com.app.infra.database.RepositoryBase;
import br.com.app.person.model.PersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends RepositoryBase<AccountEntity> {

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByBankAndPerson(BankEntity bank, PersonEntity person);

    AccountEntity findByAccountNumber(String accountNumber);

}
