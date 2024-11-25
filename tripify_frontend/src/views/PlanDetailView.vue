<template>
  <div class="container my-5">
    <!-- 여행 상세 정보 -->
    <div v-if="detail" class="card mb-4 shadow-sm">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="detail.img || defaultImage"
            class="img-fluid rounded-start"
            alt="여행 이미지"
            style="object-fit: cover; height: 100%"
          />
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ detail.title }}</h5>
            <p class="card-text">
              <small class="text-muted">{{
                formatDateRange(detail.start_date, detail.end_date)
              }}</small>
            </p>
            <p class="card-text">
              <i class="bi bi-geo-alt-fill"></i> {{ detail.location }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- 플레이리스트 모달 -->
    <!-- <div
      v-if="isPlaylistModalVisible"
      class="modal-overlay"
      @click.self="closePlaylistModal"
    >
      <div class="modal-content">
        <button class="close-button" @click="closePlaylistModal">×</button>
        <div class="recommend-container" v-if="playlist.length">
          <h3>추천 음악</h3>
          <div
            v-for="(item, index) in playlist"
            :key="index"
            class="recommend-item"
          >
            <img :src="item.albumImage" alt="Album Art" class="album-art" />
            <div class="track-info">
              <a :href="item.spotifyUrl" target="_blank" class="track-name">
                <h4>{{ item.name }}</h4>
              </a>
              <p>{{ item.artist }}</p>
            </div>
          </div>
        </div>
      </div>
    </div> -->

    <!-- 기존 추천 음악 모달 -->
    <div
      v-if="isPlaylistModalVisible"
      class="modal-overlay"
      @click.self="closePlaylistModal"
    >
      <div class="modal-content">
        <button class="close-button" @click="closePlaylistModal">×</button>
        <div class="recommend-container" v-if="playlist.length">
          <h3>추천 음악</h3>
          <div
            v-for="(item, index) in playlist"
            :key="index"
            class="recommend-item"
          >
            <img :src="item.albumImage" alt="Album Art" class="album-art" />
            <div class="track-info">
              <a :href="item.spotifyUrl" target="_blank" class="track-name">
                <h4>{{ item.name }}</h4>
              </a>
              <p>{{ item.artist }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 새로운 음악 생성 모달 -->
    <div
      v-if="isMusicCreationModalVisible"
      class="modal-overlay"
      @click.self="closeMusicCreationModal"
    >
      <div class="modal-content">
        <h3>음악을 추천받아 보세요!</h3>
        <p>
          {{ musicCreationDetails.place_name }}에서 방문한 시간과 장소에 맞는
          음악을 추천해 드립니다.
        </p>
        <div class="modal-buttons">
          <button
            class="btn btn-outline-secondary"
            @click="closeMusicCreationModal"
          >
            닫기
          </button>
          <button class="btn btn-success" @click="musicRecommend">
            지금 바로 생성하기
          </button>
        </div>
      </div>
    </div>

    <musicRecommendModal v-if="isMusicRecommendModalVisible" :data="recommendationData" @close="closeRecommendationModal"></musicRecommendModal>

    <!-- 일정 관리 -->
    <div class="d-flex justify-content-between align-items-center">
      <h3>일정 관리</h3>
      <!-- 일정 추가 버튼 -->
      <button
        class="btn btn-outline-success custom-blue-button"
        @click="addNewActivity(0, null)"
      >
        + 새 활동 추가
      </button>
    </div>

    <!-- 일정 박스들을 가로로 나열 -->
    <div v-if="detail" class="itinerary-container">
      <div
        v-for="day in detail.itinerary"
        :key="day.visit_date"
        class="card mb-3 shadow-sm"
      >
        <div
          class="card-header d-flex justify-content-between align-items-center"
        >
          <h5>{{ day.visit_date }}</h5>
          <button
            class="btn btn-outline-success"
            @click="addNewActivity(detail.plan_id, day.visit_date)"
          >
            + 새 일정 추가
          </button>
        </div>
        <!-- activities 순회 -->
        <div
          v-for="activity in day.activities"
          :key="activity.plan_place_id"
          class="card-body"
        >
          <h6 class="card-title">방문지: {{ activity.place_name }}</h6>
          <h6 class="card-title">{{ formatVisitTime(activity.visit_time) }}</h6>
          <p>{{ activity.description }}</p>
          <!-- 수정/삭제 버튼 -->
          <button
            class="btn btn-outline-secondary btn-sm mt-2"
            @click="
              editActivity(
                day.visit_date,
                activity.visit_time,
                detail.plan_id,
                activity
              )
            "
          >
            수정
          </button>
          <button
            class="btn btn-outline-danger btn-sm mt-2"
            @click="deleteActivity(activity.plan_place_id)"
          >
            삭제
          </button>
          <!-- 플레이 리스트 버튼 -->
          <font-awesome-icon
            class="h-7 w-7 icons"
            icon="circle-play"
            @click="showPlaylist(activity)"
          >
          </font-awesome-icon>
        </div>
      </div>
    </div>

    <!-- 일정 추가/수정 폼 -->
    <div v-if="isEditing" class="modal-overlay" @click.self="cancelEdit">
      <div class="modal-content">
        <button class="close-button" @click="cancelEdit">×</button>
        <form @submit.prevent="save">
          <h4 v-if="editOrAdd == 1">새 활동 추가</h4>
          <h4 v-if="editOrAdd == 2">
            {{ editingActivity.visit_date }} 새 일정 추가
          </h4>
          <h4 v-if="editOrAdd == 3">활동 수정</h4>
          <div class="mb-3">
            <label for="date" class="form-label">방문 날짜</label>
            <input
              id="date"
              type="date"
              class="form-control"
              v-model="editingActivity.visit_date"
              :readonly="editOrAdd === 2"
              :class="{ 'bg-light text-muted': editOrAdd === 2 }"
              required
            />
          </div>
          <div class="mb-3">
            <label for="time" class="form-label">방문 시각</label>
            <input
              id="time"
              type="time"
              class="form-control"
              v-model="editingActivity.visit_time"
              required
            />
          </div>
          <div class="mb-3">
            <label for="title" class="form-label">방문지</label>
            <input
              id="title"
              type="text"
              class="form-control"
              v-model="editingActivity.place_name"
              required
            />
          </div>
          <div class="mb-3">
            <label for="description" class="form-label">설명</label>
            <textarea
              id="description"
              class="form-control"
              v-model="editingActivity.description"
              required
            ></textarea>
          </div>
          <div class="d-flex justify-content-end gap-2">
            <button type="submit" class="btn btn-success">저장</button>
            <button
              type="button"
              class="btn btn-outline-secondary"
              @click="cancelEdit"
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { storeToRefs } from "pinia";
import { fetchPlaylistData } from "@/api/music";

import musicRecommendModal from "@/components/musicRecommend/musicRecommendModal.vue";

const route = useRoute();
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const store = usePlanStore();
const { getPlanDetailList: detail } = storeToRefs(store);

const plan = ref(null);
const playlist = ref([]);
const isPlaylistModalVisible = ref(false); // 모달 표시 여부

onMounted(async () => {
  plan.value = {
    user_id: user.value.userId,
    plan_id: store.getPlanId,
  };
  await store.fetchPlanDetail(plan.value); // POST 요청으로 계획 목록 가져오기
});

const formatDateRange = (start, end) => {
  return `${new Date(start).toLocaleDateString()} → ${new Date(
    end
  ).toLocaleDateString()}`;
};

const formatVisitTime = (time) => {
  if (!time) return "";

  const [hour, minute] = time.split(":").map((num) => parseInt(num));
  const period = hour >= 12 ? "오후" : "오전";
  const hour12 = hour % 12 || 12;

  return `${period} ${hour12}:${minute < 10 ? "0" + minute : minute}`;
};

// const showPlaylist = async (plan_place_id) => {
//   try {
//     const data = await fetchPlaylistData(plan_place_id);
//     playlist.value = data;
//     isPlaylistModalVisible.value = true;
//   } catch (error) {
//     console.error("플레이리스트 가져오기 실패:", error.message);
//     alert("아직 등록된 음악이 없어요!");
//   }
// };
const showPlaylist = async (activity) => {
  console.log("why:", activity);

  let plan_place_id = activity.plan_place_id;
  try {
    const data = await fetchPlaylistData(plan_place_id);
    if (data.length === 0) {
      // 음악 데이터가 없으면 음악 생성 모달 표시
      showMusicCreationModal(activity);
    } else {
      playlist.value = data;
      isPlaylistModalVisible.value = true;
    }
  } catch (error) {
    console.error("플레이리스트 가져오기 실패:", error.message);
    showMusicCreationModal(activity);
  }
};

const closeRecommendationModal = () => {
  isMusicRecommendModalVisible.value = false;
};

const closePlaylistModal = () => {
  isPlaylistModalVisible.value = false;
};

const isEditing = ref(false);
const editOrAdd = ref(0);
const editingActivity = ref({
  plan_id: 0,
  plan_place_id: 0,
  visit_date: "",
  visit_time: "",
  place_name: "",
  description: "",
});

const addNewActivity = (plan_id, visit_date) => {
  isEditing.value = true;
  editingActivity.value = {
    plan_id: 0,
    plan_place_id: 0,
    visit_date: "",
    visit_time: "",
    place_name: "",
    description: "",
  };

  if (plan_id === 0) {
    editOrAdd.value = 1;
    editingActivity.value.plan_id = detail.value.plan_id;
  } else {
    editOrAdd.value = 2;
    editingActivity.value.plan_id = detail.value.plan_id;
    editingActivity.value.visit_date = visit_date;
  }
};

const editActivity = (visit_date, visit_time, plan_id, activity) => {
  isEditing.value = true;
  editOrAdd.value = 3;
  editingActivity.value = {
    plan_id: plan_id,
    plan_place_id: activity.plan_place_id,
    visit_date,
    visit_time,
    place_name: activity.place_name,
    description: activity.description,
  };
};

const save = async () => {
  if (editOrAdd.value == 1) {
    await store.addDetail(editingActivity.value, plan.value);
    isEditing.value = false;
  } else if (editOrAdd.value == 2) {
    await store.addActivity(editingActivity.value, plan.value);
    isEditing.value = false;
  } else {
    await store.updateDetail(editingActivity.value, plan.value);
    isEditing.value = false;
  }
};

const cancelEdit = () => {
  isEditing.value = false;
};

const deleteActivity = async (plan_place_id) => {
  editingActivity.value.plan_place_id = plan_place_id;
  await store.deleteDetail(editingActivity.value, plan.value);
};

// 음악 생성 모달 표시 여부
const isMusicCreationModalVisible = ref(false);

// 추천 모달 표시 여부
const isMusicRecommendModalVisible = ref(false);

const musicCreationDetails = ref({
  location: "",
  visit_time: "",
  place_name: "",
});

// 음악 생성 모달 표시 함수
const showMusicCreationModal = (activity) => {
  console.log("activity", activity);

  // plan_place_id에 맞는 데이터를 불러와 사용 가능 (여기서는 더미 값 사용)
  musicCreationDetails.value = {
    location: detail.value.title,
    visit_time: activity.visit_time,
    place_name: activity.place_name,
    plan_place_id : activity.plan_place_id
  };
  isMusicCreationModalVisible.value = true;
  console.log("truned to true :", musicCreationDetails.value);
};

// 음악 생성 모달 닫기
const closeMusicCreationModal = () => {
  isMusicCreationModalVisible.value = false;
};

const recommendationData = ref({});

// 노래 추천 실행
const musicRecommend = () => {
  const { location, visit_time, place_name, plan_place_id } = musicCreationDetails.value;
  console.log(location, visit_time, place_name, plan_place_id);

  const hour = parseInt(visit_time.split(":")[0]);

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

  // 추천된 음악 데이터를 생성 모달로 표시
  recommendationData.value = { location, mood, place_name, plan_place_id }; // 이 데이터를 추천 컴포넌트에 전달 가능
  isMusicCreationModalVisible.value = false; // 생성 모달 닫기
  isMusicRecommendModalVisible.value = true;
};
</script>

<style scoped>
/* 특정 버튼만 파란색으로 변경 */
.custom-blue-button {
  color: #007bff !important;
  /* 텍스트 색상: 파란색 */
  border-color: #007bff !important;
  /* 테두리 색상: 파란색 */
}

.custom-blue-button:hover {
  background-color: #007bff !important;
  /* 호버 시 배경색: 파란색 */
  color: #fff !important;
  /* 호버 시 텍스트 색상: 흰색 */
}

/* 모달 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.modal-content h4 {
  margin-bottom: 20px;
}

.card {
  display: block;
  /* 카드가 콘텐츠 크기에 맞게 조정되도록 설정 */
  height: auto;
  /* 기본 높이는 콘텐츠에 맞춤 */
  max-height: 500px;
  /* 최대 높이를 설정 (일정 4개 기준, 필요 시 조정 가능) */
  overflow-y: auto;
  /* 세로 스크롤 활성화 */
  margin: 0;
  /* 불필요한 외부 여백 제거 */
  padding: 0;
  /* 불필요한 내부 여백 제거 */
  border-radius: 5px;
  /* 모서리를 둥글게 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  /* 약간의 그림자 추가 */
}

/* 스크롤바 스타일 */
.card::-webkit-scrollbar {
  width: 5px;
  /* 스크롤바 너비 설정 */
}

.card::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  /* 스크롤바 색상 */
  border-radius: 10px;
  /* 스크롤바 모서리를 둥글게 */
}

.card::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0, 0, 0, 0.4);
  /* 스크롤바 hover 시 색상 */
}

.card::-webkit-scrollbar-track {
  background: transparent;
  /* 스크롤 트랙 (배경) 투명하게 */
}

.card-body {
  margin: 0;
  /* 불필요한 간격 제거 */
  padding: 10px;
  /* 콘텐츠 여백 유지 */
  border-bottom: 1px solid #ddd;
  /* 일정 간 가독성을 위한 구분선 */
  box-sizing: border-box;
  /* 패딩 포함 크기 계산 */
}

.card-body:last-child {
  border-bottom: none;
  /* 마지막 요소 구분선 제거 */
}

.itinerary-container {
  display: flex;
  flex-wrap: nowrap;
  /* 한 줄에 카드가 모두 배치되도록 설정 */
  overflow-x: auto;
  /* 가로 스크롤 가능하도록 설정 */
  gap: 15px;
  /* 카드들 간의 간격 */
  padding-bottom: 15px;
}

.itinerary-container::-webkit-scrollbar {
  height: 8px;
  /* 스크롤바 높이 */
}

.itinerary-container::-webkit-scrollbar-thumb {
  background-color: #888;
  /* 스크롤 thumb 색상 */
  border-radius: 10px;
}

.itinerary-container::-webkit-scrollbar-thumb:hover {
  background-color: #555;
  /* hover 시 색상 */
}

.card.mb-3.shadow-sm {
  flex: 0 0 calc(33.33% - 10px);
  /* 한 창에 3개씩 보이도록 너비 설정 */
  min-width: calc(33.33% - 10px);
  /* 동일한 너비 */
  max-width: calc(33.33% - 10px);
  /* 동일한 너비 */
  box-sizing: border-box;
  /* padding, border 포함 */
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
  overflow-y: auto;
  max-height: 90vh;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.recommend-container {
  margin-top: 20px;
}

.recommend-item {
  display: flex;
  margin-bottom: 15px;
}

.album-art {
  width: 60px;
  height: 60px;
  margin-right: 15px;
  border-radius: 8px;
}

.track-info {
  flex: 1;
}

.track-name {
  text-decoration: none;
  color: #333;
  font-weight: bold;
  transition: all 0.3s ease;
}

.track-name:hover {
  color: #1db954;
  text-decoration: underline;
}

.icons {
  width: 25px;
  height: auto;
}
</style>
