<template>
  <div class="main-container">
    <div class="main-banner">
      <div class="background-image" :style="backgroundStyle"></div>
      <!-- 배너 콘텐츠 -->
      <div class="banner-contentbox container">
        <SearchBar />
        <ProfileSection />
      </div>
    </div>
    <div class="second-main-container container">
      <!-- 메인 배너 -->
      <!-- 기타 컴포넌트 -->
      <div class="second-section row">
        <PopularDestinations />
      </div>
      <div class="third-section">
        <MostViewed class="sub-section" />
        <CommunityBoard class="sub-section" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useUserStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plans";
import PopularDestinations from "@/components/PopularDestinations.vue";
import MostViewed from "@/components/MostViewed.vue";
import CommunityBoard from "@/components/Board/CommunityBoard.vue";
import ProfileSection from "@/components/ProfileSection.vue";
import SearchBar from "@/components/SearchBar.vue";

const userStore = useUserStore();
const user = computed(() => userStore.user);
const store = usePlanStore();

const images = ref([
  "src/assets/pic/yeosu.jpg",
  "src/assets/pic/paris1.jpg",
  "src/assets/pic/murano.jpg",
]);
let currentImageIndex = ref(0);
let opacity = ref(1);

const changeImage = () => {
  opacity.value = 0; // 페이드 아웃
  setTimeout(() => {
    currentImageIndex.value =
      (currentImageIndex.value + 1) % images.value.length;
    opacity.value = 1; // 페이드 인
  }, 1000); // 1초 후 이미지 변경
};

onMounted(() => {
  store.today(user.value?.userId);
  setInterval(changeImage, 8000); // 8초마다 이미지 변경
});

const backgroundStyle = computed(() => ({
  backgroundImage: `url(${images.value[currentImageIndex.value]})`,
  opacity: opacity.value,
  transition: "opacity 1s ease-in-out",
}));
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
}

.second-main-container {
  width: 90%;
  margin: auto;
}

.main-banner {
  position: relative;
  height: 500px;
  display: flex;
  justify-content: space-around;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-size: cover;
  background-position: center;
  z-index: -1; /* 배경 이미지가 컨텐츠 뒤에 위치하도록 */
}

.banner-contentbox {
  position: relative; /* 상위 요소가 position: relative일 경우 필요 */
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 80%;
  margin: auto;
  background: transparent;
  padding-left: 10%;
}

.third-section {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  gap: 30px;
  margin: auto;
}

.sub-section {
  width: 50%;
}

.container {
  padding-left: 0;
  padding-right: 0;
}

@media (max-width: 768px) {
  .banner-contentbox {
    flex-direction: column;
  }
  .banner-contentbox {
    padding-left: 0%;
  }
  .third-section {
    flex-direction: column;
  }
  .sub-section {
  width: 100%;
}
}
</style>
