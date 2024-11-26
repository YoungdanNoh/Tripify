<template>
  <header class="header">
    <router-link to="/" class="logo">
      <img src="@/assets/zero-Tripify.png" alt="Tripify 로고" />
    </router-link>
    <nav class="main-nav">
      <router-link to="/place" class="nav-link"><font-awesome-icon class="h-7 w-7 icons"
          icon="map-location-dot"></font-awesome-icon>
        <p class="words gothic-a1-regular">여행지</p>
      </router-link>
      <router-link to="/plan" class="nav-link"><font-awesome-icon class="h-7 w-7 icons"
          icon="book-atlas"></font-awesome-icon>
        <p class="words gothic-a1-regular">여행계획</p>
      </router-link>
      <router-link to="/musicRecommend" class="nav-link"><font-awesome-icon class="h-7 w-7 icons"
          icon="music"></font-awesome-icon>
        <p class="words gothic-a1-regular">음악 찾기</p>
      </router-link>
      <router-link to="/community" class="nav-link"><font-awesome-icon class="h-7 w-7 icons"
          icon="comments"></font-awesome-icon>
        <p class="words gothic-a1-regular">커뮤니티</p>
      </router-link>
    </nav>
    <div class="auth-links">
      <router-link v-if="!user" to="/login" class="nav-link login-link"><i class="fas fa-sign-in-alt"></i>
        로그인</router-link>
      <div class="profile-dropdown" v-if="user">
        <div class="dropdown">
          <span class="dropdown-trigger say-hello gothic-a1-regular"> {{ user.userName }}님 안녕하세요! <i class="fas fa-chevron-down"></i></span>
          <ul class="dropdown-menu">
            <li><router-link to="/mypage" class="gothic-a1-regular">마이페이지</router-link></li>
            <li @click="logout" class="gothic-a1-regular">로그아웃</li>
          </ul>
        </div>
        <ProfileImage class="dropdown-trigger dropdown" />
      </div>
    </div>
  </header>
</template>



<script setup>
import ProfileImage from "@/components/ProfileImage.vue";
import router from "@/router";
import { useUserStore } from "@/stores/user";
import { computed } from "vue";

const userStore = useUserStore();
const user = computed(() => userStore.user);

const logout = () => {
  userStore.logoutUser();
  router.push("/");
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #79F28B;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
}

.main-nav {
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
}

.auth-links {
  display: flex;
  align-items: center;
  gap: 1rem;
  min-width: 160px;
  /* 로그인/로그아웃 버튼이 위치할 공간을 고정 */
  justify-content: flex-end;
  /* 아이템들을 오른쪽으로 정렬 */
}


.nav-link,
.dropdown-menu a {
  display: flex;
  align-items: center;
  gap: 5px;
  font-weight: 500;
  color: #333;
  transition: transform 0.3s ease, color 0.3s ease;
  margin-bottom: 0;
}

.nav-link:hover {
  color: #0056b3;
  transform: scale(1.1);
  text-decoration: none;
}

.profile-dropdown {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.dropdown-trigger {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.logo img {
  width: 160px;
  height: auto;
}

.dropdown-menu {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  padding: 0.5rem 0;
  list-style: none;
}

.profile-dropdown:hover .dropdown-menu {
  display: block;
}


.dropdown:hover .dropdown-menu {
  display: block;
}

.dropdown-menu li {
  padding: 0.5rem 1rem;
}

.dropdown-menu li:hover {
  background-color: #f0f0f0;
}

.dropdown-menu a {
  text-decoration: none;
  /* 링크에 밑줄 없앰 */
  color: inherit;
  /* 부모 요소의 글자색 상속 */
}

.icons {
  display: none;
}

.words {
  margin-bottom: 0;
}

@media (max-width: 768px) {
  .icons {
    display: inline;
    padding: 10px;
  }

  .words {
    display: none;
  }

  .say-hello {
    display: none;
  }
}
</style>
