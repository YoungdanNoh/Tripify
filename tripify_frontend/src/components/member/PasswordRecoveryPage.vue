<template>
    <div class="password-recovery-page">
        <h2>비밀번호 찾기</h2>
        <form @submit.prevent="handlePasswordRecovery" v-if="!passwordFound">
            <div class="form-group">
                <label for="email">이메일</label>
                <input id="email" type="email" v-model="email" required />
            </div>
            <p v-if="message" :class="{ success: passwordFound, error: !passwordFound }">{{ message }}</p>
            <button type="submit">비밀번호 확인</button>
            <!-- 오류 발생 시 회원가입 링크 표시 -->
            <div v-if="errorOccurred" class="signup-link">
                <router-link to="/login/signup">회원가입 하기</router-link>
            </div>
        </form>
        <div v-else>
            <p class="success">{{ message }}</p>
            <button @click="redirectToLogin">로그인하기</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { findPassword } from '@/api/user';

const email = ref(''); // 사용자가 입력한 이메일
const message = ref(null); // 화면에 표시할 메시지
const passwordFound = ref(false); // 비밀번호 찾기 성공 여부
const errorOccurred = ref(false); // 오류 발생 여부
const router = useRouter(); // Vue Router 사용

const handlePasswordRecovery = async () => {
    try {
        // 초기화
        errorOccurred.value = false;
        message.value = null;

        // API 호출 및 응답 데이터 받기
        const response = await findPassword(email.value);

        // 성공적으로 비밀번호를 찾았을 때
        message.value = "비밀번호: " + response.data;
        passwordFound.value = true; // 비밀번호 찾기 성공 상태 설정
    } catch (error) {
        // 오류 발생 시 메시지 표시
        passwordFound.value = false; // 성공 상태 초기화
        errorOccurred.value = true; // 오류 상태 설정
        if (error.response && error.response.status === 404) {
            message.value = error.response.data; // 백엔드에서 반환한 메시지
        } else {
            message.value = "오류가 발생했습니다. 다시 시도해주세요.";
        }
        console.error(error);
    }
};

// 로그인 페이지로 이동
const redirectToLogin = () => {
    router.push('/login');
};
</script>

<style scoped>
.password-recovery-page {
    max-width: 400px;
    margin: 40px auto;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    background: #fff;
}

h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
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

p {
    margin-top: 10px;
    text-align: center;
    font-size: 14px;
    color: #007bff;
}

p.error {
    color: #d32f2f;
    font-weight: bold;
}

p.success {
    color: #4caf50;
    font-weight: bold;
}

.signup-link {
    text-align: center;
    margin-top: 15px;
}

.signup-link a {
    color: #007bff;
    text-decoration: none;
}

.signup-link a:hover {
    text-decoration: underline;
}
</style>
