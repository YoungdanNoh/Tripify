<template>
    <div class="signup-page">
      <h2>회원가입</h2>
      <form @submit.prevent="handleSignUp">
        <!-- 아이디 중복 에러 메시지 -->
        <p v-if="idError" class="error">{{ idError }}</p>
  
        <!-- 이름 입력 -->
        <div class="form-group">
          <label for="userName">이름</label>
          <input
            id="userName"
            type="text"
            v-model="userName"
            required
          />
        </div>
  
        <!-- 이메일 입력 -->
        <div class="form-group">
          <label for="email">이메일</label>
          <input
            id="email"
            type="email"
            v-model="email"
            required
          />
        </div>
  
        <!-- 비밀번호 입력 -->
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input
            id="password"
            type="password"
            v-model="password"
            required
          />
        </div>
  
        <!-- 회원가입 버튼 -->
        <button type="submit">회원가입</button>
      </form>
      <p v-if="message">{{ message }}</p>
    </div>
  </template>
  
  <script>
  import { checkIdAvailability, signUp } from "@/api/user";
  
  export default {
    data() {
      return {
        userId: "",
        userName: "",
        email: "",
        password: "",
        idError: null,
        message: null,
      };
    },
    methods: {
      async checkId() {
        try {
          const response = await checkIdAvailability(this.userId);
          this.idError = response.data ? "ID가 중복됩니다." : null;
        } catch {
          this.idError = "ID 확인 중 오류가 발생했습니다.";
        }
      },
      async handleSignUp() {
        try {
          await signUp(this.userName, this.email, this.password);
          this.message = "회원가입이 완료되었습니다.";
          this.$router.push("/login");
        } catch {
          this.message = "회원가입에 실패했습니다.";
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .signup-page {
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
  