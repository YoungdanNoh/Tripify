<template>
    <div class="comment-form">
        <textarea v-model="content" placeholder="댓글을 입력해주세요." rows="3" class="comment-textarea"></textarea>
        <button @click="submitContent" class="submit-button">
            댓글 작성
        </button>
    </div>
</template>

<script setup>
import { ref } from "vue";

// Emits 정의
const emit = defineEmits(["submit"]); // emit 선언

// 상태 정의
const content = ref("");

// 댓글 작성 버튼 클릭 시 emit
function submitContent() {
    if (!content.value.trim()) {
        alert("댓글 내용을 입력해주세요.");
        return;
    }
    emit("submit", content.value); // 부모 컴포넌트로 content 전달
    content.value = ""; // 입력 필드 초기화
}

</script>

<style scoped>
.comment-form {
    display: flex;
    flex-direction: row;
    /* 가로 배치 */
    gap: 10px;
    /* 텍스트 에어리어와 버튼 사이 간격 */
    align-items: center;
    /* 버튼과 텍스트 에어리어 수직 정렬 */
    margin-top: 20px;
}

.comment-textarea {
    flex: 1;
    /* 텍스트 에어리어가 가로 공간을 더 차지하도록 설정 */
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
    /* 버튼 텍스트가 줄바꿈되지 않도록 설정 */
}

.submit-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}</style>

