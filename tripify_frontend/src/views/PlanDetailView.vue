<template>
  <div class="container my-5">
    <!-- 여행 상세 정보 -->
    <div v-if="detail" class="card mb-4 shadow-sm">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="detail.img || noImage"
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
            <p class="card-text"><i class="bi bi-geo-alt-fill"></i> {{ detail.location }}</p>
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
    <div v-if="isPlaylistModalVisible" class="modal-overlay" @click.self="closePlaylistModal">
      <div class="modal-content">
        <button class="close-button" @click="closePlaylistModal">×</button>
        <div class="recommend-container" v-if="playlist.length">
          <h3>추천 음악</h3>
          <div v-for="(item, index) in playlist" :key="index" class="recommend-item">
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
          {{ musicCreationDetails.place_name }}에서 방문한 시간과 장소에 맞는 음악을 추천해
          드립니다.
        </p>
        <div class="modal-buttons">
          <button class="btn btn-outline-secondary" @click="closeMusicCreationModal">닫기</button>
          <button class="btn btn-success" @click="musicRecommend">지금 바로 생성하기</button>
        </div>
      </div>
    </div>

    <musicRecommendModal
      v-if="isMusicRecommendModalVisible"
      :data="recommendationData"
      @close="closeRecommendationModal"
    >
    </musicRecommendModal>

    <!-- 일정 관리 -->
    <div class="d-flex justify-content-between align-items-center">
      <h3>일정 관리</h3>
      <!-- 일정 추가 버튼 -->
      <button class="btn btn-outline-success custom-blue-button" @click="addNewActivity(0, null)">
        + 새 활동 추가
      </button>
    </div>

    <!-- 일정 박스들을 가로로 나열 -->
    <div
      v-if="detail"
      class="itinerary-container"
      @mousedown="startDrag"
      @mousemove="onDrag"
      @mouseup="stopDrag"
      @mouseleave="stopDrag"
    >
      <div v-for="day in detail.itinerary" :key="day.visit_date" class="card day-card shadow-sm">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h5>{{ day.visit_date }}</h5>
        </div>
        <div class="card-body">
          <div class="timeline">
            <div
              v-for="activity in day.activities"
              :key="activity.plan_place_id"
              class="timeline-item"
            >
              <div class="timeline-dot"></div>
              <div class="timeline-content">
                <h6 class="time">{{ formatVisitTime(activity.visit_time) }}</h6>
                <h6 class="place-name">방문지: {{ activity.place_name }}</h6>
                <p>{{ activity.description }}</p>
                <div class="action-buttons">
                  <button
                    class="btn btn-outline-secondary btn-sm"
                    @click="
                      editActivity(day.visit_date, activity.visit_time, detail.plan_id, activity)
                    "
                  >
                    수정
                  </button>
                  <button
                    class="btn btn-outline-danger btn-sm"
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
            <button
              class="btn btn-outline-success"
              @click="addNewActivity(detail.plan_id, day.visit_date)"
            >
              + 새 일정 추가
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 일정 추가/수정 폼 -->
    <div v-if="isEditing" class="modal-overlay" @click.self="cancelEdit">
      <div class="modal-content">
        <button class="close-button" @click="cancelEdit">×</button>
        <form @submit.prevent="save">
          <h4 v-if="editOrAdd == 1">새 활동 추가</h4>
          <h4 v-if="editOrAdd == 2">{{ editingActivity.visit_date }} 새 일정 추가</h4>
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
            <button type="button" class="btn btn-outline-secondary" @click="cancelEdit">
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import noImage from "@/assets/noImage.png"; // 기본 이미지 가져오기
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
  return `${new Date(start).toLocaleDateString()} → ${new Date(end).toLocaleDateString()}`;
};

const formatVisitTime = (time) => {
  if (!time) return "";

  const [hour, minute] = time.split(":").map((num) => parseInt(num));

  const period = hour >= 12 ? "오후" : "오전";
  const hour12 = hour % 12 || 12; // 24시간제를 12시간제로 변환 (0시 -> 12시, 15시 -> 3시 등)

  return `${period} ${hour12}:${minute < 10 ? "0" + minute : minute}`; // "오후 3:00" 형식으로 반환
};

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
}); // 수정할 활동 데이터 저장

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
    plan_place_id: activity.plan_place_id,
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

/*마우스 드래그로 가로 스크롤 작동시키기*/
// 드래그 상태와 위치를 추적하기 위한 변수
const isDragging = ref(false);
const startX = ref(0);
const scrollLeft = ref(0);

const startDrag = (e) => {
  isDragging.value = true; // 드래그 시작
  startX.value = e.pageX - e.currentTarget.offsetLeft; // 클릭 위치 저장
  scrollLeft.value = e.currentTarget.scrollLeft; // 현재 스크롤 위치 저장
};

const onDrag = (e) => {
  if (!isDragging.value) return; // 드래그 중이 아니면 무시
  e.preventDefault(); // 선택 방지
  const x = e.pageX - e.currentTarget.offsetLeft; // 현재 마우스 위치
  const walk = x - startX.value; // 이동 거리 계산
  e.currentTarget.scrollLeft = scrollLeft.value - walk; // 스크롤 이동
};

const stopDrag = () => {
  isDragging.value = false; // 드래그 종료
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

.card-body {
  padding: 10px;
  /* 카드 내부 콘텐츠 여백 */
  border-bottom: 1px solid #ddd;
  /* 카드 내부 구분선 */
  box-sizing: border-box;
  /* 패딩 포함 크기 계산 */
  max-height: 400px;
  /* 세로 스크롤 높이 제한 */
  overflow-y: auto;
  /* 세로 스크롤 활성화 */
}

/* 스크롤바 스타일 */
.card-body::-webkit-scrollbar {
  width: 8px;
  /* 스크롤바 너비 */
}

.card-body::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  /* 스크롤바 색상 */
  border-radius: 10px;
  /* 스크롤바 둥근 모양 */
}

.card-body::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0, 0, 0, 0.4);
  /* hover 시 색상 */
}

.card-body::-webkit-scrollbar-track {
  background: transparent;
  /* 스크롤 트랙 투명하게 */
}

.itinerary-container {
  max-height: 600px;
  overflow-y: auto;
  display: flex;
  flex-wrap: nowrap;
  /* 한 줄에 카드가 모두 배치되도록 설정 */
  overflow-x: auto;
  /* 가로 스크롤 가능하도록 설정 */
  gap: 15px;
  /* 카드들 간의 간격 */
  padding-bottom: 15px;
  cursor: grab;
  /* 기본 커서 스타일 */
}

.itinerary-container:active {
  cursor: grabbing;
  /* 드래그 중 커서 스타일 */
}

.day-card {
  flex: 0 0 300px;
  min-width: 300px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.day-card .card-header {
  background-color: #f8f9fa;
  font-weight: bold;
  text-align: center;
}

.timeline {
  position: relative;
  padding: 15px 20px;
}

.timeline-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
  position: relative;
}

.timeline-item:not(:last-child)::after {
  content: "";
  position: absolute;
  top: 30px;
  /* 점 아래쪽에서 선이 시작되도록 조정 */
  left: 8px;
  /* 선이 점의 중앙을 지나도록 설정 */
  width: 2px;
  height: calc(100% - 20px);
  /* 선 길이를 점과 점 사이로 제한 */
  background-color: #6c757d;
}

.timeline-dot {
  width: 21px;
  height: 21px;
  background-color: #343a40;
  border-radius: 50%;
  position: relative;
  z-index: 1;
  margin-left: -1px;
  /* 점이 선의 가운데 정렬되도록 설정 */
}

.timeline-content {
  margin-left: 20px;
  /* 점과 콘텐츠 간의 간격 */
  position: relative;
}

.timeline-content .time {
  font-size: 14px;
  color: #6c757d;
}

.timeline-content .place-name {
  font-weight: bold;
}

.timeline-content .action-buttons {
  display: flex;
  gap: 5px;
  margin-top: 10px;
}

.timeline-content p {
  margin: 5px 0;
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
