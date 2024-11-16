package com.trip.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.user.vo.User;

@Mapper
public interface UserMapper {
	// 로그인
	User find(Map<String,String> map);
	
	// 회원가입
	int add(User user);
	
	// 회원수정 
	int modify(User user);
	
	// 회원조회 
	User findById(String userId);
	
	// 회원삭제 
	int delete(String userId);
		
	// 아이디 중복
	User checkIdDuplicate(String userId);
	
	String findPwdById(String email);
	
	User getUserByEmail(String email);
}
