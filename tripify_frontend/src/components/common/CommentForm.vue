<template>
    <div class="comment-form">
        <textarea v-model="content" placeholder="댓글을 입력해주세요." rows="3" class="comment-textarea" @focus="checkLogin">
        </textarea>
        <button @click="submitContent" class="submit-button">
            댓글 작성
        </button>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user"; // 사용자 스토어 가져오기

const emit = defineEmits(["submit"]);
const content = ref("");

const userStore = useUserStore(); // 사용자 스토어 인스턴스화

function submitContent() {
    if (!content.value.trim()) {
        alert("댓글 내용을 입력해주세요.");
        return;
    }
    emit("submit", content.value);
    content.value = ""; // 입력 필드 초기화
}

function checkLogin(event) {
    if (!userStore.user) { // 로그인 상태 확인
        alert("로그인해주세요!");
        event.target.blur(); // 포커스 제거
    }
}
</script>

<style scoped>
.comment-form {
    display: flex;
    flex-direction: row;
    gap: 10px;
    align-items: center;
    margin-top: 20px;
}

.comment-textarea {
    flex: 1;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: none;
}

.submit-button {
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    white-space: nowrap;
}

.submit-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}
</style>
