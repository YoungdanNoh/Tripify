package com.trip.user.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.user.service.UserService;
import com.trip.user.vo.User;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@RestController
@RequestMapping("/member")
public class userController {
	@Autowired
	UserService service;
	
	// 로그인(Message Body 사용하기)
	@PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody Map<String, String>map, HttpSession session) {
		User user = service.loginUser(map);
		
        if(user != null) {
        	System.out.println(user);
        	session.setAttribute("user", user);
        	User sessionUser = (User)session.getAttribute("user");
        }
        
        if(user == null) {
        	return ResponseEntity.ok(false);
        }else {
        	return ResponseEntity.ok(true);
        }
        
    }
	
	
	// 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
    	System.out.println("로그아웃");
   
    	session.removeAttribute("user");
    	
    	User user = (User)session.getAttribute("user");
    	if(user == null) {
    		System.out.println("세션에서 로그인 정보 삭제");
    	}
    	
        return ResponseEntity.ok("로그아웃 성공");
    }
	
    
    // 회원 가입, unique key 예외 처리하기
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
    	int result = service.registerUser(user);
    	
        return ResponseEntity.ok("회원가입 성공");
    }
    
    // 회원조회
    @GetMapping //@GetMapping("/") ->  /member/로 감.
    public ResponseEntity<User> getUser(HttpSession session) {
    	User sessionUser = (User)session.getAttribute("user");
    	String userId = sessionUser.getUserId()+"";
    	
    	User user =service.getUser(userId);
    		
    	return ResponseEntity.ok(user);
    }
    
    // 회원수정
    @PutMapping
    public ResponseEntity<String> updateMember(@RequestBody User user, HttpSession session) {     
    	print("updateMember");
    	
    	User sessionUser = (User)session.getAttribute("user");    	
    	user.setUserId(sessionUser.getUserId());
    	
    	int result = service.updateUser(user);
    	
    	return ResponseEntity.ok("회원수정 성공");
    }
    
    // 회원삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody Map<String,String> map) {     
        String id = map.get("id");
    	System.out.println(id);
    	service.deleteUser(id);
    	return ResponseEntity.ok("회원삭제 성공");
    }
    
    
    // ID 중복 검사
    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkIdDuplication(@RequestParam String id) {
    	System.out.println(id);
    	boolean isDuplicate = true;
    	if(service.checkIdDuplication(id) == null) {
        	isDuplicate = false;
        }
    	
        return ResponseEntity.ok(isDuplicate);
        
    }
    
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody Map<String,String> map){
    	print("find-password");
    	System.out.println(map.get("email"));
    	String pwd = service.findPassword(map.get("email"));
    	return ResponseEntity.ok(pwd);
    }
    
    public void print(String str) {
    	System.out.println(str+" from UserController");
    }
}
