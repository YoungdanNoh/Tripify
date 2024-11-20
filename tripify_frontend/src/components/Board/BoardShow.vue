<template>
  <div class="posts-container">
    <h1>게시판</h1>

    <!-- 글쓰기 버튼 -->
    <button class="write-button" @click="goToWrite">글 쓰기</button>

    <!-- 게시물 리스트 -->
    <table class="posts-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>작성자</th>
          <th>제목</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(post, index) in posts" :key="post.postId" @click="viewPost(post.postId)" class="post-row">
          <td>{{ totalPosts - (currentPage - 1) * limit - index }}</td>
          <td>{{ post.userName }}</td>
          <td>{{ post.title }}</td>
          <td>{{ new Date(post.createdAt).toLocaleDateString() }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 페이징 버튼 -->
    <div class="pagination">
      <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">이전</button>
      <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
      <button :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">다음</button>
    </div>
  </div>
</template>

<script>
import { fetchPosts } from "@/api/posts"; // posts API 함수 import
import { useRouter } from "vue-router";

export default {
  data() {
    return {
      posts: [], // 게시물 데이터
      currentPage: 1, // 현재 페이지
      totalPages: 1, // 총 페이지 수
      limit: 10, // 페이지당 게시물 수
      totalPosts: 0, // 전체 게시물 수
    };
  },
  setup() {
    const router = useRouter();
    return { router };
  },
  methods: {
    async fetchPosts() {
      try {
        const response = await fetchPosts(this.currentPage, this.limit); // API 호출
        this.posts = response.data.posts;
        this.totalPosts = response.data.total;
        this.totalPages = Math.ceil(this.totalPosts / this.limit);
      } catch (error) {
        console.error("게시물을 가져오는 중 오류가 발생했습니다.", error);
      }
    },
    changePage(page) {
      this.currentPage = page;
      this.fetchPosts();
    },
    goToWrite() {
      this.router.push("/community/write");
    },
    viewPost(postId) {
      this.router.push(`/community/posts/${postId}`);
    },
  },
  created() {
    this.fetchPosts();
  },
};
</script>

<style scoped>
.posts-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.write-button {
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  align-self: flex-end;
  /* 버튼을 오른쪽 끝으로 정렬 */
}

.write-button:hover {
  background-color: #45a049;
}

.posts-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.posts-table thead {
  background-color: #79818a;
  color: white;
}

.posts-table th,
.posts-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.posts-table th {
  font-weight: bold;
}

.posts-table tbody tr:hover {
  background-color: #f1f1f1;
  /* 본문 행만 마우스오버 시 강조 */
  cursor: pointer;
  /* 본문 행만 클릭 가능하도록 커서 표시 */
}


.posts-table .post-row {
  transition: background-color 0.2s;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination span {
  font-size: 16px;
  font-weight: bold;
}

.posts-table thead tr:hover {
  background-color: inherit;
  /* 헤더의 배경색이 변경되지 않도록 설정 */
  cursor: default;
  /* 헤더에 마우스 올려도 기본 커서 유지 */
}
</style>
