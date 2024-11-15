<template>
  <header class="header">
    <h1>Tripify</h1>
    <nav>
      <router-link to="/place">여행지</router-link>
      <router-link to="/community">커뮤니티</router-link>
      <a href="#" class="login">Login</a>
    </nav>
  </header>
</template>

<script>
export default {
  name: "HeaderNav",
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.header h1 {
  margin: 0;
}

.header nav a {
  margin-left: 10px;
}
</style>
