import { defineStore } from "pinia";
import { getComments, addComment, deleteComment } from "@/api/place"; // deleteComment 추가

export const useCommentStore = defineStore("comment", {
  state: () => ({
    comments: [], // 댓글 목록
    error: null, // 에러 메시지
  }),
  actions: {
    async fetchComments(placeId) {
      try {
        const response = await getComments(placeId);
        this.comments = [...response.data];
        this.error = null;
      } catch (err) {
        this.comments = [];
        if (err.response && err.response.status) {
          switch (err.response.status) {
            case 404:
              this.error = "해당 장소에 대한 댓글을 찾을 수 없습니다.";
              break;
            case 500:
              this.error = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
              break;
            default:
              this.error = err.response.data?.message || "댓글을 불러오는 데 실패했습니다.";
          }
        } else {
          this.error = "네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요.";
        }
      }
    },
    async addComment(placeId, commentData) {
      try {
        const response = await addComment(placeId, commentData);
        this.comments.push({
          ...commentData,
          commentId: response.data.commentId,
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        });
        this.error = null;
        return response.data;
      } catch (err) {
        if (err.response && err.response.status) {
          switch (err.response.status) {
            case 400:
              this.error = "잘못된 요청입니다.";
              break;
            case 500:
              this.error = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
              break;
            default:
              this.error = err.response.data?.message || "댓글을 추가하는 데 실패했습니다.";
          }
        } else {
          this.error = "네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요.";
        }
        throw err;
      }
    },
    async deleteComment(commentId) {
      try {
        await deleteComment(commentId); // API 호출
        this.comments = this.comments.filter((comment) => comment.commentId !== commentId); // 삭제된 댓글 필터링
        this.error = null;
      } catch (err) {
        if (err.response && err.response.status) {
          switch (err.response.status) {
            case 404:
              this.error = "삭제할 댓글을 찾을 수 없습니다.";
              break;
            case 500:
              this.error = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
              break;
            default:
              this.error = err.response.data?.message || "댓글을 삭제하는 데 실패했습니다.";
          }
        } else {
          this.error = "네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요.";
        }
        throw err;
      }
    },
    clearComments() {
      this.comments = [];
    },
    clearError() {
      this.error = null;
    },
  },
  persist: false,
});
