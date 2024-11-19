<template>
  <header class="header">
    <router-link to="/" class="logo">
      <img src="@/assets/zero-Tripify.png" alt="Tripify 로고" />
    </router-link>
    <nav>
      <!-- 기본 메뉴 -->
      <router-link to="/place">여행지</router-link>
      <router-link to="/plan">여행계획</router-link>
      <!-- <button @click="spotify" class="image-button">
        <img src="@/assets/spotify-logo.png" alt="Spotify 버튼" />
      </button> -->
      <router-link to="/community">커뮤니티</router-link>
      <router-link v-if="!user" to="/login">로그인</router-link>

      <!-- 로그인된 경우 드롭다운 -->
      <div class="dropdown" v-if="user">
        <span class="dropdown-trigger"> {{ user.userName }}님 안녕하세요! </span>
        <ul class="dropdown-menu">
          <li><router-link to="/profile">회원정보 수정</router-link></li>
          <li @click="logout">로그아웃</li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { computed } from "vue";
import { authorize } from "@/api/spotify";

const userStore = useUserStore();
const user = computed(() => userStore.user);

const logout = () => {
  console.log("로그아웃 클릭!");
  userStore.logoutUser();
};

const spotify = () => {
  console.log("Spotify 인증 실행!");
  authorize();
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
nav {
  display: flex;
  gap: 1rem;
}

.logo img {
  width: 140px; /* 로고 너비 고정 */
  height: auto; /* 비율 유지 */
}

.image-button {
  background: none; /* 배경 제거 */
  border: none; /* 테두리 제거 */
  cursor: pointer;
}

.image-button img {
  width: 70px; /* 버튼 크기 조정 */
  height: auto; /* 이미지 비율 유지 */
}

/* 드롭다운 스타일 */
.dropdown {
  position: relative;
}

.dropdown-trigger {
  cursor: pointer;
  font-weight: bold;
}

.dropdown-menu {
  position: absolute;
  top: 100%; /* 트리거 아래에 표시 */
  left: 0;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  padding: 0.5rem 0;
  list-style: none;
  margin: 0;
  display: none; /* 기본적으로 숨김 */
}

.dropdown-menu li {
  padding: 0.5rem 1rem;
  white-space: nowrap;
}

.dropdown-menu li:hover {
  background-color: #f0f0f0;
  cursor: pointer;
}

.dropdown-menu a {
  text-decoration: none;
  color: inherit;
}

/* 드롭다운 트리거에 마우스를 올리면 메뉴 표시 */
.dropdown:hover .dropdown-menu {
  display: block;
}
</style>
