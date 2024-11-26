<template>
    <div class="liked-places-container">
        <PlaceItem v-for="place in likedPlaces" :key="place.place_id" :place="place" @click="selectOne(place.placeId)" />
        <PlaceModal v-if="isSelected" @close="() => { isSelected = false }"></PlaceModal>
    </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import PlaceItem from '@/components/PlaceItem.vue'; // PlaceItem 컴포넌트 임포트
import { useUserStore } from "@/stores/user"; // userStore 임포트
import { usePlaceStore } from "@/stores/place"; // userStore 임포트
import { getLikedPlaces } from "@/api/place";
import PlaceModal from "../PlaceModal.vue";

// Pinia store 가져오기
const userStore = useUserStore();
const placeStore = usePlaceStore();
const likedPlaces = ref([]);
const isSelected = ref(false);

// 좋아하는 장소 가져오기
async function fetchLikedPlaces() {
    try {
        const response = await getLikedPlaces(userStore.user.userId); // API 호출
        likedPlaces.value = response; // 응답 저장
    } catch (error) {
        console.error("Error fetching liked places:", error);
        likedPlaces.value = []; // 오류 발생시 빈 배열로 설정
    }
}

function selectOne(placeId) {
    isSelected.value = true;
    placeStore.setSelectedPlace(placeId); // placeStore.setSelectedPlace(placeId)로 placeId를 selectedPlaceId로 설정
}

// 컴포넌트 마운트 시 데이터 로드
onMounted(fetchLikedPlaces);
</script>
  
<style scoped>
.liked-places-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 16px;
}
</style>
  