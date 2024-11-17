<template>
  <div class="post-detail-container">
    <h1>{{ post.title }}</h1>
    <p>{{ post.content }}</p>
    <p class="meta">작성일: {{ new Date(post.createdAt).toLocaleString() }}</p>

    <div class="actions" v-if="isAuthor">
      <button class="edit-button" @click="goToEdit">수정</button>
      <button class="delete-button" @click="deletePostData">삭제</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { fetchPost, deletePost } from "@/api/posts";
import { useUserStore } from "@/stores/user";

export default {
  setup() {
    const router = useRouter(); // 라우터 객체
    const route = useRoute(); // 현재 라우트 정보
    const userStore = useUserStore();

    const post = ref({}); // 게시글 데이터

    const isAuthor = computed(() => {
      return (
        post.value.userId &&
        userStore.user?.userId &&
        post.value.userId === userStore.user.userId
      );
    });

    // 게시글 데이터 가져오기
    const fetchPostData = async () => {
      try {
        const postId = route.params.postId;
        const response = await fetchPost(postId);

        post.value = response.data;
        console.log("작성자:", post.value.userId); // postId 확인
        console.log("로그인한사람:", userStore.getUserId());
      } catch (error) {
        console.error("게시글을 가져오는 중 오류가 발생했습니다.", error);
        alert("게시글을 불러올 수 없습니다.");
      }
    };

    // 수정 페이지 이동
    const goToEdit = () => {
      router.push(`/community/posts/${route.params.postId}/edit`); // 수정 페이지로 이동
    };

    // 게시글 삭제
    const deletePostData = async () => {
      try {
        const postId = route.params.postId; // 라우트에서 postId 가져오기
        await deletePost(postId); // API 호출
        alert("게시글이 삭제되었습니다.");
        router.push("/community"); // 게시판으로 이동
      } catch (error) {
        console.error("게시글 삭제 중 오류가 발생했습니다.", error);
        alert("게시글 삭제에 실패했습니다.");
      }
    };

    // 컴포넌트가 마운트될 때 게시글 데이터 가져오기
    onMounted(() => {
      fetchPostData();
    });

    return {
      post,
      goToEdit,
      deletePostData,
      isAuthor
    };
  },
};
</script>

<style scoped>
.post-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
.meta {
  font-size: 0.8em;
  color: #666;
}
.actions {
  margin-top: 20px;
}
.edit-button,
.delete-button {
  margin-right: 10px;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.edit-button {
  background-color: #ffc107;
  color: white;
}
.edit-button:hover {
  background-color: #e0a800;
}
.delete-button {
  background-color: #dc3545;
  color: white;
}
.delete-button:hover {
  background-color: #c82333;
}
</style>
