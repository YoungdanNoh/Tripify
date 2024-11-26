<template>
  <div class="profile-card" v-if="user">
    <div class="upper-section">
      <div class="profile-image-container">
        <ProfileImage />
        <router-link to="/profile" class="profile-settings-btn">
          <font-awesome-icon class="icons" icon="gear" />
          <!-- Font Awesome 아이콘 사용 -->
        </router-link>
      </div>
      <div class="text-section">
        <h5>{{ user.userName }} 님</h5>
        <p>{{ user.email }}</p>
      </div>
    </div>
    <hr />
    <h5 class="schedule-title">오늘의 일정</h5>
    <div class="table-box">
      <table v-if="getTodayS.length > 0" class="schedule-table" @mouseover="showScrollbar" @mouseout="hideScrollbar">
        <tr v-for="(schedule, index) in getTodayS" :key="index">
          <td>{{ schedule.location }}</td>
          <td>{{ formatVisitTime(schedule.visit_time) }}</td>
          <td>{{ schedule.place_name }}</td>
          <td>
            <font-awesome-icon class="h-7 w-7 icons" icon="circle-play"
              @click="musicRecommend(schedule.location, schedule.visit_time, schedule.place_name)"></font-awesome-icon>
          </td>
        </tr>
      </table>
      <div v-else>오늘의 일정이 없습니다.</div>
    </div>
  </div>
  <div class="profile-card" v-else>
    <div class="profile-image-container">
      <ProfileImage />
      <router-link to="/login" class="btn btn-primary"> 로그인 </router-link>
    </div>
    <h3>방문자님, 환영합니다!</h3>
    <p>여행 계획을 시작하려면 로그인해주세요.</p>
  </div>
</template>

<script setup>
import ProfileImage from "@/components/ProfileImage.vue";
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";

// Vue Router 훅 사용
const router = useRouter();

// 상태 변수 선언
const userStore = useUserStore();
const user = computed(() => userStore.user); // 로그인 상태 확인

const store = usePlanStore(); //현재 일정 확인
const { getTodayS } = storeToRefs(store);

const showScrollbar = (e) => {
  e.target.classList.remove("no-scroll");
};

const hideScrollbar = (e) => {
  e.target.classList.add("no-scroll");
};

onMounted(async () => {
  await store.today(user.value?.userId);
});

// 시간 포맷 함수
const formatVisitTime = (time) => {
  if (!time) return "";

  const [hour, minute] = time.split(":").map((num) => parseInt(num));

  const period = hour >= 12 ? "오후" : "오전";
  const hour12 = hour % 12 || 12; // 24시간제를 12시간제로 변환 (0시 -> 12시, 15시 -> 3시 등)

  return `${period} ${hour12}:${minute < 10 ? "0" + minute : minute}`; // "오후 3:00" 형식으로 반환
};

// 노래 추천받기 버튼 클릭 함수
const musicRecommend = (location, visit_time, place_name) => {
  //console.log('노래 추천 함수:', location, visit_time, place_name);

  const hour = parseInt(visit_time.split(":")[0]); //방문 시간을 추출

  // 시간대에 따른 감성 설정
  let mood;
  if (hour >= 0 && hour < 7) {
    mood = "새벽감성";
  } else if (hour >= 7 && hour < 10) {
    mood = "이른아침";
  } else if (hour >= 10 && hour < 12) {
    mood = "점심";
  } else if (hour >= 12 && hour < 17) {
    mood = "활기찬 오후";
  } else if (hour >= 17 && hour < 21) {
    mood = "저녁감성";
  } else {
    mood = "늦은 저녁";
  }
  //console.log('노래 추천 감성:', mood);
  // 00:00 ~ 7:00 새벽감성
  // 7:00 ~ 10:00 이른아침
  // 10:00 ~ 12:00 점심
  // 12:00 ~ 17:00 활기찬 오후
  // 17:00 ~ 21:00 저녁감성
  // 21:00 ~ 00:00 늦은 저녁

  router.push({
    name: "musicRecommend",
    query: { location: location, mood: mood, place_name: place_name },
  });
};
</script>

<style scoped>
.schedule-title {
  text-align: left;
  margin-left: 10px;
  /* 오른쪽 정렬된 텍스트의 위치를 조정할 수 있습니다. */
}

.icons {
  width: 28px;
  height: 28px;
}

.profile-image {
  width: 70px;
  height: 70px;
}

.profile-card {
  background: white;
  color: black;
  border-radius: 10px;
  padding: 20px;
  width: 380px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin: 20px auto;
  /* 중앙 정렬을 위한 auto */
  min-width: 284px;
  max-height: 380px;
}

.profile-image-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.profile-settings-btn {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: transparent;
  cursor: pointer;
  color: #838383;
}

.upper-section {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 20px;
  /* 텍스트 섹션과 이미지 섹션 사이의 간격 */
}

.text-section h5,
.text-section p {
  margin: 5px 0;
}

.table-box {
  max-height: 190px;
  overflow: hidden;
  /* 초기 상태에서는 스크롤바 숨기기 */
  position: relative;
}

.table-box:hover {
  overflow-y: auto;
  /* 마우스 오버 시 스크롤 활성화 */
}

.schedule-table {
  width: 100%;
  height: 100%;
  border-collapse: collapse;
  padding-right: 10px;
  /* 스크롤바와 콘텐츠 사이에 공간을 둠 */
}

.schedule-table::-webkit-scrollbar {
  width: 8px;
  /* 스크롤바 폭 설정 */
  background-color: rgba(0, 0, 0, 0.1);
  /* 스크롤바 배경색 */
}

.schedule-table::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.5);
  /* 스크롤바 색상 설정 */
  border-radius: 4px;
  /* 스크롤바 모서리 둥글게 */
}

.schedule-table td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

.schedule-table tr:last-child td {
  border-bottom: none;
}

@media (max-width: 768px) {
  .profile-card {
    width: 100%;
    padding: 15px;
  }
}
</style>
