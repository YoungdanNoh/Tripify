<template>
    <div class="recommend-container" v-if="recommendations.length">
        <h3>추천 음악</h3>
        <div v-for="(item, index) in recommendations" :key="index" class="recommend-item">
            <img :src="item.image" alt="Album Art" class="album-art" />
            <div class="track-info">
                <h4>{{ item.name }}</h4>
                <p>{{ item.artist }}</p>
                <a :href="item.url" target="_blank">Spotify에서 보기</a>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { fetchAccessToken } from "@/api/music";

const recommendations = ref([]);

// Access Token 및 추천 음악 데이터 받기
const fetchRecommendations = async (trackId) => {
    const accessToken = await fetchAccessToken();
    const url = `https://api.spotify.com/v1/recommendations?seed_tracks=${trackId}&limit=10&min_valence=0.5`;

    try {
        const response = await axios.get(url, {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        });

        recommendations.value = response.data.tracks.map((track) => ({
            name: track.name,
            artist: track.artists.map((artist) => artist.name).join(", "),
            image: track.album.images[0]?.url || "",
            url: track.external_urls.spotify,
        }));
    } catch (error) {
        console.error("추천 음악 가져오기 실패:", error);
    }
};

// 추천 요청 이벤트 처리
onMounted(() => {
    window.addEventListener("recommendations", async (event) => {
        const track = event.detail;
        await fetchRecommendations(track.id);
    });
});
</script>

  
<style scoped>
.recommend-container {
    max-width: 600px;
    margin: 20px auto;
}

.recommend-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.album-art {
    width: 50px;
    height: 50px;
    margin-right: 15px;
}

.track-info h4 {
    margin: 0;
    font-size: 16px;
}

.track-info p {
    margin: 5px 0;
    color: #666;
}
</style>
  