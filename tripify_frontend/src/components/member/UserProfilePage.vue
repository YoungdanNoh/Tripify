<template>
  <div class="profile-page">
    <h2>프로필 정보</h2>
    <form @submit.prevent="handleUpdate">
      <!-- 이름 -->
      <div class="form-group">
        <label for="userName">이름</label>
        <input
          id="userName"
          type="text"
          v-model="formData.userName"
          :disabled="!isEditing"
          required
        />
      </div>

      <!-- 이메일 (비활성화) -->
      <div class="form-group">
        <label for="email">이메일</label>
        <input id="email" type="email" v-model="formData.email" disabled />
      </div>

      <!-- 비밀번호 (정보 수정 중에만 표시) -->
      <div class="form-group" v-if="isEditing">
        <label for="password">비밀번호</label>
        <input
          id="password"
          type="password"
          v-model="formData.password"
          placeholder="새 비밀번호를 입력하세요"
        />
      </div>

      <!-- 정보 수정 버튼 -->
      <button type="button" v-if="!isEditing" @click="enableEditing">
        정보 수정
      </button>
      <button type="submit" v-if="isEditing">저장</button>
    </form>

    <!-- 회원 탈퇴 버튼 -->
    <button @click="handleDelete">회원 탈퇴</button>

    <!-- 탈퇴 완료 메시지 -->
    <AlertMessage
      v-if="showAlert"
      message="회원 탈퇴가 완료되었습니다."
      @closed="handleAlertClose"
    />

    <!-- 일반 메시지 -->
    <p v-if="message" :class="{'error': isError}">{{ message }}</p>
  </div>
</template>

<script>
import { useUserStore } from "@/stores/user";
import { updateUser, deleteUser } from "@/api/user";
import { computed, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import AlertMessage from "@/components/member/AlertMessage.vue";

export default {
  components: {
    AlertMessage,
  },
  setup() {
    const userStore = useUserStore();
    const router = useRouter(); // router 인스턴스 가져오기

    // Pinia에서 유저 정보 가져오기
    const user = computed(() => userStore.user);

    // 수정 가능한 form 데이터
    const formData = reactive({
      userName: user.value?.userName || "",
      email: user.value?.email || "",
      password: "",
    });

    const isEditing = ref(false); // 수정 모드 상태
    const message = ref(""); // 일반 메시지
    const showAlert = ref(false); // AlertMessage 표시 여부
    const isError = ref(false); // 에러 상태

    const enableEditing = () => {
      isEditing.value = true;
    };

    const handleUpdate = async () => {
      try {
        const { userName, email, password } = formData;
        await updateUser(userName, email, password || null); // API 호출
        userStore.user.userName = userName; // Pinia 상태 업데이트
        message.value = "프로필 정보가 성공적으로 수정되었습니다.";
        isEditing.value = false;
        isError.value = false;
      } catch (err) {
        message.value = "프로필 정보 수정에 실패했습니다.";
        isError.value = true;
        console.error(err);
      }
    };

    const handleDelete = async () => {
      try {
        // email 값을 가져오기 (예: Pinia 또는 상태에서)
        const { email } = userStore.user;

        if (!email) {
          message.value = "회원 정보를 확인할 수 없습니다.";
          isError.value = true;
          return;
        }

        // API 호출
        await deleteUser(email);

        // 상태 초기화 및 탈퇴 후 처리
        userStore.user = null; // Pinia 상태 초기화
        showAlert.value = true; // AlertMessage 표시
        message.value = ""; // 일반 메시지 초기화

        // 3초 후 페이지 이동
        setTimeout(() => {
          router.push("/");
        }, 3000);
      } catch (err) {
        message.value = "회원 탈퇴에 실패했습니다.";
        isError.value = true;
        console.error(err);
      }
    };

    const handleAlertClose = () => {
      showAlert.value = false; // AlertMessage 닫기
    };

    return {
      formData,
      isEditing,
      enableEditing,
      handleUpdate,
      handleDelete,
      message,
      showAlert,
      handleAlertClose,
      isError,
    };
  },
};
</script>

<style scoped>
.profile-page {
  max-width: 400px;
  margin: 40px auto;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
  margin-top: 20px; /* 조정된 마진 */
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
}

.error {
  color: #d32f2f; /* 오류 메시지 색상 */
  font-weight: bold;
}
</style>
