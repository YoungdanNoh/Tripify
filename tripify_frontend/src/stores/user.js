import { defineStore } from 'pinia';
import { login } from '@/api/user';

export const useUserStore = defineStore('user', {
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
        this.user = null; // 로그인 실패 시 사용자 정보 초기화
        this.error = err.response?.data || '로그인에 실패했습니다.';
      }
    },
    logoutUser() {
      this.user = null; // 사용자 정보 초기화
      this.error = null; // 에러 초기화
    },
  },
});
