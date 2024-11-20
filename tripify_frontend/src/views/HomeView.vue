<template>
  <div>
    <!-- 메인 배너 -->
    <div class="main-banner">
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

    <!-- 인기 관광지 섹션 -->
    <PopularDestinations />

    <!-- 가장 많이 조회된 장소 섹션 -->
    <MostViewed />

    <!-- 커뮤니티 게시판 섹션 -->
    <CommunityBoard />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import PopularDestinations from "@/components/PopularDestinations.vue";
import MostViewed from "@/components/MostViewed.vue";
import CommunityBoard from "@/components/Board/CommunityBoard.vue";

// 상태 변수 선언
const searchKeyword = ref("");

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
.main-banner {
  background-image: url("https://www.balpumnews.com/wp-content/uploads/2024/03/3087_16272_5619.jpg");
  background-size: cover;
  background-position: center;
  height: 500px;
  color: white;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.search-bar {
  width: 60%;
  max-width: 600px;
  margin: 20px auto;
}
.search-bar input {
  height: 50px;
  border-radius: 0;
}
.search-bar button {
  height: 50px;
}
</style>
