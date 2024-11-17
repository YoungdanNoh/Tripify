package com.trip.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.user.mapper.UserMapper;
import com.trip.user.vo.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper mapper;
	
	@Override
	public User loginUser(Map<String, String> map) {
		User user = mapper.find(map);
		return user;
	}

	@Override
	public int registerUser(User user) {
		return mapper.add(user);
	}

	@Override
	public int updateUser(User user) {
		return mapper.modify(user);
	}

	@Override
	public User getUser(String id) {
		return mapper.findById(id);
	}
	
	
	@Override
	public User checkIdDuplication(String id) {
		return mapper.checkIdDuplicate(id);
	}

	@Override
	public int deleteUser(String email) {
		return mapper.delete(email);
	}

	@Override
	public String findPassword(String email) {	
		return mapper.findPwdById(email);
	}
	
	@Override
    public User getUserByEmail(String email) {
        return mapper.getUserByEmail(email);
    }
	
}
