package br.com.app.person.repository;

import br.com.app.infra.database.RepositoryCustomBase;
import br.com.app.person.model.PersonEntity;

public interface PersonRepositoryCustom extends RepositoryCustomBase<PersonEntity> {

    PersonEntity findByIdAndCpfAndRgAndUsername(long id, String cpf, String rg, String username);
}
