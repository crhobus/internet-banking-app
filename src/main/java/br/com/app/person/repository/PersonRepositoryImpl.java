package br.com.app.person.repository;

import br.com.app.infra.database.RepositoryBaseImpl;
import br.com.app.person.model.PersonEntity;
import br.com.app.person.model.QPersonEntity;
import br.com.app.user.model.QUserEntity;
import com.querydsl.jpa.impl.JPAQuery;

public class PersonRepositoryImpl extends RepositoryBaseImpl<PersonEntity> implements PersonRepositoryCustom {

    @Override
    public PersonEntity findByIdAndCpfAndRgAndUsername(long id, String cpf, String rg, String username) {
        QPersonEntity person = QPersonEntity.personEntity;
        QUserEntity user = QUserEntity.userEntity;

        JPAQuery<PersonEntity> query = select(person)
                .from(person)
                .innerJoin(person.user, user)
                .where(person.id.eq(id)
                        .and(person.cpf.eq(cpf))
                        .and(person.rg.eq(rg))
                        .and(user.username.eq(username)));

        return query.fetchFirst();
    }
}
