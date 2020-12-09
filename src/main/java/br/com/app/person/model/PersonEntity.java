package br.com.app.person.model;

import br.com.app.infra.database.EntityBase;
import br.com.app.user.model.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "person")
@SequenceGenerator(name = "SEQUENCE_BASE", sequenceName = "person_seq", allocationSize = 1)
public class PersonEntity extends EntityBase {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 6, nullable = false)
    private Gender gender;

    @Column(name = "cpf", length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(name = "rg", length = 7, unique = true, nullable = false)
    private String rg;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "neighborhood", length = 180, nullable = false)
    private String neighborhood;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "city", length = 200, nullable = false)
    private String city;

    @Column(name = "state", length = 150, nullable = false)
    private String state;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account", foreignKey = @ForeignKey(name = "person_user_account_fk"), referencedColumnName = "id", nullable = false, unique = true)
    private UserEntity user;

}
