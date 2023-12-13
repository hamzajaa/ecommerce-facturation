package com.ecommerce.facturation.mapper;
import com.ecommerce.facturation.bean.User;
import com.ecommerce.facturation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {




    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.id());
            user.setFullName(userDTO.fullName());
            user.setEmail(userDTO.email());
            user.setAddress(userDTO.address());
            user.setPhoneNumber(userDTO.phoneNumber());
            user.setRole(userDTO.role());
            return user;
        }
    }

    @Override
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getPhoneNumber(),
                    user.getRole()
            );
            return userDTO;


        }
    }


}
