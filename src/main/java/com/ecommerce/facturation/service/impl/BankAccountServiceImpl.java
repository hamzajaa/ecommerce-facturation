package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dao.BankAccountDao;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.mapper.BankAccountMapper;
import com.ecommerce.facturation.service.facade.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountDao bankAccountDao;
    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountDao.findAll().stream()
                .map(bankAccount -> bankAccountMapper.toDto(bankAccount)).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO findById(Long id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountDao.findById(id).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with id: " + id));
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount=bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount newBankAccount=bankAccountDao.save(bankAccount);
        return bankAccountMapper.toDto(newBankAccount);
    }

    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount=bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount newBankAccount=bankAccountDao.save(bankAccount);
        return bankAccountMapper.toDto(newBankAccount);
    }

    @Override
    public boolean deleteBankAccountById(Long id) throws BankAccountNotFoundException {
        BankAccountDTO bankAccountDTO = findById(id);
        if (bankAccountDTO !=null){
            bankAccountDao.deleteById(id);
        }
        return false;
    }
}
