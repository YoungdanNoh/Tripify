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
  
      <!-- 메시지 -->
      <p v-if="message">{{ message }}</p>
    </div>
  </template>
  
  <script>
  import { useUserStore } from "@/stores/user";
  import { updateUser } from "@/api/user";
  import { computed, reactive, ref } from "vue";
  
  export default {
    setup() {
      const userStore = useUserStore();
  
      // Pinia에서 유저 정보 가져오기
      const user = computed(() => userStore.user);
  
      // 수정 가능한 form 데이터
      const formData = reactive({
        userName: user.value?.userName || "",
        email: user.value?.email || "",
        password: "",
      });
  
      const isEditing = ref(false) // 수정 모드 상태
      const message = ref("") // 상태 메시지
  
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
        } catch (err) {
          message.value = "프로필 정보 수정에 실패했습니다.";
          console.error(err);
        }
      };
  
      const handleDelete = () => {
        // 회원 탈퇴 로직 구현
        console.log("회원 탈퇴 로직 실행");
        message.value = "회원 탈퇴 기능은 아직 구현되지 않았습니다.";
      };
  
      return {
        formData,
        isEditing,
        enableEditing,
        handleUpdate,
        handleDelete,
        message,
      };
    },
  };
  </script>
  
  <style scoped>
  .profile-page {
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
  p {
    margin-top: 10px;
    color: green;
    font-weight: bold;
  }
  </style>
  