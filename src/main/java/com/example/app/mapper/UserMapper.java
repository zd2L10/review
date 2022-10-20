package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.User;

@Mapper
public interface UserMapper {

	// IDの重複確認
	User findByLoginId(String loginId) throws Exception;

	// 名前の重複確認
	User findByName(String name) throws Exception;
	
	// ユーザー登録
	void insert(User user) throws Exception;
	
	// ユーザー削除
	void delete(User user) throws Exception;
	
	
}
