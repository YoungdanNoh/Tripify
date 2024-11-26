import axios from "@/util/axios-common.js";

const API_URL = "/posts";

// 게시글 목록 조회
export const fetchPosts = (page = 1, pageSize = 10) => {
  return axios.get(API_URL, { params: { page, pageSize } });
};

// 게시글 상세 조회
export const fetchPost = (postId) => {
  return axios.get(`${API_URL}/${postId}`);
};

// 게시글 작성
export const createPost = (userId, title, content) => {
  return axios.post(API_URL, { userId, title, content });
};

// 게시글 수정
export const updatePost = (postId, title, content) => {
  console.log("api log:", postId, title, content);
  return axios.put(`${API_URL}/${postId}`, { title, content });
};

// 게시글 삭제
export const deletePost = (postId) => {
  return axios.delete(`${API_URL}/${postId}`);
};

export const fetchPostsByUserId = (userId) => {
  return axios.get(`${API_URL}/myposts/${userId}`);
};
