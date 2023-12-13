package com.ecommerce.facturation.service.facade;

import com.ecommerce.facturation.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO findById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    boolean deleteUserById(Long id);
    UserDTO findByEmail(String email);

}
