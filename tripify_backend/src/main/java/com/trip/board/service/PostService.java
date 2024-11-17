package com.trip.board.service;

import com.trip.board.vo.PaginationVO;
import com.trip.board.vo.PostVO;

import java.util.List;

public interface PostService {
    List<PostVO> getPosts(int page, int pageSize);

    PaginationVO getPagination(int page, int pageSize);
    
    void createPost(PostVO post);

    void updatePost(PostVO post);

    void deletePost(int postId);
    
    PostVO getPost(int postId); // 게시글 상세 조회 메서드

}
