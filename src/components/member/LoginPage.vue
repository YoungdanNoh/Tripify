<template>
    <div class="login-page">
        <h2>로그인</h2>
        <form @submit.prevent="handleLogin">
            <FormInput label="이메일" v-model="email" type="email" required />
            <FormInput label="비밀번호" v-model="password" type="password" required />
            <Button type="submit" text="로그인" />
        </form>
        <p v-if="error" class="error">{{ error }}</p>
        <router-link to="/find-password">비밀번호를 잊으셨나요?</router-link>
        <router-link to="/signup">회원가입</router-link>
    </div>
</template>
  
<script>
import { login } from '@/api/user';
import FormInput from '@/components/common/FormInput.vue';
import Button from '@/components/common/Button.vue';

export default {
    components: { FormInput, Button },
    data() {
        return {
            email: '',
            password: '',
            error: null,
        };
    },
    methods: {
        async handleLogin() {
            try {
                const response = await login(this.email, this.password);
                if (response.data) {
                    this.$router.push('/profile');
                } else {
                    this.error = "로그인 실패. 이메일과 비밀번호를 확인하세요.";
                }
            } catch {
                this.error = "로그인 에러가 발생했습니다.";
            }
        },
    },
};
</script>
  