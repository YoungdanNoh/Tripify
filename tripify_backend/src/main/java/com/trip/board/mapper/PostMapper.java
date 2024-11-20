package com.trip.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.trip.board.vo.PostComment;
import com.trip.board.vo.PostVO;
import com.trip.board.vo.PostWithAuthorVO;

@Mapper
public interface PostMapper {
    int countPosts();

    List<PostWithAuthorVO> getPosts(@Param("offset") int offset, @Param("pageSize") int pageSize);
    
    void insertPost(PostVO post);

    void updatePost(PostVO post);

    void deletePost(int postId);
    
    PostWithAuthorVO selectPost(int postId); // 게시글 상세 조회 메서드

 // 게시글 댓글 조회
    List<PostComment> selectCommentsByPostId(@Param("postId") Integer postId);
    
 // 게시글 댓글 삽입
    void insertPostComment(PostComment postComment);
}
