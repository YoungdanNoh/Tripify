package com.trip.board.service;

import java.util.List;

import com.trip.board.vo.PaginationVO;
import com.trip.board.vo.PostComment;
import com.trip.board.vo.PostVO;
import com.trip.board.vo.PostWithAuthorVO;
import com.trip.place.vo.PlaceComment;

public interface PostService {
    List<PostWithAuthorVO> getPosts(int page, int pageSize);

    PaginationVO getPagination(int page, int pageSize);
    
    void createPost(PostVO post);

    void updatePost(PostVO post);

    void deletePost(int postId);
    
    PostWithAuthorVO getPost(int postId); // 게시글 상세 조회 메서드

    // 게시글 댓글 가져오기
    List<PostComment> getCommentsByPostId(Integer postId);
    
 // 게시글 댓글 작성
    int addPostComment(PostComment postComment);

	void deleteComment(int commentId) throws Exception;
}
