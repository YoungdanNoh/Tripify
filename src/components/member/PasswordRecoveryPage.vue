<template>
    <div class="password-recovery-page">
        <h2>비밀번호 찾기</h2>
        <form @submit.prevent="handlePasswordRecovery">
            <FormInput label="이메일" v-model="email" type="email" required />
            <Button type="submit" text="비밀번호 재설정 링크 보내기" />
        </form>
        <p v-if="message">{{ message }}</p>
    </div>
</template>
  
<script>
import { findPassword } from '@/api/user';
import FormInput from '@/components/common/FormInput.vue';
import Button from '@/components/common/Button.vue';

export default {
    components: { FormInput, Button },
    data() {
        return {
            email: '',
            message: null,
        };
    },
    methods: {
        async handlePasswordRecovery() {
            try {
                await findPassword(this.email);
                this.message = "비밀번호 재설정 링크가 이메일로 전송되었습니다.";
            } catch {
                this.message = "오류가 발생했습니다. 다시 시도해주세요.";
            }
        },
    },
};
</script>
