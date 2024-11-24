<template>
  <div class="banner-content">
    <h1>당신의 다음 여행을 계획하세요</h1>
    <div class="search-bar">
      <div class="input-group">
        <input
          type="text"
          class="form-control"
          placeholder="도시, 호텔, 관광지 검색"
          v-model="searchKeyword"
        />
        <button class="btn btn-primary" @click="performSearch">검색</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import { useRouter } from "vue-router";

// 상태 변수 선언
const searchKeyword = ref("");
const userStore = useUserStore();
const user = computed(() => userStore.user); // 로그인 상태 확인

const store = usePlanStore(); //현재 일정 확인

onMounted(async () => {
  await store.today(user.value.userId);
});

// Vue Router 훅 사용
const router = useRouter();

// 검색 실행 함수
const performSearch = () => {
  if (searchKeyword.value.trim() === "") {
    alert("검색어를 입력해주세요!");
    return;
  }
  router.push({ name: "place", params: { keyword: searchKeyword.value } });
};
</script>

<style scoped>
.banner-content h1 {
  margin: 0;
  padding: 0;
  font-size: 2rem; /* 기본 글자 크기 */
  text-align: center;
  line-height: 1.2;
}

.search-bar {
  width: 100%;
  max-width: 600px;
  margin-top: 20px;
}

.search-bar input,
.search-bar button {
  height: 50px;
  border-radius: 0;
}

.banner-content h1 {
  text-shadow: 2px 2px 15px rgba(0, 0, 0, 1); /* 검은색 그림자를 추가 */
  color: aliceblue;
  font-weight: bold;
}


@media (max-width: 768px) {
  .main-banner {
    padding: 20px;
  }

  .search-bar {
    max-width: 100%;
  }

  .banner-content h1 {
    font-size: 2rem; /* 모바일 화면에서 글자 크기 감소 */
  }
}

@media (max-width: 1000px) {
  .banner-content h1 {
    font-size: 1.25rem; /* 600px 이하에서 더 작은 글자 크기로 조정 */
  }
}
</style>
