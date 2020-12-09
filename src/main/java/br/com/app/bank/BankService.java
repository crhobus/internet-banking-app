package br.com.app.bank;

import br.com.app.bank.dto.BankDto;
import br.com.app.bank.dto.PagedBankDto;
import br.com.app.bank.model.BankEntity;
import br.com.app.bank.repository.BankRepository;
import br.com.app.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    @Autowired
    private BankConverter converter;

    @Transactional
    public BankDto create(BankDto dto) {
        var bank = converter.toBankEntity(dto);
        repository.save(bank);
        return converter.toBankDto(bank);
    }

    @Transactional
    public BankDto update(BankDto dto) {
        var bank = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Banco não encontrado"));

        converter.merge(bank, dto);
        repository.save(bank);
        return converter.toBankDto(bank);
    }

    @Transactional
    public void delete(Long id) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Banco não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BankDto get(Long id) {
        var bank = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banco não encontrado"));

        return converter.toBankDto(bank);
    }

    @Transactional(readOnly = true)
    public PagedBankDto getAll(int page, int size, String direction) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<BankEntity> banks = repository.findAll(pageable);

        return converter.toPagedBankDto(banks);
    }
}
