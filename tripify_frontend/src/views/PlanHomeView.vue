<template>
  <div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>내 여행</h2>
      <button class="btn btn-primary" @click="showForm = true">+ 새 여행 만들기</button>
    </div>

    <!-- 여행 추가 폼 -->
    <PlanForm v-if="showForm" @savePlan="addPlan" @cancel="showForm = false" />

    <!-- 여행 목록 -->
    <PlanList :plans="getPlanList" @view="viewPlan" @delete="deletePlan" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { storeToRefs } from "pinia";
import PlanList from "@/components/plan/PlanList.vue";
import PlanForm from "@/components/plan/PlanForm.vue";

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

// 여행 추가
const addPlan = async (newPlan) => {
  //console.log("New Plan:", newPlan.title);
  //trips.value.push({ ...newTrip, id: Date.now(), itinerary: [] });
  try {
    // Pinia 스토어의 add 메서드 호출
    await store.add(newPlan);
    console.log("Plan added successfully");
  } catch (error) {
    console.error("Failed to add plan:", error);
  }

  showForm.value = false;
};

// 여행 보기 라우팅 처리
const viewPlan = (plan_id) => {
  store.planId = plan_id;
  router.push({ name: "PlanDetail", state: { plan_id } });
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
