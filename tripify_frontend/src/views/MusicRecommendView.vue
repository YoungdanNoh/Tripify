<template>
  <div class="container">
    <SearchComponent class="search-component"></SearchComponent>
    <recommendation class="recommendation"></recommendation>
  </div>
</template>

<script setup>
import SearchComponent from "@/components/musicRecommend/SearchComponent.vue";
import recommendation from "@/components/musicRecommend/recommendation.vue";
</script>

<style scoped>
.container {
  display: flex;
  justify-content: flex-start; /* 자식 요소들을 왼쪽으로 정렬 */
  align-items: flex-start; /* 자식 요소들을 위쪽으로 정렬 */
  gap: 50px; /* 두 컴포넌트 사이에 40px 간격 추가 */
}

.recommendation {
  width: 600px; /* recommendation의 고정 너비 설정 */
}
</style>
