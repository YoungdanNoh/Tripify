<template>
    <div class="community-board">
      <div class="container">
        <h2>커뮤니티 공유 게시판</h2>
        <div class="row">
          <div class="col-md-6" v-for="post in posts" :key="post.title">
            <div class="board-item">
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        posts: [
          { title: "최신 글 제목 1", description: "최신 글 내용 요약..." },
          { title: "최신 글 제목 2", description: "최신 글 내용 요약..." },
          { title: "최신 글 제목 3", description: "최신 글 내용 요약..." },
          { title: "최신 글 제목 4", description: "최신 글 내용 요약..." },
          { title: "최신 글 제목 5", description: "최신 글 내용 요약..." },
          { title: "최신 글 제목 6", description: "최신 글 내용 요약..." },
        ],
      };
    },
  };
  </script>
  
  <style scoped>
  .community-board {
    padding: 50px 0;
  }
  .board-item {
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
  }
  </style>
  