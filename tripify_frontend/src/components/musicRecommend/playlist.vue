<template>
  <div class="recommend-container" v-if="playlist.length">
    <h3>추천 음악</h3>
    <div v-for="(item, index) in playlist" :key="index" class="recommend-item">
      <img :src="item.albumImage" alt="Album Art" class="album-art" />
      <div class="track-info">
        <!-- item.name만 클릭할 수 있도록 a 태그로 감쌈 -->
        <a :href="item.spotifyUrl" target="_blank" class="track-name">
          <h4>{{ item.name }}</h4>
        </a>
        <p>{{ item.artist }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { fetchPlaylistData } from "@/api/music";

// store에서 추천 음악 가져오기
const playlist = ref(null);

onMounted(async () => {
  const playlistId = 13; // 사용하려는 Playlist ID
  try {
    const data = await fetchPlaylistData(playlistId);
    playlist.value = data;
  } catch (error) {
    console.error("플레이리스트 가져오기 실패:", error.message);
  }
});

</script>

<style scoped>
.recommend-container {
  margin-top: 20px;
  max-height: 800px;
  /* 최대 높이 설정 */
  overflow-y: auto;
  /* 세로 스크롤 허용 */
}

.recommend-container::-webkit-scrollbar {
  width: 8px;
  /* 스크롤바의 너비 */
}

.recommend-container::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  /* 스크롤바의 색 */
  border-radius: 4px;
}

.recommend-container::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  /* 스크롤바의 트랙 색 */
}

.recommend-item {
  display: flex;
  justify-content: space-between;
  /* 이미지와 텍스트 사이에 공간을 추가 */
  align-items: center;
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

/* a 태그의 기본 스타일을 제거하고, 커스텀 스타일을 추가 */
.track-name {
  text-decoration: none;
  /* 기본 밑줄 제거 */
  color: #333;
  /* 텍스트 색상 */
  font-weight: bold;
  /* 텍스트를 볼드로 */
  transition: all 0.3s ease;
  /* 애니메이션 효과 */
}

.track-name:hover {
  color: #1db954;
  /* Spotify 녹색을 호버 색상으로 설정 */
  text-decoration: underline;
  /* 마우스를 올리면 밑줄 표시 */
}

.spotify-button {
  width: 120px;
  /* 버튼 크기 조정 */
  height: auto;
  cursor: pointer;
}
</style>
