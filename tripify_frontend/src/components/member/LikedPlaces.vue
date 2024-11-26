<template>
    <div class="liked-places-container">
        <PlaceItem v-for="place in likedPlaces" :key="place.place_id" :place="place" @click="selectOne(place.placeId)" />
        <PlaceModal v-if="isSelected" @close="() => { isSelected = false }"></PlaceModal>
    </div>
    <div v-if="likedPlaces.length === 0" class="no-places">
        <p class="no-places-text">아직 마음에 드는 장소가 없어요!</p>
        <button class="explore-button btn btn-success" @click="goToPlaces">여행지 찾으러 가기</button>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import PlaceItem from '@/components/PlaceItem.vue';
import { useUserStore } from "@/stores/user";
import { usePlaceStore } from "@/stores/place";
import { getLikedPlaces } from "@/api/place";
import PlaceModal from "../PlaceModal.vue";
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const placeStore = usePlaceStore();
const router = useRouter();
const likedPlaces = ref([]);
const isSelected = ref(false);

async function fetchLikedPlaces() {
    try {
        const response = await getLikedPlaces(userStore.user.userId);
        likedPlaces.value = response;
    } catch (error) {
        console.error("Error fetching liked places:", error);
        likedPlaces.value = [];
    }
}

function selectOne(placeId) {
    isSelected.value = true;
    placeStore.setSelectedPlace(placeId);
}

function goToPlaces() {
    router.push({ name: 'place' });
}

onMounted(fetchLikedPlaces);
</script>

<style scoped>
.liked-places-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 16px;
}

.no-places {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin: 50px 0;
    height: 200px;
    /* 높이 설정 */
}

.no-places-text {
    font-size: 1.5rem;
    margin-bottom: 20px;
}

.explore-button {
    padding: 10px 20px;
    font-size: 1.2rem;
    cursor: pointer;
}
</style>
