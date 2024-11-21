<template>
  <div class="card p-4 mb-4 shadow-sm">
    <form @submit.prevent="savePlan">
      <div class="mb-3">
        <label for="title" class="form-label">여행 이름</label>
        <input id="title" type="text" class="form-control" v-model="plan.title" required />
      </div>
      <div class="mb-3">
        <label for="startDate" class="form-label">여행 시작 날짜</label>
        <input id="startDate" type="date" class="form-control" v-model="plan.start_date" required />
      </div>
      <div class="mb-3">
        <label for="endDate" class="form-label">여행 종료 날짜</label>
        <input id="endDate" type="date" class="form-control" v-model="plan.end_date" required />
      </div>
      <div class="mb-3">
        <label for="location" class="form-label">여행지</label>
        <input id="location" type="text" class="form-control" v-model="plan.location" required />
      </div>
      <div class="d-flex justify-content-end gap-2">
        <button type="submit" class="btn btn-success">저장</button>
        <button type="button" class="btn btn-outline-secondary" @click="$emit('cancel')">
          취소
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, defineEmits } from "vue";
import { useUserStore } from "@/stores/user";
import { storeToRefs } from "pinia";

const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const emit = defineEmits(["savePlan"]);

// 여행 기본 데이터
const plan = ref({
  user_id: user.value.userId,
  title: "",
  start_date: "",
  end_date: "",
  location: "",
  img: "",
});

// 저장 이벤트
const savePlan = () => {
  emit("savePlan", { ...plan.value });
};
</script>
