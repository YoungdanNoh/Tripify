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
          <h6 class="card-title">방문지: {{ activity.place_name }}</h6>
          <p>{{ activity.description }}</p>
          <!-- 수정/삭제 버튼 -->
          <button
            class="btn btn-outline-secondary btn-sm mt-2"
            @click="editActivity(day.visit_date, detail.plan_id, activity)"
          >
            수정
          </button>
          <button
            class="btn btn-outline-danger btn-sm mt-2"
            @click="deleteActivity(activity.plan_place_id)"
          >
            삭제
          </button>
          <!-- 일정 추가 버튼 -->
          <div class="mt-4">
            <button
              class="btn btn-outline-success"
              @click="addNewActivity(detail.plan_id, activity, day.visit_date)"
            >
              + 새 활동 추가
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 일정 추가 버튼 -->
    <div class="mt-4">
      <button class="btn btn-outline-success" @click="addNewActivity(0, null, null)">
        + 새 활동 추가
      </button>
    </div>

    <!-- 일정 추가/수정 폼 -->
    <div v-if="isEditing" class="card p-4 my-4 shadow-sm">
      <!-- <h4>{{ editOrAdd ? "활동 수정" : "새 활동 추가" }}</h4> -->
      <div v-if="editOrAdd == 1"><h4>새 활동 추가</h4></div>
      <div v-if="editOrAdd == 2">
        <h4>{{ editingActivity.visit_date }} 새 활동 추가</h4>
      </div>
      <div v-if="editOrAdd == 3"><h4>활동 수정</h4></div>
      <form @submit.prevent="saveActivity">
        <div class="mb-3">
          <label for="date" class="form-label">방문 날짜</label>
          <input
            id="date"
            type="date"
            class="form-control"
            v-model="editingActivity.visit_date"
            :readonly="editOrAdd === 2"
            :class="{ 'bg-light text-muted': editOrAdd === 2 }"
            required
          />
        </div>
        <div class="mb-3">
          <label for="title" class="form-label">방문지</label>
          <input
            id="title"
            type="text"
            class="form-control"
            v-model="editingActivity.place_name"
            placeholder="활동 제목을 입력하세요"
            required
          />
        </div>
        <div class="mb-3">
          <label for="description" class="form-label">설명</label>
          <textarea
            id="description"
            class="form-control"
            v-model="editingActivity.description"
            placeholder="활동 설명을 입력하세요"
            required
          ></textarea>
        </div>
        <div class="d-flex justify-content-end gap-2">
          <button type="submit" class="btn btn-success" @click="save">저장</button>
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

const plan = ref(null);

onMounted(async () => {
  //console.log(route.params.plan_id);
  plan.value = {
    user_id: user.value.userId,
    plan_id: store.getPlanId,
  };
  await store.fetchPlanDetail(plan.value); // POST 요청으로 계획 목록 가져오기
});

// 날짜 포맷 함수
const formatDateRange = (start, end) => {
  return `${new Date(start).toLocaleDateString()} → ${new Date(end).toLocaleDateString()}`;
};

const isEditing = ref(false); // 수정 모드 여부
const editOrAdd = ref(0); // 수정할 활동 index

const editingActivity = ref({
  plan_id: 0,
  plan_place_id: 0,
  visit_date: "",
  place_name: "",
  description: "",
  order_in_day: 0,
}); // 수정할 활동 데이터 저장

const origin_order = ref(0);

// 새 활동 추가 버튼 클릭
const addNewActivity = (plan_id, activity, visit_date) => {
  isEditing.value = true; // 새 활동 추가
  editingActivity.value = {
    plan_id: 0,
    plan_place_id: 0,
    visit_date: "",
    place_name: "",
    description: "",
    order_in_day: 0,
  };

  if (plan_id === 0) {
    //아예 새로운 활동을 추가
    //order_in_day + 1을 전송한다.
    editOrAdd.value = 1;
    //origin_order.value = 1;

    editingActivity.value.plan_id = detail.value.plan_id;
    editingActivity.value.order_in_day = 1;
  } else {
    //기존 활동 사이에 새로운 활동 추가
    //order_in_day + 1을 전송한다.
    editOrAdd.value = 2;
    origin_order.value = activity.order_in_day;

    editingActivity.value.plan_id = detail.value.plan_id;
    editingActivity.value.plan_place_id = activity.plan_place_id;
    editingActivity.value.visit_date = visit_date;
    editingActivity.value.order_in_day = activity.order_in_day + 1;
  }
};

// 수정 버튼 클릭
const editActivity = (visit_date, plan_id, activity) => {
  isEditing.value = true; // 수정 모드
  editOrAdd.value = 3;
  console.log("editActivity", plan_id);
  editingActivity.value = {
    plan_id: plan_id,
    plan_place_id: activity.plan_place_id,
    visit_date,
    place_name: activity.place_name,
    description: activity.description,
    order_in_day: activity.order_in_day,
  };

  origin_order.value = activity.order_in_day;
};

// 저장 버튼 클릭
// 수정 또는 새 활동 추가 후 저장 -> DB에 반영
const save = async () => {
  //console.log(editingActivityId.value);
  if (editOrAdd.value == 1) {
    // 아예 새로운 활동을 추가한다.
    // 전송할 데이터: editingActivity
    console.log("아예 새로운 활동을 추가한다.");

    await store.addDetail(editingActivity.value, plan.value); // POST 요청으로 활동 추가
    isEditing.value = false;
  } else if (origin_order.value < editingActivity.value.order_in_day) {
    // 기존 날짜에 새로운 활동을 추가한다.
    console.log("기존 날짜에 새로운 활동을 추가한다.");

    await store.addActivity(editingActivity.value, plan.value); // POST 요청으로 활동 추가
    isEditing.value = false;
  } else {
    // 기존 활동을 수정한다.
    console.log("기존 활동을 수정한다.");

    await store.updateDetail(editingActivity.value, plan.value); // POST 요청으로 활동 추가
    isEditing.value = false;
  }
};

// 수정 취소
const cancelEdit = () => {
  isEditing.value = false;
};

const deleteActivity = async (plan_place_id) => {
  //plan_place_id에 해당되는 활동을 삭제한다.
  editingActivity.value.plan_place_id = plan_place_id;

  await store.deleteDetail(editingActivity.value, plan.value); // 활동 삭제
};
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
