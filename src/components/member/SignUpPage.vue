<template>
    <div class="signup-page">
        <h2>회원가입</h2>
        <form @submit.prevent="handleSignUp">
            <FormInput label="아이디" v-model="userId" @blur="checkId" required />
            <p v-if="idError" class="error">{{ idError }}</p>
            <FormInput label="이름" v-model="userName" required />
            <FormInput label="이메일" v-model="email" type="email" required />
            <FormInput label="비밀번호" v-model="password" type="password" required />
            <Button type="submit" text="회원가입" />
        </form>
        <p v-if="message">{{ message }}</p>
    </div>
</template>
  
<script>
import { checkIdAvailability, signUp } from '@/api/user';
import FormInput from '@/components/common/FormInput.vue';
import Button from '@/components/common/Button.vue';

export default {
    components: { FormInput, Button },
    data() {
        return {
            userId: '',
            userName: '',
            email: '',
            password: '',
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
                await signUp(this.userId, this.userName, this.email, this.password);
                this.message = "회원가입이 완료되었습니다.";
                this.$router.push('/login');
            } catch {
                this.message = "회원가입에 실패했습니다.";
            }
        },
    },
};
</script>
  
<style scoped>
.error {
    color: red;
}
</style>
