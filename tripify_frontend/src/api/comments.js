import axios from "@/util/axios-common.js";

const API_URL = "/";

// 여행지 댓글 가져오기
export const fetchCommentsByPlaceId = (placeId) => {
  return axios.get(`${API_URL}place/${placeId}/comments`);
};

// 여행지에 댓글 추가
export const addCommentToPlace = (placeId, userId, content) => {
  const commentRequest = { userId, content };
  return axios.post(`${API_URL}place/${placeId}/comments`, commentRequest);
};

// 여행지 댓글 삭제
export const deletePlaceComment = (commentId) => {
  return axios.delete(`${API_URL}place/comments/${commentId}`);
};

// 게시글 댓글 가져오기
export const fetchCommentsByPostId = (postId) => {
  return axios.get(`${API_URL}posts/${postId}/comments`);
};

// 게시글에 댓글 추가
export const addCommentToPost = (postId, userId, content) => {
  const commentRequest = { userId, content };
  return axios.post(`${API_URL}posts/${postId}/comments`, commentRequest);
};

// 게시글 댓글 삭제
export const deletePostComment = (commentId) => {
  return axios.delete(`${API_URL}posts/comments/${commentId}`);
};
