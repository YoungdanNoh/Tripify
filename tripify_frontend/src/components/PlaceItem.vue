<template>
  <li class="place-item">
    <img :src="imageSrc" alt="place image" />
    <div class="place-info">
      <h2>{{ place.title }}</h2>
      <p>{{ place.addr1 }}</p>
      <p>{{ place.addr2 }} ⭐</p>
    </div>
  </li>
</template>

<script setup>
import { defineProps, computed } from 'vue';
import defaultImage from '@/assets/noImage.png'; // 기본 이미지 파일을 import로 불러옵니다.

const props = defineProps({
  place: Object,
});

// 이미지 소스를 동적으로 결정하는 computed 속성
const imageSrc = computed(() => {
  return props.place.first_image1 || defaultImage;
});
</script>

<style scoped>
.place-item {
  display: flex;
  margin: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.place-info {
  margin-left: 10px;
}

.place-item img {
  width: 100px; /* 원하는 너비로 조정 */
  height: 100px; /* 원하는 높이로 조정 */
  object-fit: cover; /* 이미지 비율을 유지하며 크기 맞추기 */
}
</style>
