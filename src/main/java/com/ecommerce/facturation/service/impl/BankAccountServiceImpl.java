package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dao.BankAccountDao;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.DeliveryManDto;
import com.ecommerce.facturation.dto.ProviderDto;
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
    public void setDataProviderToBankAccount(String payload) {
        ProviderDto providerDto = jsonMapper.convertJsonToObject(payload, ProviderDto.class);
        UserDTO userDTO = new UserDTO(
                providerDto.fullName(),
                providerDto.email(),
                null,
                null,
                Role.PROVIDER
        );
        UserDTO savedUser = userService.save(userDTO);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                providerDto.rib(),
                Bank.CIHBANK,
                savedUser
        );
        save(bankAccountDTO);
    }

    @Override
    public BankAccountDTO setDataDeliveryManToBankAccount(String payload) {
        DeliveryManDto deliveryManDto = jsonMapper.convertJsonToObject(payload, DeliveryManDto.class);
        UserDTO userDTO = new UserDTO(
                deliveryManDto.fullName(),
                deliveryManDto.email(),
                null,
                deliveryManDto.phoneNumber(),
                Role.DELIVERY
        );
        UserDTO savedUser = userService.save(userDTO);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                deliveryManDto.rib(),
                Bank.CIHBANK,
                savedUser
        );
        save(bankAccountDTO);
        return bankAccountDTO;
    }

    @Override
    public BankAccount findByUserRole(Role role) {
        return bankAccountDao.findByUserRole(role);
    }
}
