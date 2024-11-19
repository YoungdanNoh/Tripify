package com.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trip.user.service.UserService;
import com.trip.user.vo.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
public class userController {
    
    @Autowired
    UserService service;

 // 로그인 (User 정보 반환)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> map) {
        User user = service.loginUser(map);

        if (user != null) {
            System.out.println("Login user: " + user);

            // 민감한 정보 제거
            user.setPassWord(null);

            // User 객체 반환
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("로그인 실패");
    }


    // 로그아웃 (쿠키 삭제)
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response) {
        System.out.println("로그아웃");

        // 쿠키 무효화
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0); // 쿠키 즉시 삭제
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }

    // 회원 가입
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        int result = service.registerUser(user);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 회원 조회 (쿠키로 사용자 확인)
    @GetMapping
    public ResponseEntity<User> getUser(@RequestBody Map<String, String> map) {
        String email = map.get("email");

        if (email == null) {
            return ResponseEntity.status(401).build(); // 인증 실패
        }

        User user = service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // 회원 수정
    @PutMapping
    public ResponseEntity<String> updateMember(@RequestBody User user, HttpServletRequest request) {
        System.out.println(user);
        
        if(user.getPassword() == null) {
        	user.setPassWord(service.findPassword(user.getEmail()));
        }
    	
    	if (user.getEmail() == null) {
            return ResponseEntity.status(401).body("인증 실패");
        }

        int result = service.updateUser(user);
        return ResponseEntity.ok("회원수정 성공");
    }

    // 회원 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        if (email == null) {
            return ResponseEntity.status(401).body("인증 실패");
        }

        service.deleteUser(email);
        return ResponseEntity.ok("회원삭제 성공");
    }


    // ID 중복 검사
    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkIdDuplication(@RequestParam String id) {
        boolean isDuplicate = service.checkIdDuplication(id) != null;
        return ResponseEntity.ok(isDuplicate);
    }

    // 비밀번호 찾기
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody Map<String, String> map) {
        System.out.println("Finding password for email: " + map.get("email"));
        String pwd = service.findPassword(map.get("email"));
        return ResponseEntity.ok(pwd);
    }

    // 유틸리티 메서드: 쿠키에서 이메일 추출
    private String getEmailFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("user".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
