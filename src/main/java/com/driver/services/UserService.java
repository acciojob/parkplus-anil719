package com.driver.services;


import com.driver.model.User;
import org.springframework.stereotype.Service;

@Service

public interface UserService {

	void deleteUser(Integer userId);
	User updatePassword(Integer userId, String password);
    void register(String name, String phoneNumber, String password);
}
