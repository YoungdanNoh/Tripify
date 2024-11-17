<template>
    <div class="community-container">
      <h1>커뮤니티</h1>
      <router-view /> <!-- 자식 뷰 렌더링 -->
    </div>
  </template>
  
  <script>
  export default {
    name: "CommunityView",
  };
  </script>
  
  <style scoped>
  .community-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  </style>
  