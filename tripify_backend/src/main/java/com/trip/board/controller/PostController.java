package com.trip.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import com.trip.board.vo.PostComment;
import com.trip.board.vo.PostVO;
import com.trip.board.vo.PostWithAuthorVO;
import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentRequest;

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
    
 // 게시글 댓글 가져오기
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<PostComment>> getCommentsByPostId(@PathVariable int postId) {
        List<PostComment> comments = postService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping("{postId}/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Integer postId,
            @RequestBody PlaceCommentRequest commentRequest) {

        // `commentRequest`로 userId와 content를 받음
        PostComment postComment = new PostComment();
        postComment.setPostId(postId);
        postComment.setUserId(commentRequest.getUserId());
        postComment.setContent(commentRequest.getContent());

        // Service를 통해 댓글 저장
        int isAdded = postService.addPostComment(postComment);

        if (isAdded>0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 추가에 실패했습니다.");
        }
    }
    
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        try {
            postService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("댓글 삭제 중 오류가 발생했습니다.");
        }
    }
    
    @GetMapping("/myposts/{userId}")
    public ResponseEntity<List<PostVO>> getPostsByUserId(@PathVariable int userId) {
        List<PostVO> posts = postService.findPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}
