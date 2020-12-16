package br.com.app.account;

import br.com.app.account.dto.AccountDto;
import br.com.app.account.model.AccountEntity;
import br.com.app.util.DozerConverter;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    public AccountDto toAccountDto(AccountEntity account) {
        AccountDto dto = DozerConverter.parseObject(account, AccountDto.class);
        dto.setPersonId(account.getPerson().getId());
        dto.setBankId(account.getBank().getId());
        return dto;
    }

    public AccountEntity toAccountEntity(AccountDto dto) {
        return DozerConverter.parseObject(dto, AccountEntity.class);
    }
}
