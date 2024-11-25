<template>
  <div class="trip-list">
    <div v-for="plan in plans" :key="plan.plan_id" class="card mb-3 shadow-sm">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="plan.img || noImage"
            class="img-fluid rounded-start"
            alt="여행 이미지"
            style="object-fit: cover; height: 100%"
          />
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ plan.title }}</h5>
            <p class="card-text">
              <small class="text-muted">{{
                formatDateRange(plan.start_date, plan.end_date)
              }}</small>
            </p>
            <p class="card-text"><i class="bi bi-geo-alt-fill"></i>여행지: {{ plan.location }}</p>
            <div class="d-flex justify-content-end gap-2">
              <button class="btn btn-outline-primary btn-sm" @click="$emit('view', plan.plan_id)">
                보기
              </button>
              <button
                class="btn btn-outline-secondary btn-sm"
                @click="
                  $emit(
                    'modify',
                    plan.plan_id,
                    plan.start_date,
                    plan.end_date,
                    plan.title,
                    plan.location
                  )
                "
              >
                수정
              </button>
              <button class="btn btn-outline-danger btn-sm" @click="$emit('delete', plan.plan_id)">
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
import noImage from "@/assets/noImage.png"; // 기본 이미지 가져오기
const props = defineProps({
  plans: {
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
