<template>
  <div class="full-screen">
    <header-nav class="header-nav" />
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import HeaderNav from "./components/HeaderNav.vue";

import { useUserStore } from "@/stores/user";

</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
.do-hyeon-regular {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 400;
  font-style: normal;
}
</style>

<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gothic+A1&display=swap');
.gothic-a1-regular {
  font-family: "Gothic A1", sans-serif;
  font-weight: 400;
  font-style: normal;
}

</style>

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
  position: fixed;
  top: 60px; /* header-nav 높이만큼 아래로 내림 */
  left: 0;
  width: 100%;
  height: calc(100vh - 60px); /* 전체 높이에서 header-nav 높이만큼 뺌 */
  padding: 20px;
  overflow-y: auto;
  box-sizing: border-box;
}
</style>
