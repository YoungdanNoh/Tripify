// baseCommentStore.js
import { defineStore } from "pinia";
import {
  fetchCommentsByPlaceId,
  addCommentToPlace,
  deletePlaceComment,
  fetchCommentsByPostId,
  addCommentToPost,
  deletePostComment,
} from "@/api/comments"; // 수정된 API 경로를 임포트합니다

export const useCommentStore = defineStore("baseComment", {
  state: () => ({
    comments: [],
    error: null,
  }),
  actions: {
    async fetchComments(type, itemId) {
      try {
        let response;
        if (type === "place") {
          response = await fetchCommentsByPlaceId(itemId);
        } else if (type === "post") {
          response = await fetchCommentsByPostId(itemId);
        }
        this.comments = response.data || [];
        this.error = null;
      } catch (err) {
        this.handleError(err);
      }
    },
    async addComment(type, itemId, userId, userName, content) {
      try {
        let response;
        if (type === "place") {
          response = await addCommentToPlace(itemId, userId, content);
        } else if (type === "post") {
          response = await addCommentToPost(itemId, userId, content);
        }
        this.error = null;
      } catch (err) {
        this.handleError(err);
      }
    },
    async deleteComment(type, commentId) {
      try {
        let response;
        if (type === "place") {
          response = await deletePlaceComment(commentId);
        } else if (type === "post") {
          response = await deletePostComment(commentId);
        }
        if (response.status === 200) {
          this.comments = this.comments.filter((c) => c.commentId !== commentId);
        }
        this.error = null;
      } catch (err) {
        this.handleError(err);
      }
    },
    handleError(err) {
      this.error = err.response?.data?.message || "네트워크 오류가 발생했습니다.";
      if (err.response && err.response.status === 500) {
        this.error = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
      }
    },
    clearComments() {
      this.comments = [];
    },
    clearError() {
      this.error = null;
    },
  },
});
