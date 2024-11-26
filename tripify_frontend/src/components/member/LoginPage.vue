<template>
    <div class="login-page">
      <h2 class="gothic-a1-regular">로그인</h2>
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
       <div class="link-container">

         <router-link to="/login/password-recovery">비밀번호를 잊으셨나요?</router-link>
         
         <router-link to="/login/signup">회원가입</router-link>
        </div>
    </div>
  </template>
  
  <script>
  import { ref, computed, onBeforeMount } from 'vue';
  import { useUserStore } from '@/stores/user';
  import { useRouter } from 'vue-router';
  
  export default {
    setup() {
      const email = ref('');
      const password = ref('');
      const userStore = useUserStore(); // Pinia store 사용
      const error = computed(() => userStore.error); // store의 error 상태 가져오기
      const router = useRouter(); // router 인스턴스 가져오기

      onBeforeMount(() => {
      userStore.clearError(); // 에러 상태 초기화
    });
  
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
  margin: 40px auto;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  border-radius: 8px;
  background: #fff;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 10px;
  color: #333;
  font-size: 14px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  transition: border-color 0.3s;
}

input:focus {
  border-color: #007bff;
  outline: none;
}

button {
  display: block;
  width: 100%;
  padding: 12px;
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.error {
  margin-top: 10px;
  color: #d32f2f;
  font-weight: bold;
  text-align: center;
}

.router-link {
  display: block;
  margin: 0 5px;
  text-align: center;
  color: #007bff;
  text-decoration: none;
  flex: 1;
}

.router-link:hover {
  text-decoration: underline;
}

.link-container {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}



</style>

  