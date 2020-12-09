package br.com.app.bank.model;

import br.com.app.infra.database.EntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "bank")
@SequenceGenerator(name = "SEQUENCE_BASE", sequenceName = "bank_seq", allocationSize = 1)
public class BankEntity extends EntityBase {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

}
