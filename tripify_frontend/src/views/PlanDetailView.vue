<template>
  <div class="container my-5">
    <!-- 여행 상세 정보 -->
    <div v-if="trip" class="card mb-4 shadow-sm">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="trip.image || defaultImage"
            class="img-fluid rounded-start"
            alt="여행 이미지"
            style="object-fit: cover; height: 100%"
          />
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ trip.title }}</h5>
            <p class="card-text">
              <small class="text-muted">{{ formatDateRange(trip.startDate, trip.endDate) }}</small>
            </p>
            <p class="card-text"><i class="bi bi-geo-alt-fill"></i> {{ trip.location }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 일정 관리 -->
    <h3>일정 관리</h3>
    <div v-if="trip.itinerary.length" class="itinerary">
      <div v-for="(day, index) in trip.itinerary" :key="index" class="card mb-3 shadow-sm">
        <div class="card-body">
          <h5 class="card-title">Day {{ index + 1 }}: {{ day.title }}</h5>
          <p>{{ day.description }}</p>
          <ul>
            <li v-for="(location, locIndex) in day.locations" :key="locIndex">
              <i class="bi bi-geo-alt-fill"></i> {{ location }}
            </li>
          </ul>
          <button class="btn btn-outline-secondary btn-sm mt-2" @click="editDay(index)">
            수정
          </button>
          <button class="btn btn-outline-danger btn-sm mt-2" @click="deleteDay(index)">삭제</button>
        </div>
      </div>
    </div>
    <div v-else class="text-muted">일정이 없습니다. 새 일정을 추가하세요!</div>

    <!-- 일정 추가 버튼 -->
    <div class="mt-4">
      <button class="btn btn-outline-success" @click="addNewDay">+ 새 일정 추가</button>
    </div>

    <!-- 일정 추가/수정 폼 -->
    <div v-if="editingDay !== null" class="card p-4 my-4 shadow-sm">
      <h4>{{ editingDayIndex !== null ? "일정 수정" : "새 일정 추가" }}</h4>
      <form @submit.prevent="saveDay">
        <div class="mb-3">
          <label for="title" class="form-label">제목</label>
          <input
            id="title"
            type="text"
            class="form-control"
            v-model="editingDay.title"
            placeholder="일정 제목을 입력하세요"
            required
          />
        </div>
        <div class="mb-3">
          <label for="description" class="form-label">설명</label>
          <textarea
            id="description"
            class="form-control"
            v-model="editingDay.description"
            placeholder="일정 설명을 입력하세요"
            required
          ></textarea>
        </div>
        <div class="mb-3">
          <label for="locations" class="form-label">장소</label>
          <input
            id="locations"
            type="text"
            class="form-control"
            v-model="newLocation"
            placeholder="장소를 입력하고 Enter를 눌러 추가하세요"
            @keyup.enter.prevent="addLocation"
          />
          <ul class="mt-2">
            <li v-for="(location, locIndex) in editingDay.locations" :key="locIndex">
              {{ location }}
              <button
                type="button"
                class="btn-close ms-2"
                aria-label="Remove"
                @click="removeLocation(locIndex)"
              ></button>
            </li>
          </ul>
        </div>
        <div class="d-flex justify-content-end gap-2">
          <button type="submit" class="btn btn-success">저장</button>
          <button type="button" class="btn btn-outline-secondary" @click="cancelEdit">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";

// 임시 데이터
const trips = [
  {
    id: 1,
    title: "파리 여행",
    startDate: "2024-11-20",
    endDate: "2024-11-25",
    location: "파리, 프랑스",
    image: "https://via.placeholder.com/400x200?text=Paris",
    itinerary: [
      {
        title: "에펠탑 방문",
        description: "파리의 상징 에펠탑에서 사진 촬영.",
        locations: ["에펠탑", "샹드 마르스 공원"],
      },
    ],
  },
];

// 라우트에서 여행 ID 가져오기
const route = useRoute();
const tripId = parseInt(route.params.id, 10);
const trip = ref(trips.find((t) => t.id === tripId));

// 기본 이미지
const defaultImage = "https://via.placeholder.com/400x200?text=여행+이미지";

// 날짜 포맷 함수
const formatDateRange = (start, end) => {
  return `${new Date(start).toLocaleDateString()} → ${new Date(end).toLocaleDateString()}`;
};

// 일정 추가/수정 관리
const editingDay = ref(null);
const editingDayIndex = ref(null);
const newLocation = ref("");

// 새 일정 추가
const addNewDay = () => {
  editingDay.value = { title: "", description: "", locations: [] };
  editingDayIndex.value = null;
};

// 일정 수정
const editDay = (index) => {
  editingDay.value = { ...trip.value.itinerary[index] };
  editingDayIndex.value = index;
};

// 일정 저장
const saveDay = () => {
  if (editingDayIndex.value !== null) {
    trip.value.itinerary[editingDayIndex.value] = { ...editingDay.value };
  } else {
    trip.value.itinerary.push({ ...editingDay.value });
  }
  cancelEdit();
};

// 일정 삭제
const deleteDay = (index) => {
  trip.value.itinerary.splice(index, 1);
};

// 장소 추가/삭제
const addLocation = () => {
  if (newLocation.value.trim()) {
    editingDay.value.locations.push(newLocation.value.trim());
    newLocation.value = "";
  }
};
const removeLocation = (index) => {
  editingDay.value.locations.splice(index, 1);
};

// 수정 취소
const cancelEdit = () => {
  editingDay.value = null;
  editingDayIndex.value = null;
  newLocation.value = "";
};
</script>

<style scoped>
.card ul {
  list-style: none;
  padding: 0;
}
.card ul li {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}
</style>
