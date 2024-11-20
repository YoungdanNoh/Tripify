import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { plans, addPlan, getPlanDetail } from "@/api/plans";

export const usePlanStore = defineStore("plan", () => {
  // 1. data
  const plansList = ref([]); // 여행 계획 목록
  const planDetailList = ref(null); // 여행 계획 상세

  //2. getters
  const getPlanList = computed(() => plansList.value);
  const getPlanDetailList = computed(() => planDetailList.value);

  //3. actions
  const listPlan = async (user_id) => {
    plans(
      user_id,
      (response) => {
        plansList.value = []; // 초기화
        plansList.value = response.data.plans; // 응답 데이터에서 plans를 추출
        console.log("여행 계획: ", plansList.value);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const add = async (plan) => {
    addPlan(
      plan,
      async (response) => {
        console.log(response.data);
        await listPlan(plan.user_id); // 목록 다시 가져오기
        //console.log("plansList: ", plansList.value);
        //console.log("plan: ", plan);
        //plansList.value.push(plan);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const fetchPlanDetail = async (plan) => {
    try {
      await getPlanDetail(
        plan,
        (response) => {
          planDetailList.value = response.data; // 백엔드에서 응답 받은 데이터를 저장
          console.log("Fetched Plan Detail");
        },
        (error) => {
          console.error("Failed to fetch plan detail:", error);
        }
      );
    } catch (error) {
      console.error("Error in fetchPlanDetail:", error);
    }
  };

  return {
    plansList,
    planDetailList,
    getPlanList,
    getPlanDetailList,
    listPlan,
    add,
    fetchPlanDetail,
  };
});
