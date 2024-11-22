<template>
  <div class="main-container">
    <!-- 메인 배너 -->
    <div class="main-banner">
      <div class="banner-content">
        <h1>당신의 다음 여행을 계획하세요</h1>
        <div class="search-bar">
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              placeholder="도시, 호텔, 관광지 검색"
              v-model="searchKeyword"
            />
            <button class="btn btn-primary" @click="performSearch">검색</button>
          </div>
        </div>
      </div>

      <!-- 사용자 프로필 정보 -->
      <div class="profile-section" v-if="user">
        <div class="profile-card">
          <!-- 프로필 이미지 -->
          <div class="profile-image-container">
            <ProfileImage />
          </div>
          <h3>{{ user.userName }}</h3>
          <p>{{ user.email }}</p>
          <router-link to="/profile" class="btn btn-outline-primary"> 프로필 수정 </router-link>
          <hr />
          <h4>오늘의 일정</h4>
          <ul>
            <div v-if="getTodayS.length == 0">오늘의 일정이 없습니다.</div>
            <li v-for="(schedule, index) in getTodayS" :key="index">
              {{ schedule.location }} - {{ formatVisitTime(schedule.visit_time) }}
              {{ schedule.place_name }}
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- 인기 관광지 섹션 -->
    <PopularDestinations />

    <!-- 가장 많이 조회된 장소 섹션 -->
    <MostViewed />

    <!-- 커뮤니티 게시판 섹션 -->
    <CommunityBoard />
  </div>
</template>

<script setup>
import ProfileImage from "@/components/ProfileImage.vue";
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import PopularDestinations from "@/components/PopularDestinations.vue";
import MostViewed from "@/components/MostViewed.vue";
import CommunityBoard from "@/components/Board/CommunityBoard.vue";

// 상태 변수 선언
const searchKeyword = ref("");
const userStore = useUserStore();
const user = computed(() => userStore.user); // 로그인 상태 확인

const store = usePlanStore(); //현재 일정 확인
const { getTodayS } = storeToRefs(store);

const todaySchedules = ref([]);

onMounted(async () => {
  await store.today(user.value.userId);
});

// Vue Router 훅 사용
const router = useRouter();

// 검색 실행 함수
const performSearch = () => {
  if (searchKeyword.value.trim() === "") {
    alert("검색어를 입력해주세요!");
    return;
  }
  router.push({ name: "place", params: { keyword: searchKeyword.value } });
};

// 시간 포맷 함수
const formatVisitTime = (time) => {
  if (!time) return "";

  const [hour, minute] = time.split(":").map((num) => parseInt(num));

  const period = hour >= 12 ? "오후" : "오전";
  const hour12 = hour % 12 || 12; // 24시간제를 12시간제로 변환 (0시 -> 12시, 15시 -> 3시 등)

  return `${period} ${hour12}:${minute < 10 ? "0" + minute : minute}`; // "오후 3:00" 형식으로 반환
};
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
}

.main-banner {
  background-image: url("https://www.balpumnews.com/wp-content/uploads/2024/03/3087_16272_5619.jpg");
  background-size: cover;
  background-position: center;
  height: 500px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
}

.banner-content {
  text-align: center;
  flex: 1;
}

.search-bar {
  width: 60%;
  max-width: 600px;
  margin: 20px auto;
}

.search-bar input {
  height: 50px;
  border-radius: 0;
}

.search-bar button {
  height: 50px;
}

.profile-section {
  flex: 0.3;
  display: flex;
  justify-content: flex-end;
}

.profile-card {
  background: white;
  color: black;
  border-radius: 10px;
  padding: 20px;
  width: 300px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.profile-image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-bottom: 10px;
}

.profile-card h3 {
  margin: 10px 0;
}

.profile-card ul {
  list-style: none;
  padding: 0;
  margin: 10px 0 0;
}

.profile-card ul li {
  margin-bottom: 5px;
  font-size: 14px;
}
</style>
