package br.com.app.account.model;

import br.com.app.bank.model.BankEntity;
import br.com.app.infra.database.EntityBase;
import br.com.app.person.model.PersonEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "account")
@SequenceGenerator(name = "SEQUENCE_BASE", sequenceName = "account_seq", allocationSize = 1)
public class AccountEntity extends EntityBase {

    @Column(name = "agency", length = 10, nullable = false)
    private String agency;

    @Column(name = "account_number", length = 40, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "digit", nullable = false)
    private Integer digit;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "token", nullable = false)
    private byte[] token;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank", foreignKey = @ForeignKey(name = "account_bank_fk"), referencedColumnName = "id", nullable = false)
    private BankEntity bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person", foreignKey = @ForeignKey(name = "account_person_fk"), referencedColumnName = "id", nullable = false)
    private PersonEntity person;

}
