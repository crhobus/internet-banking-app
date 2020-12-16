package br.com.app.account;

import br.com.app.account.dto.AccountDto;
import br.com.app.account.dto.AccountPatchDto;
import br.com.app.account.model.AccountEntity;
import br.com.app.account.repository.AccountRepository;
import br.com.app.bank.BankService;
import br.com.app.bank.model.BankEntity;
import br.com.app.exception.BadRequestException;
import br.com.app.exception.ResourceNotFoundException;
import br.com.app.person.PersonService;
import br.com.app.person.model.PersonEntity;
import br.com.app.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PersonService personService;

    @Autowired
    private BankService bankService;

    @Autowired
    private AccountConverter converter;

    @Autowired
    private SecurityUtils securityUtils;

    @Transactional
    public AccountDto create(AccountDto dto) {
        if (repository.existsByAccountNumber(dto.getAccountNumber())) {
            throw new BadRequestException("A conta informada já está cadastrada para outra pessoa");
        }
        PersonEntity person = personService.findById(dto.getPersonId());
        if (person == null) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }
        BankEntity bank = bankService.findById(dto.getBankId());
        if (bank == null) {
            throw new ResourceNotFoundException("Banco não encontrado");
        }
        if (repository.existsByBankAndPerson(bank, person)) {
            throw new BadRequestException("Já existe uma conta cadastrada para esta pessoa nesse banco");
        }

        var account = converter.toAccountEntity(dto);
        account.setToken(securityUtils.generateHash(dto.getSecurityToken()));
        account.setBalance(new BigDecimal(0.0));
        account.setEnabled(true);
        account.setPerson(person);
        account.setBank(bank);
        repository.save(account);

        return converter.toAccountDto(account);
    }

    @Transactional
    public Boolean patch(String accountNumber, AccountPatchDto dto) {
        AccountEntity account = repository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new BadRequestException("A conta informada não existe");
        }

        account.setToken(securityUtils.generateHash(dto.getSecurityToken()));
        account.setDigit(dto.getDigit());
        repository.save(account);

        return true;
    }

}
