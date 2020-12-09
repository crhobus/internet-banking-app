package br.com.app.person.repository;

import br.com.app.infra.database.RepositoryBase;
import br.com.app.person.model.PersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends RepositoryBase<PersonEntity>, PersonRepositoryCustom {

    boolean existsByCpfOrRg(String cpf, String rg);

}
