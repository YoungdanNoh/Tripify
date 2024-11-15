<template>
    <div class="profile-page">
        <h2>프로필 정보</h2>
        <form @submit.prevent="handleUpdate">
            <FormInput label="이름" v-model="user.userName" required />
            <FormInput label="이메일" v-model="user.email" type="email" required />
            <FormInput label="비밀번호" v-model="user.password" type="password" required />
            <Button type="submit" text="정보 수정" />
        </form>
        <Button @click="handleDelete" text="회원 탈퇴" />
        <p v-if="message">{{ message }}</p>
    </div>
</template>
  
<script>
import { fetchUser, updateUser, deleteUser } from '@/api/user';
import FormInput from '@/components/common/FormInput.vue';
import Button from '@/components/common/Button.vue';

export default {
    components: { FormInput, Button },
    data() {
        return {
            user: {
                userName: '',
                email: '',
                password: '',
            },
            message: null,
        };
    },
    async created() {
        try {
            const response = await fetchUser();
            this.user = response.data;
        } catch {
            this.message = "사용자 정보를 불러오는 중 오류가 발생했습니다.";
        }
    },
    methods: {
        async handleUpdate() {
            try {
                await updateUser(this.user.userId, this.user.userName, this.user.email, this.user.password);
                this.message = "회원 정보가 수정되었습니다.";
            } catch {
                this.message = "회원 정보 수정에 실패했습니다.";
            }
        },
        async handleDelete() {
            try {
                await deleteUser(this.user.userId);
                this.message = "회원이 삭제되었습니다.";
                this.$router.push('/signup');
            } catch {
                this.message = "회원 탈퇴에 실패했습니다.";
            }
        },
    },
};
</script>
  
<style scoped>
.message {
    color: green;
}
</style>
