package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dao.BankAccountDao;
import com.ecommerce.facturation.dao.UserDao;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.UserDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.mapper.BankAccountMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.mapper.UserMapper;
import com.ecommerce.facturation.service.facade.BankAccountService;
import com.ecommerce.facturation.service.facade.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountDao bankAccountDao;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

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
        UserDTO foundedUser = userService.findById(userId);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount.setUser(userMapper.toEntity(foundedUser));
        BankAccount newBankAccount = bankAccountDao.save(bankAccount);
        return bankAccountMapper.toDto(newBankAccount);
    }


    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount newBankAccount = bankAccountDao.save(bankAccount);
        return bankAccountMapper.toDto(newBankAccount);
    }

    @Override
    public boolean deleteBankAccountById(Long id) throws BankAccountNotFoundException {
        BankAccountDTO bankAccountDTO = findById(id);
        if (bankAccountDTO != null) {
            bankAccountDao.deleteById(id);
        }
        return false;
    }

    @Override
    public BankAccountDTO findByUserEmail(String email) {
        BankAccount foundedBankAccount = bankAccountDao.findByUserEmail(email);
        return bankAccountMapper.toDto(foundedBankAccount);
    }

    @Autowired
    private JsonMapper jsonMapper;

    @Override
    public BankAccountDTO setDataToBankAccount(String payload) {
        UserDTO userDTO = jsonMapper.convertJsonToObject(payload, UserDTO.class);

        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                UUID.randomUUID().toString(),
                Bank.CIHBANK,
                userDTO
        );
        save(bankAccountDTO);
        return bankAccountDTO;
    }
}
