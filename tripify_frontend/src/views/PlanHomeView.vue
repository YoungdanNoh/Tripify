<template>
  <div class="container my-5">
    <h2>내 여행</h2>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <button class="btn custom-btn w-100 btn-lg" @click="createNewPlan">+ 새 여행 만들기</button>
    </div>

    <!-- 여행 추가 모달 -->
    <div v-if="showForm" class="modal-overlay">
      <div class="modal-container">
        <div class="modal-header">
          <h5 class="modal-title">새 여행 만들기</h5>
          <button class="close-button" @click="showForm = false">&times;</button>
        </div>
        <div class="modal-body">
          <!-- PlanForm 컴포넌트 -->
          <PlanForm :plan="currentPlan" @savePlan="addPlan" @modify="modifyPlan" @cancel="showForm = false" />
        </div>
      </div>
    </div>

    <!-- 여행 목록 -->
    <PlanList :plans="getPlanList" @view="viewPlan" @modify="modifyForm" @delete="deletePlan" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { usePlanStore } from '@/stores/plans';
import { storeToRefs } from 'pinia';
import PlanList from '@/components/plan/PlanList.vue';
import PlanForm from '@/components/plan/PlanForm.vue';

const userStore = useUserStore();
const { user } = storeToRefs(userStore);

const store = usePlanStore();
const { getPlanList } = storeToRefs(store);

const router = useRouter();

onMounted(async () => {
  await store.listPlan(user.value.userId); // POST 요청으로 계획 목록 가져오기
  //console.log("User:", getPlanList.value);
});
//console.log("User:", user.value.userId);

const showForm = ref(false);
const currentPlan = ref(null); // 현재 수정 중인 여행 계획

// 새 여행 만들기
const createNewPlan = () => {
  currentPlan.value = null; // 새로운 여행을 만들기 위해 currentPlan을 null로 설정
  showForm.value = true; // 여행 만들기 모달 표시
};

// 여행 추가
const addPlan = async (newPlan) => {
  //console.log("New Plan:", newPlan.title);
  //trips.value.push({ ...newTrip, id: Date.now(), itinerary: [] });
  try {
    // Pinia 스토어의 add 메서드 호출
    await store.add(newPlan);
    console.log('Plan added successfully');
  } catch (error) {
    console.error('Failed to add plan:', error);
  }

  showForm.value = false;
};

// 여행 보기 라우팅 처리
const viewPlan = (plan_id) => {
  store.planId = plan_id;
  router.push({ name: 'PlanDetail', state: { plan_id } });
};

// 여행 수정
const modifyForm = async (plan_id, start_date, end_date, title, location, img) => {
  console.log('Modify Plan:', plan_id);
  currentPlan.value = { plan_id, start_date, end_date, title, location, img };
  showForm.value = true;

  // try {
  //   // Pinia 스토어의 add 메서드 호출
  //   await store.modify(newPlan);
  //   console.log("Plan modify successfully");
  // } catch (error) {
  //   console.error("Failed to modify plan:", error);
  // }

  // showForm.value = false;
};

const modifyPlan = async (newPlan) => {
  try {
    // Pinia 스토어의 add 메서드 호출
    console.log('New Plan:', newPlan.img);
    await store.modify(newPlan);
    console.log('Plan modify successfully');
  } catch (error) {
    console.error('Failed to modify plan:', error);
  }

  showForm.value = false;
};

// 여행 삭제
const deletePlan = async (plan_id) => {
  const plan = ref({
    plan_id: plan_id,
    user_id: user.value.userId,
  });

  await store.deleteP(plan.value);
  //console.log("Delete Plan:", plan.value);
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  background: white;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  animation: fadeIn 0.3s ease;
  overflow: hidden;
}

.modal-header {
  padding: 1rem;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title {
  margin: 0;
  font-size: 1.25rem;
}

.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.modal-body {
  padding: 1rem;
}

/* 버튼을 전체 너비로 확장 */
.w-100 {
  width: 100% !important;
}

/* 버튼 높이를 120px로 설정 */
.btn-lg {
  height: 100px; /* 버튼의 높이를 두 배로 설정 */
  font-size: 1.5rem; /* 글자 크기 적당히 증가 */
}

.custom-btn {
  background-color: transparent; /* 배경색 투명 */
  border: 2px solid #888; /* 회색 테두리 */
  color: #888; /* 글자색 회색 */
  box-shadow: none; /* 그림자 제거 */
  transition: all 0.3s ease; /* 배경색과 테두리 색상에 대한 전환 효과 */
}

/* 버튼에 hover 효과 추가 */
.custom-btn:hover {
  background-color: #f5f5f5; /* 마우스 오버 시 배경색을 밝은 회색으로 */
  border-color: #444; /* 테두리 색상 어두운 회색으로 변경 */
  color: #444; /* 글자색을 어두운 회색으로 변경 */
}
</style>
