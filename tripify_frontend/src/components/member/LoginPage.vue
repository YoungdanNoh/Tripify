<template>
    <div class="login-page">
      <h2>로그인</h2>
      <form @submit.prevent="handleLogin">
        <!-- 이메일 입력 -->
        <div class="form-group">
          <label for="email">이메일</label>
          <input id="email" type="email" v-model="email" required />
        </div>
  
        <!-- 비밀번호 입력 -->
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input id="password" type="password" v-model="password" required />
        </div>
  
        <!-- 로그인 버튼 -->
        <button type="submit">로그인</button>
      </form>
  
      <!-- 에러 메시지 -->
      <p v-if="error" class="error">{{ error }}</p>
  
      <!-- 라우터 링크 -->
      <router-link to="/login/password-recovery">비밀번호를 잊으셨나요?</router-link>
      <router-link to="/login/signup">회원가입</router-link>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import { useUserStore } from '@/stores/user';
  import { useRouter } from 'vue-router';
  
  export default {
    setup() {
      const email = ref('');
      const password = ref('');
      const userStore = useUserStore(); // Pinia store 사용
      const { error } = userStore; // store의 error 상태 가져오기
      const router = useRouter(); // router 인스턴스 가져오기
  
      const handleLogin = async () => {
        await userStore.loginUser(email.value, password.value);
        if (!userStore.error) {
          // 로그인 성공 시 다른 페이지로 이동 (예: 홈 화면)
          alert('로그인 성공!');
          router.push('/');
        }
      };
  
      return {
        email,
        password,
        error,
        handleLogin,
      };
    },
  };
  </script>
  
  <style scoped>
  .login-page {
    max-width: 400px;
    margin: auto;
  }
  .form-group {
    margin-bottom: 15px;
  }
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
  }
  button {
    display: block;
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    cursor: pointer;
  }
  button:hover {
    background-color: #0056b3;
  }
  .error {
    margin-top: 10px;
    color: red;
    font-weight: bold;
  }
  </style>
  