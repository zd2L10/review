package com.example.app.service;

import com.example.app.domain.User;

public interface UserService {

	User getUserById(String loginId) throws Exception;
	
	User getUserByName(String name) throws Exception;
	
	void addUser(User user) throws Exception;
	
	void delete(User user) throws Exception;
	
	boolean isCorrectIdAndPass(String loginId, String loginPass) throws Exception;
}
