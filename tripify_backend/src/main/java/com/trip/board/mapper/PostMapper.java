package com.trip.board.mapper;

import com.trip.board.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    int countPosts();

    List<PostVO> getPosts(@Param("offset") int offset, @Param("pageSize") int pageSize);
    
    void insertPost(PostVO post);

    void updatePost(PostVO post);

    void deletePost(int postId);
    
    PostVO selectPost(int postId); // 게시글 상세 조회 메서드

}
