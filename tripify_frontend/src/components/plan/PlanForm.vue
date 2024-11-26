<template>
  <div class="card p-4 mb-4 shadow-sm">
    <form @submit.prevent="savePlan">
      <div class="mb-3">
        <label for="title" class="form-label">여행 이름</label>
        <input id="title" type="text" class="form-control" v-model="form.title" required />
      </div>
      <div class="mb-3">
        <label for="startDate" class="form-label">여행 시작 날짜</label>
        <input id="startDate" type="date" class="form-control" v-model="form.start_date" required />
      </div>
      <div class="mb-3">
        <label for="endDate" class="form-label">여행 종료 날짜</label>
        <input id="endDate" type="date" class="form-control" v-model="form.end_date" required />
      </div>
      <div class="mb-3">
        <label for="location" class="form-label">여행지</label>
        <input id="location" type="text" class="form-control" v-model="form.location" required />
      </div>
      <div class="mb-3">
        <label for="img" class="form-label">커버 이미지 url</label>
        <input id="img" type="text" class="form-control" v-model="form.img" />
      </div>
      <div class="d-flex justify-content-end gap-2">
        <button type="submit" class="btn btn-success">저장</button>
        <button type="button" class="btn btn-outline-secondary" @click="$emit('cancel')">취소</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, defineEmits, watch } from 'vue';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';

const props = defineProps({
  plan: Object, // 수정할 계획 데이터
});

const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const emit = defineEmits(['savePlan', 'modify']);

// 여행 기본 데이터
const form = ref({
  user_id: user.value.userId,
  title: '',
  start_date: '',
  end_date: '',
  location: '',
  img: '',
});

// watch로 plan이 변경될 때마다 form 값을 업데이트
watch(
  () => props.plan,
  (newPlan) => {
    if (newPlan) {
      form.value = { ...newPlan };
    }
  },
  { immediate: true }
);

// 저장 이벤트
const savePlan = () => {
  if (props.plan && props.plan.plan_id) {
    form.value.user_id = user.value.userId;
    emit('modify', { ...form.value });
  } else {
    emit('savePlan', { ...form.value });
  }
};
</script>
