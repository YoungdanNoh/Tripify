<template>
    <div class="recommend-container" v-if="similarTracks.length">
        <h3>추천 음악</h3>
        <div v-for="(item, index) in similarTracks" :key="index" class="recommend-item">
            <img :src="item.albumImage" alt="Album Art" class="album-art" />
            <div class="track-info">
                <h4>{{ item.name }}</h4>
                <p>{{ item.artist }}</p>
                <a :href="item.spotifyUrl" target="_blank">Spotify에서 보기</a>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed } from "vue";
import { useMusicStore } from "@/stores/music"; // Pinia store import

// Pinia store instance
const store = useMusicStore();

// store에서 추천 음악 가져오기
const similarTracks = computed(() => store.similarTracks);

// 비슷한 음악 추천 요청 (필요한 경우 함수 호출 예시)
const fetchRecommendations = (trackId) => {
    store.getRecommendations(trackId);
};
</script>

<style scoped>
.recommend-container {
    margin-top: 20px;
}

.recommend-item {
    display: flex;
    margin-bottom: 15px;
}

.album-art {
    width: 60px;
    height: 60px;
    margin-right: 15px;
    border-radius: 8px;
}

.track-info {
    flex: 1;
}

.track-info h4 {
    margin: 0;
}

.track-info p {
    color: #666;
}
</style>
