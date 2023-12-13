package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dao.UserDao;
import com.ecommerce.facturation.dto.UserDTO;
import com.ecommerce.facturation.mapper.UserMapper;
import com.ecommerce.facturation.service.facade.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> getUsers() {
        return userDao.findAll().stream().map(
                user -> userMapper.toDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userDao.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        findById(userDTO.id());
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userDao.save(user);
        return userMapper.toDto(updatedUser);


    }

    @Override
    public boolean deleteUserById(Long id) {
        UserDTO userDTO = findById(id);
        if (userDTO != null) {
            userDao.deleteById(id);
            return true;
        } else return false;
    }

    @Override
    public UserDTO findByEmail(String email) {
        User foundedUser = userDao.findByEmail(email);
        return userMapper.toDto(foundedUser);
    }
}
