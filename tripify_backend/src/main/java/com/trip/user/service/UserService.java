package com.trip.user.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cglib.core.TinyBitSet;

import com.trip.user.vo.User;


public interface UserService {
	User loginUser(Map<String, String> map);
	
	int registerUser(User user);
	
	User getUser(String id);

	int updateUser(User user);
	
	int deleteUser(String eamil);
	
	User checkIdDuplication(String id);
	
	String findPassword(String email);
	
	User getUserByEmail(String email);
}
