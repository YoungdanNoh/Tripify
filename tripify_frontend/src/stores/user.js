import { defineStore } from "pinia";
import { login } from "@/api/user";

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null, // 로그인 사용자 정보
    error: null, // 에러 메시지
  }),
  actions: {
    async loginUser(email, password) {
      try {
        const response = await login(email, password);
        this.user = response.data; // 로그인 성공 시 사용자 정보 저장
        this.error = null; // 에러 초기화
      } catch (err) {
        this.user = null;
        if (err.response) {
          switch (err.response.status) {
            case 401:
              this.error = "이메일 혹은 비밀번호가 올바르지 않습니다.";
              break;
            case 500:
              this.error = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
              break;
            default:
              this.error = err.response.data.message || "로그인에 실패했습니다.";
          }
        } else {
          this.error = "네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요.";
        }
      }
    },
    logoutUser() {
      this.user = null; // 사용자 정보 초기화
      this.error = null; // 에러 초기화
    },
    clearError() {
      this.error = null; // 에러 상태 초기화
    },
    getUserId() {
      return this.user?.userId || null;
    },
  },
  persist: {
    storage: window.sessionStorage, // localStorage를 사용
    key: "user", // 로�� 스토리지에 'user' ���로 저장
  },
});
