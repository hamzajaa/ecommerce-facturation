package com.ecommerce.facturation.dao;

import com.ecommerce.facturation.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
