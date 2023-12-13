package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.bean.BankAccountBalance;
import com.ecommerce.facturation.dao.BankAccountBalanceDao;
import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.exception.BankAccountBalanceNotFoundException;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.mapper.BankAccountBalanceMapper;
import com.ecommerce.facturation.mapper.BankAccountMapper;
import com.ecommerce.facturation.service.facade.BankAccountBalanceService;
import com.ecommerce.facturation.service.facade.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {
    @Autowired
    private BankAccountBalanceMapper bankAccountBalanceMapper;
    @Autowired
    private BankAccountBalanceDao bankAccountBalanceDao;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public List<BankAccountBalanceDTO> getBankAccountBalances() {
        return bankAccountBalanceDao.findAll().stream()
                .map(bankAccountBalance -> bankAccountBalanceMapper.fromBankAccountBalance(bankAccountBalance))
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountBalanceDTO findById(Long id) throws BankAccountBalanceNotFoundException {
        BankAccountBalance bankAccountBalance = bankAccountBalanceDao.findById(id).orElseThrow(() -> new BankAccountBalanceNotFoundException("Bank Balance Not Found" + id));
        return bankAccountBalanceMapper.fromBankAccountBalance(bankAccountBalance);
    }

    @Override
    public BankAccountBalanceDTO save(BankAccountBalanceDTO bankAccountBalanceDTO) {
        BankAccountBalance bankAccountBalance = bankAccountBalanceMapper.fromBankAccountBalanceDTO(bankAccountBalanceDTO);
        BankAccountBalance newBankAccountBalance = bankAccountBalanceDao.save(bankAccountBalance);
        return bankAccountBalanceMapper.fromBankAccountBalance(newBankAccountBalance);
    }

    @Override
    public BankAccountBalanceDTO update(BankAccountBalanceDTO bankAccountBalanceDTO) throws BankAccountNotFoundException {
        BankAccountBalance bankAccountBalance = bankAccountBalanceMapper.fromBankAccountBalanceDTO(bankAccountBalanceDTO);
        BankAccountDTO foundedBankAccount = bankAccountService.findById(bankAccountBalanceDTO.bankAccountDTO().id());
        bankAccountBalance.setBankAccount(bankAccountMapper.toEntity(foundedBankAccount));
        BankAccountBalance newBankAccountBalance = bankAccountBalanceDao.save(bankAccountBalance);
        return bankAccountBalanceMapper.fromBankAccountBalance(newBankAccountBalance);
    }

    @Override
    public boolean deleteBankAccountBalanceById(Long id) throws BankAccountBalanceNotFoundException {
        BankAccountBalanceDTO bankAccountBalanceDTO = findById(id);
        if (bankAccountBalanceDTO != null) {
            bankAccountBalanceDao.deleteById(id);
        }
        return false;
    }

    @Override
    public BankAccountBalanceDTO findByBankAccount_Bank(Bank bank) {
        BankAccountBalance foundedBank = bankAccountBalanceDao.findByBankAccount_Bank(bank);
        return bankAccountBalanceMapper.fromBankAccountBalance(foundedBank);
    }
}
