<template>
  <div class="full-screen">
    <header-nav class="header-nav" />
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import HeaderNav from "./components/HeaderNav.vue";

export default {
  components: { HeaderNav },
};
</script>

<style scoped>
.full-screen {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.header-nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  background-color: white;
  border-bottom: 1px solid #ddd;
}

.main-content {
  flex: 1;
  position: fixed; /* 화면 상단에 고정 */
  top: 60px; /* header-nav 높이만큼 아래로 내림 */
  left: 0;
  width: 100%;
  height: calc(100vh - 60px); /* 전체 높이에서 header-nav 높이만큼 뺌 */
  padding: 20px;
  overflow-y: auto;
  box-sizing: border-box;
}
</style>
