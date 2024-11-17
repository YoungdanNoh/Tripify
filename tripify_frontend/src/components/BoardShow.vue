<template>
    <div class="posts-container">
      <h1>게시판</h1>
  
      <!-- 글쓰기 버튼 -->
      <button class="write-button" @click="goToWrite">글 쓰기</button>
  
      <!-- 게시물 리스트 -->
      <div class="post" v-for="post in posts" :key="post.postId" @click="viewPost(post.postId)">
        <h3>{{ post.title }}</h3>
        <p>{{ post.content }}</p>
        <p class="meta">작성일: {{ new Date(post.createdAt).toLocaleString() }}</p>
      </div>
  
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
        posts: [],
        currentPage: 1,
        totalPages: 1,
        limit: 5,
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
          this.totalPages = Math.ceil(response.data.total / this.limit);
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
  }
  .write-button {
    display: block;
    margin-bottom: 20px;
    padding: 10px 15px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .write-button:hover {
    background-color: #218838;
  }
  .post {
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
    cursor: pointer;
  }
  .post h3 {
    margin: 0;
  }
  .post .meta {
    font-size: 0.8em;
    color: #666;
  }
  .pagination {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
  }
  .pagination button {
    padding: 5px 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .pagination button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  .pagination span {
    font-weight: bold;
  }
  </style>
  