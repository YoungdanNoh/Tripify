<template>
  <div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>내 여행</h2>
      <button class="btn btn-primary" @click="showForm = true">+ 새 여행 만들기</button>
    </div>

    <!-- 여행 추가 폼 -->
    <PlanForm v-if="showForm" @save="addTrip" @cancel="showForm = false" />

    <!-- 여행 목록 -->
    <PlanList :trips="trips" @delete="deleteTrip" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import PlanList from "@/components/plan/PlanList.vue";
import PlanForm from "@/components/plan/PlanForm.vue";

// 여행 목록 관리
const trips = ref([
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
]);

const showForm = ref(false);

// 여행 추가
const addTrip = (newTrip) => {
  trips.value.push({ ...newTrip, id: Date.now(), itinerary: [] });
  showForm.value = false;
};

// 여행 삭제
const deleteTrip = (id) => {
  trips.value = trips.value.filter((trip) => trip.id !== id);
};
</script>
