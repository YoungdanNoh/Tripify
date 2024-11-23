<template>
  <li class="place-item" @mouseenter="highlightPlace"
    @mouseleave="unhighlightPlace">
    <img :src="imageSrc" alt="place image" />
    <div class="place-info">
      <div class="place-title-container">
        <p class="place-title">{{ place.title }}</p>
        <!-- 좋아요 아이콘 -->
        <p
          class="place-heart"
          :class="{ liked: isLiked }"
          @click="toggleLike($event)"
        >
          <span v-if="isLiked">♥</span>
          <span v-else>♡</span>
        </p>
      </div>
      <hr />
      <p>{{ place.addr1 }} {{ place.addr2 }}</p>
    </div>
  </li>
</template>


<script setup>
import { defineProps, computed, ref, watch } from "vue";
import { useUserStore } from "@/stores/user"; // userStore import
import { usePlaceStore } from "@/stores/place"; // placeStore import

import defaultImage from "@/assets/noImage.png"; // 기본 이미지 파일 import

// Pinia store 가져오기
const userStore = useUserStore();
const placeStore = usePlaceStore();

// Props 정의
const props = defineProps({
  place: Object, // 장소 객체
});

// 사용자 ID 가져오기
const userId = computed(() => userStore.userId);

// 좋아요 상태 관리
const isLiked = ref(props.place.likeStatus === "true");

// props와 isLiked 동기화
watch(
  () => props.place.likeStatus,
  (newVal) => (isLiked.value = newVal === "true")
);

watch(
  () => isLiked.value,
  (newVal) => (props.place.likeStatus = newVal ? "true" : "false")
);

// 이미지 소스 결정
const imageSrc = computed(() => props.place.first_image1 || defaultImage);

// 좋아요 토글 함수
const toggleLike = async (event) => {
  event.stopPropagation(); // 이벤트 전파 중단
  console.log("kkkk",props.place);
  
  try {
    if (isLiked.value) {
      // 좋아요 해제
      await placeStore.removeLikePlace(props.place.place_id, userStore.getUserId());
      isLiked.value = false;
    } else {
      // 좋아요 추가
      await placeStore.addLikePlace(props.place.place_id, userStore.getUserId());
      isLiked.value = true;
    }
  } catch (error) {
    console.error("좋아요 처리 중 오류 발생:", error);
  }
};

function highlightPlace() {
  placeStore.setHighlightedPlaceId(props.place.place_id);
}

function unhighlightPlace() {
  placeStore.setHighlightedPlaceId(null);
}
</script>

<style scoped>
.place-item {
  display: flex;
  margin: 10px;
  padding: 10px;
  border: 2px solid #ddd; /* 기본 상태에서도 두꺼운 테두리 */
  border-radius: 8px;
  transition: all 0.3s ease; /* 부드러운 애니메이션 효과 */
  cursor: pointer;

}

.place-item:hover {
  border-color: #007bff; /* 테두리 색상만 변경 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
}

.place-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.place-info {
  margin-left: 10px;
  flex: 1;
}

.place-info .place-title-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: -20px;
}

.place-info .place-title {
  font-weight: bold;
  font-size: large;
  margin-right: 5px;
}

.place-info .place-heart {
  font-size: 1.3rem; /* 하트 크기 조정 */
  cursor: pointer;
  transition: transform 0.2s ease, color 0.3s ease;
  color: #ddd;
}

.place-info .place-heart.liked {
  color: red; /* 좋아요가 눌렸을 때 빨간색 */
  transform: scale(1.1); /* 눌렀을 때 약간 확대 */
}

.place-info .place-heart:hover {
  color: #ff6b6b; /* 호버 시 연한 빨간색 */
}
</style>
