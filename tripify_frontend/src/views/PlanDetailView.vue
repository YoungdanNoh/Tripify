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
            <p class="card-text"><i class="bi bi-geo-alt-fill"></i> {{ detail.location }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 일정 관리 -->
    <h3>일정 관리</h3>
    <div v-if="detail">
      <div v-for="day in detail.itinerary" :key="day.visit_date" class="card mb-3 shadow-sm">
        <div class="card-header">
          <h5>{{ day.visit_date }}</h5>
        </div>
        <!-- activities 순회 -->
        <div v-for="activity in day.activities" :key="activity.plan_place_id" class="card-body">
          <h6 class="card-title">{{ activity.place_name }}</h6>
          <p>{{ activity.description }}</p>
          <!-- 수정/삭제 버튼 -->
          <button
            class="btn btn-outline-secondary btn-sm mt-2"
            @click="editActivity(activity.plan_place_id)"
          >
            수정
          </button>
          <button
            class="btn btn-outline-danger btn-sm mt-2"
            @click="deleteActivity(activity.plan_place_id)"
          >
            삭제
          </button>
        </div>
      </div>
    </div>
    <!-- 일정 추가 버튼 -->
    <div class="mt-4">
      <button class="btn btn-outline-success" @click="addNewActivity">+ 새 활동 추가</button>
    </div>

    <!-- 일정 추가/수정 폼 -->
    <div v-if="isEditing" class="card p-4 my-4 shadow-sm">
      <h4>{{ editingActivityIndex !== null ? "활동 수정" : "새 활동 추가" }}</h4>
      <form @submit.prevent="saveActivity">
        <div class="mb-3">
          <label for="date" class="form-label">방문 날짜</label>
          <input id="date" type="date" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="title" class="form-label">방문지</label>
          <input
            id="title"
            type="text"
            class="form-control"
            placeholder="활동 제목을 입력하세요"
            required
          />
        </div>
        <div class="mb-3">
          <label for="description" class="form-label">설명</label>
          <textarea
            id="description"
            class="form-control"
            placeholder="활동 설명을 입력하세요"
            required
          ></textarea>
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
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { storeToRefs } from "pinia";

const route = useRoute();

const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const store = usePlanStore();
const { getPlanDetailList: detail } = storeToRefs(store);

// 날짜 포맷 함수
const formatDateRange = (start, end) => {
  return `${new Date(start).toLocaleDateString()} → ${new Date(end).toLocaleDateString()}`;
};

onMounted(async () => {
  //console.log(route.params.plan_id);
  const plan = {
    user_id: user.value.userId,
    plan_id: route.params.plan_id,
  };
  await store.fetchPlanDetail(plan); // POST 요청으로 계획 목록 가져오기
  //console.log("Plan:", detail);
  //console.log("Plan in:", detail.value.itinerary);
  //console.log("User:", getPlanList.value);
});

const isEditing = ref(false); // 수정 모드 여부
const editingActivityIndex = ref(null); // 수정할 활동 index

// 새 활동 추가 버튼 클릭
const addNewActivity = () => {
  isEditing.value = true; // 새 활동 추가
  // selectedActivity.value = null;
  // activityForm.date = "";
  // activityForm.place_name = "";
  // activityForm.description = "";
};

// 수정 버튼 클릭
const editActivity = (plan_place_id) => {
  isEditing.value = true; // 수정 모드
  editingActivityIndex.value = plan_place_id;
  // selectedActivity.value = activity;
  // activityForm.date = activity.visit_date;
  // activityForm.place_name = activity.place_name;
  // activityForm.description = activity.description;
};

// 수정 취소
const cancelEdit = () => {
  isEditing.value = false;
};

// // 임시 데이터
// const trips = [
//   {
//     id: 1,
//     title: "파리 여행",
//     startDate: "2024-11-20",
//     endDate: "2024-11-25",
//     location: "파리, 프랑스",
//     image: "https://via.placeholder.com/400x200?text=Paris",
//     itinerary: [
//       {
//         day: 1,
//         activities: [
//           {
//             title: "에펠탑 방문",
//             description: "파리의 상징 에펠탑에서 사진 촬영.",
//             locations: ["에펠탑", "샹드 마르스 공원"],
//           },
//           {
//             title: "루브르 박물관 투어",
//             description: "세계적으로 유명한 루브르 박물관 탐방.",
//             locations: ["루브르 박물관"],
//           },
//         ],
//       },
//     ],
//   },
// ];

// // 라우트에서 여행 ID 가져오기
// //const route = useRoute();
// const tripId = parseInt(route.params.plan_id, 10);
// const trip = ref(trips.find((t) => t.id === tripId));

// // 기본 이미지
// const defaultImage = "https://via.placeholder.com/400x200?text=여행+이미지";

// // 일정 추가/수정 관리
// const editingActivity = ref(null);
// //const editingActivityIndex = ref(null);
// const editingDay = ref(null);
// const newLocation = ref("");

// // // 새 활동 추가
// // const addNewActivity = (day = null) => {
// //   editingDay.value = day || trip.value.itinerary.length + 1;
// //   editingActivity.value = { title: "", description: "", locations: [], visit_date: "" };
// //   editingActivityIndex.value = null;
// // };

// // // 활동 수정
// // const editActivity = (plan_place_id) => {
// //   // editingActivity.value = { ...activity, visit_date: visitDate }; // 기존 활동 데이터 복사
// //   // editingActivityIndex.value = activity.plan_place_id; // 수정할 활동의 ID
// // };

// // 활동 저장
// const saveActivity = () => {
//   const dayIndex = editingDay.value - 1;
//   const activities = trip.value.itinerary[dayIndex]?.activities || [];

//   if (editingActivityIndex.value !== null) {
//     activities[editingActivityIndex.value] = { ...editingActivity.value };
//   } else {
//     activities.push({ ...editingActivity.value });
//     if (!trip.value.itinerary[dayIndex]) {
//       trip.value.itinerary.push({ day: editingDay.value, activities });
//     }
//   }

//   cancelEdit();
// };

// // 활동 삭제
// const deleteActivity = (day, activityIndex) => {
//   const dayIndex = day - 1;
//   trip.value.itinerary[dayIndex].activities.splice(activityIndex, 1);
// };

// // 장소 추가/삭제
// const addLocation = () => {
//   if (newLocation.value.trim()) {
//     editingActivity.value.locations.push(newLocation.value.trim());
//     newLocation.value = "";
//   }
// };
// const removeLocation = (index) => {
//   editingActivity.value.locations.splice(index, 1);
// };

// // // 수정 취소
// // const cancelEdit = () => {
// //   editingActivity.value = null;
// //   editingActivityIndex.value = null;
// //   editingDay.value = null;
// //   newLocation.value = "";
// // };
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
