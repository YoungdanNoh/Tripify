package com.trip.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.board.service.PostService;
import com.trip.board.vo.PaginationVO;
import com.trip.board.vo.PostVO;
import com.trip.board.vo.PostWithAuthorVO;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Map<String, Object> getPosts(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        List<PostWithAuthorVO> posts = postService.getPosts(page, pageSize);
        PaginationVO pagination = postService.getPagination(page, pageSize);
        
        System.out.println(posts.get(0));
        
        return Map.of(
                "posts", posts,
                "total", pagination.getTotalRecords(),  // 전체 게시물 수 반환
                "pagination", pagination
        );
    }
    
 // 게시글 작성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostVO post) {
        postService.createPost(post);
        return ResponseEntity.ok("게시글이 작성되었습니다.");
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody PostVO post) {
        post.setPostId(postId); // postId 설정
        postService.updatePost(post);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
    
 // 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostWithAuthorVO> getPost(@PathVariable int postId) {
        PostWithAuthorVO post = postService.getPost(postId);
        if (post == null) {
            return ResponseEntity.notFound().build(); // 게시글이 없으면 404 반환
        }
        return ResponseEntity.ok(post); // 게시글 반환
    }

}
