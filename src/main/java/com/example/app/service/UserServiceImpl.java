package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public User getUserById(String loginId) throws Exception {
		return userMapper.findByLoginId(loginId);
	}

	@Override
	public User getUserByName(String name) throws Exception {
		return userMapper.findByName(name);
	}
	
	@Override
	public void addUser(User user) throws Exception {
		userMapper.insert(user);
		
	}
	
	@Override
	public void delete(User user) throws Exception {
		userMapper.delete(user);
	}

	@Override
	public boolean isCorrectIdAndPass(String loginId, String loginPass) throws Exception {
		User user = userMapper.findByLoginId(loginId);
		
		// ログインIDが正しいかチェック
		if(user == null) {
			return false;
		}
		
		// パスワードが正しいかチェック
		if(!BCrypt.checkpw(loginPass, user.getLoginPass())) {
			return false;
		}
		
		return true;
	}


	

}
