<template>
  <div class="modal-backdrop1" @click.self="close">
    <div class="modal-content">
      <button class="close-button" @click="close">X</button>
      <div class="info-section">
        <h3>{{ place.title }}</h3>
        <img :src="place.first_image1 || defaultImage" alt="Place Image" />
        <p><strong>주소:</strong> {{ place.addr1 }} {{ place.addr2 }}</p>
        <p><strong>설명:</strong> {{ place.description || "설명이 없습니다." }}</p>
      </div>

      <div class="comments-section">
        <!-- 댓글 작성 섹션 -->
        <div class="comment-form-container">
          <CommentForm @submit="handleCommentSubmit" />
        </div>
        <!-- 댓글 리스트 -->
        <CommentList :itemId="place.place_id" :type="'place'" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, onMounted, ref } from "vue";
import { useCommentStore } from "@/stores/comment";
import { useUserStore } from "@/stores/user";
import defaultImage from "@/assets/noImage.png";
import CommentForm from "./common/CommentForm.vue";
import CommentList from "./common/CommentList.vue";

const props = defineProps({
  place: Object,
});

const emit = defineEmits(["close"]);
const commentStore = useCommentStore();
const userStore = useUserStore();

// 댓글 로드 및 에러 상태
const loading = ref(false);
const error = ref(null);

// 댓글 추가 핸들러
async function handleCommentSubmit(content) {
  try {
    await commentStore.addComment('place', props.place.place_id, userStore.user.userId, userStore.user.userName, content);
    alert("댓글이 성공적으로 추가되었습니다.");
    commentStore.fetchComments('place', props.place.place_id); // 새로고침
  } catch (error) {
    console.error("댓글 추가 실패:", error);
    alert(commentStore.error || "댓글 추가에 실패했습니다.");
  }
}

// 닫기 함수
function close() {
  emit("close");
}
</script>


<style scoped>
.modal-backdrop1 {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  display: flex;
  /* 좌우 배치를 위한 flexbox */
  flex-direction: row;
  /* info-section과 comments-section을 가로 배치 */
  justify-content: space-between;
  /* 섹션 간 간격 확보 */
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  max-width: 900px;
  /* 전체 팝업 너비 */
  width: 90%;
  position: relative;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.info-section {
  flex: 1;
  /* info-section이 공간을 균등하게 차지 */
  margin-right: 20px;
  /* comments-section과 간격 */
}

.info-section img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 15px;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
}

hr {
  margin: 10px 0;
  /* hr 위아래 간격 축소 */
}

/* 댓글 섹션 */
.comments-section {
  flex: 1.5;
  /* comments-section이 더 넓게 차지 */
  display: flex;
  flex-direction: column;
  /* 댓글 작성과 리스트를 세로로 배치 */
  gap: 20px;
  /* 댓글 작성 섹션과 리스트 간 간격 */
}

/* 댓글 작성 영역 */
.comment-form-container {
  align-items: center;
  /* 입력창과 버튼을 세로 중앙 정렬 */
  gap: 10px;
  /* 입력창과 버튼 사이 간격 */
}

/* 댓글 리스트 */
.comment-list {
  max-height: 300px;
  /* 고정 높이 */
  overflow-y: auto;
  /* 스크롤 가능 */
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}
</style>
