<template>
  <div class="profile-image" v-if="user">
    <img :src="profileImage" alt="프로필 이미지" />
  </div>
</template>
  
<script setup>
import { computed } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const user = computed(() => userStore.user);

const profileImage = computed(() => {
  return user.value?.profileImage || "src/assets/noImage.png"; // 기본 이미지 경로
});
</script>
  
<style scoped>
.profile-image {
  width: 40px;
  /* 프로필 이미지 크기 */
  height: 40px;
  border-radius: 50%;
  /* 동그란 모양 */
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* 이미지 비율 유지 */
}
</style>
  