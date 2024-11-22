import { ref, computed, watchEffect } from "vue";
import { defineStore } from "pinia";
import {
  todaySchedule,
  plans,
  addPlan,
  deletePlan,
  getPlanDetail,
  addPlanDetail,
  addNewActivity,
  updateActivity,
  deletePlanDetail,
} from "@/api/plans";

export const usePlanStore = defineStore("plan", () => {
  // 1. data
  const todayS = ref([]); // 오�� ����
  const planList = ref([]); // 여행 계획 목록
  const planId = ref(localStorage.getItem("planId") || null);
  const planDetailList = ref(null); // 여행 계획 상세

  //2. getters
  const getTodayS = computed(() => todayS.value);
  const getPlanList = computed(() => planList.value);
  const getPlanId = computed(() => planId.value); // planId getter 추가
  const getPlanDetailList = computed(() => planDetailList.value);

  //3. actions
  const today = async (user_id) => {
    todaySchedule(
      user_id,
      (response) => {
        todayS.value = response.data.today;
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const listPlan = async (user_id) => {
    plans(
      user_id,
      (response) => {
        planList.value = []; // 초기화
        planList.value = response.data.plans; // 응답 데이터에서 plans를 추출
        //console.log("여행 계획: ", planList.value);
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
        //console.log(response.data);
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

  const deleteP = async (plan) => {
    deletePlan(
      plan,
      async (response) => {
        await listPlan(plan.user_id); // 목록 다시 가져오기
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
          planDetailList.value = []; // 초기화
          planDetailList.value = response.data; // 백엔드에서 응답 받은 데이터를 저장
          //console.log(planDetailList.value);
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

  // planId 변경 시 localStorage에 저장
  watchEffect(() => {
    if (planId.value) {
      localStorage.setItem("planId", planId.value);
    }
  });

  const addDetail = async (planDetail, plan) => {
    addPlanDetail(
      planDetail,
      async (response) => {
        // 성공 시, 응답 데이터 처리
        console.log("Plan Detail Added:");
        //planDetailList.value.push(response.data); // 상세 정보 목록에 추가
        await fetchPlanDetail(plan); // 목록 다시 가져오기
      },
      (error) => {
        // 실패 시, 에러 처리
        console.error("Failed to add plan detail:", error);
      }
    );
  };

  const addActivity = async (planDetail, plan) => {
    addNewActivity(
      planDetail,
      async (response) => {
        // 성공 시, 응답 데이터 처리
        console.log("Plan Activity Added:");
        //planDetailList.value.push(response.data); // 상세 정보 목록에 추가
        await fetchPlanDetail(plan); // 목록 다시 가져오기
      },
      (error) => {
        // 실패 시, 에러 처리
        console.error("Failed to add plan detail:", error);
      }
    );
  };

  const updateDetail = async (planDetail, plan) => {
    updateActivity(
      planDetail,
      async (response) => {
        // 성공 시, 응답 데이터 처리
        console.log("Plan Activity Updated:");
        await fetchPlanDetail(plan); // 목록 다시 가져오기
      },
      (error) => {
        // 실패 시, 에러 처리
        console.error("Failed to update plan detail:", error);
      }
    );
  };

  const deleteDetail = async (planDetail, plan) => {
    deletePlanDetail(
      planDetail,
      async (response) => {
        // 성공 시, 응답 데이터 처리
        console.log("Plan Detail Deleted:");
        //planDetailList.value.push(response.data); // 상세 정보 목록에 추가
        await fetchPlanDetail(plan); // 목록 다시 가져오기
      },
      (error) => {
        // 실패 시, 에러 처리
        console.error("Failed to add plan detail:", error);
      }
    );
  };

  return {
    todayS,
    planList,
    planId,
    planDetailList,
    getTodayS,
    getPlanList,
    getPlanId,
    getPlanDetailList,
    today,
    listPlan,
    add,
    deleteP,
    fetchPlanDetail,
    addDetail,
    addActivity,
    updateDetail,
    deleteDetail,
  };
});
