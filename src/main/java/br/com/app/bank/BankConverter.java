package br.com.app.bank;

import br.com.app.bank.dto.BankDto;
import br.com.app.bank.dto.PagedBankDto;
import br.com.app.bank.model.BankEntity;
import br.com.app.util.DozerConverter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BankConverter {

    public BankDto toBankDto(BankEntity bank) {
        return DozerConverter.parseObject(bank, BankDto.class);
    }

    public BankEntity toBankEntity(BankDto dto) {
        return DozerConverter.parseObject(dto, BankEntity.class);
    }

    public void merge(BankEntity entity, BankDto dto) {
        DozerConverter.parseObject(dto, entity);
    }

    public PagedBankDto toPagedBankDto(Page<BankEntity> banks) {
        PagedBankDto dto = new PagedBankDto();

        dto.setFound(banks.getTotalElements());
        dto.setMoreItemsAvailable(banks.hasNext());
        dto.setNextPage(banks.hasNext() ? banks.getNumber() + 1L : 0L);
        dto.setBanks(banks.getContent().stream().map(this::toBankDto).collect(Collectors.toList()));

        return dto;
    }
}
