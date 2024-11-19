<template>
  <div class="trip-list">
    <div v-for="trip in trips" :key="trip.id" class="card mb-3 shadow-sm">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="trip.image"
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
            <div class="d-flex justify-content-end gap-2">
              <router-link :to="`/PlanDetail/${trip.id}`" class="btn btn-outline-primary btn-sm"
                >보기</router-link
              >
              <button class="btn btn-outline-danger btn-sm" @click="$emit('delete', trip.id)">
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  trips: {
    type: Array,
    required: true,
  },
});

// 날짜 포맷 함수
const formatDateRange = (start, end) =>
  `${new Date(start).toLocaleDateString()} → ${new Date(end).toLocaleDateString()}`;
</script>

<style scoped>
.card img {
  height: 150px;
  object-fit: cover;
}
</style>
