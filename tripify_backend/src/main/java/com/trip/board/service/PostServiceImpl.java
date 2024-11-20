package com.trip.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.board.mapper.PostMapper;
import com.trip.board.util.PageNavigationUtil;
import com.trip.board.vo.PaginationVO;
import com.trip.board.vo.PostComment;
import com.trip.board.vo.PostVO;
import com.trip.board.vo.PostWithAuthorVO;

@Service
public class PostServiceImpl implements PostService {

	private final PostMapper postMapper;

	public PostServiceImpl(PostMapper postMapper) {
		this.postMapper = postMapper;
	}

	@Override
	public List<PostWithAuthorVO> getPosts(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return postMapper.getPosts(offset, pageSize);
	}

	@Override
	public PaginationVO getPagination(int page, int pageSize) {
		int totalRecords = postMapper.countPosts();
		return PageNavigationUtil.createPagination(page, totalRecords, pageSize);
	}

	@Override
	public void createPost(PostVO post) {
		postMapper.insertPost(post);
	}

	@Override
	public void updatePost(PostVO post) {
		postMapper.updatePost(post);
	}

	@Override
	public void deletePost(int postId) {
		postMapper.deletePost(postId);
	}

	@Override
	public PostWithAuthorVO getPost(int postId) {
		return postMapper.selectPost(postId);
	}

	@Override
	public List<PostComment> getCommentsByPostId(Integer postId) {
		return postMapper.selectCommentsByPostId(postId);
	}

	@Override
	public PostComment addPostComment(PostComment postComment) {
		postMapper.insertPostComment(postComment);
		return postComment;
	}

}
