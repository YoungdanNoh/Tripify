<template>
  <div class="post-detail-container">
    <h1>{{ post.title }}</h1>
    <div class="meta-container">
      <p class="meta">작성일: {{ new Date(post.createdAt).toLocaleString() }}</p>
      <p class="meta">작성자 : {{ post.userName }}</p>
    </div>
    <hr>
    <p class="content">{{ post.content }}</p>

    <div class="actions" v-if="isAuthor">
      <button class="edit-button" @click="goToEdit">수정</button>
      <button class="delete-button" @click="deletePostData">삭제</button>
    </div>
    <div class="comment-form-container">
      <CommentForm @submit="handleCommentSubmit" />
    </div>
    <CommentList :itemId="postId" :type="'post'" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { fetchPost, deletePost } from '@/api/posts';
import { useUserStore } from '@/stores/user';
import { useCommentStore } from "@/stores/comment";
import CommentList from '../common/CommentList.vue';
import CommentForm from "../common/CommentForm.vue";

const router = useRouter(); // 라우터 객체
const route = useRoute(); // 현재 라우트 정보
const userStore = useUserStore();
const commentStore = useCommentStore();

const post = ref({}); // 게시글 데이터
const postId = route.params.postId; // ��시�� ID

const loading = ref(false);
const error = ref("");

const isAuthor = computed(() => {
  return post.value.userId && userStore.user?.userId && post.value.userId === userStore.user.userId;
});

// 게시글 데이터 가져오기
const fetchPostData = async () => {
  try {
    const postId = route.params.postId;
    const response = await fetchPost(postId);
    post.value = response.data;
    loadComments();
  } catch (error) {
    console.error("게시글을 가져오는 중 오류가 발생했습니다.", error);
    alert("게시글을 불러올 수 없습니다.");
  }
};

// 수정 페이지 이동
const goToEdit = () => {
  router.push(`/community/posts/${route.params.postId}/edit`);
};

// 게시글 삭제
const deletePostData = async () => {
  try {
    const postId = route.params.postId;
    await deletePost(postId);
    alert("게시글이 삭제되었습니다.");
    router.push("/community");
  } catch (error) {
    console.error("게시글 삭제 중 오류가 발생했습니다.", error);
    alert("게시글 삭제에 실패했습니다.");
  }
};

async function handleCommentSubmit(content) {
  try {
    await commentStore.addComment('post', postId, userStore.user.userId, userStore.user.userName, content);
    alert("댓글이 성공적으로 추가되었습니다.");
    commentStore.fetchComments('post', postId); // 새로고침
  } catch (error) {
    console.error("댓글 추가 실패:", error);
    alert(commentStore.error || "댓글 추가에 실패했습니다.");
  }
}

// 댓글 로드 함수
async function loadComments() {
  loading.value = true;
  error.value = null;
  try {
    await commentStore.fetchComments('post', postId); // type과 itemId로 변경
  } catch (err) {
    error.value = commentStore.error || "댓글을 불러오는 데 실패했습니다.";
    console.error(err);
  } finally {
    loading.value = false;
  }
}

// 컴포넌트가 마운트될 때 게시글 데이터 가져오기
onMounted(() => {
  fetchPostData();
}
);
</script>


<style scoped>
.post-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.content {
  min-height: 150px;
  /* 최소 높이 설정 */
  overflow-y: auto;
  /* 내용이 넘칠 경우 스크롤바 표시 */
  color: #666;
  line-height: 1.6;
  margin-top: 10px;
  padding: 10px;
  /* 패딩 추가로 내용과 테두리 간 간격 생성 */
  background: #f9f9f9;
  /* 배경색 설정으로 내용 부분 시각적 강조 */
  border-radius: 5px;
  /* 테두리 둥글게 처리 */
}

.meta-container {
  display: flex;
  justify-content: space-between;
}

.meta {
  font-size: 0.9em;
  color: #888;
  margin-bottom: 10px;
  /* 메타 정보와 내용 사이 간격 추가 */
}

.actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
}

.edit-button,
.delete-button {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-weight: bold;
}

.edit-button {
  background-color: #4CAF50;
  color: white;
}

.edit-button:hover {
  background-color: #45a049;
}

.delete-button {
  background-color: #f44336;
  color: white;
  margin-left: 10px;
}

.delete-button:hover {
  background-color: #d32f2f;
}

.comment-form-container {
  margin-bottom: 20px;
}
</style>
