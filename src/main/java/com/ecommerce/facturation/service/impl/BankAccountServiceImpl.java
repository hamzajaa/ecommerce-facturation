package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dao.BankAccountDao;
import com.ecommerce.facturation.dao.UserDao;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.UserDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.mapper.BankAccountMapper;
import com.ecommerce.facturation.service.facade.BankAccountService;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private UserDao userDao;

    @Override
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountDao.findAll().stream()
                .map(bankAccount -> bankAccountMapper.toDto(bankAccount)).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO findById(Long id) throws EntityNotFoundException {
        BankAccount bankAccount = bankAccountDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank account not found with id: " + id));
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) throws EntityNotFoundException {
        Long userId = bankAccountDTO.userDto().id();
        User userDTO = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount.setUser(userDTO);
        BankAccount newBankAccount = bankAccountDao.save(bankAccount);
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
