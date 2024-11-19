<template>
  <div class="music-app">
    <header class="app-header">
      <h1>Spotify Playlist Manager</h1>
      <button v-if="user" @click="logout">로그아웃</button>
    </header>

    <!-- 사용자 정보 -->
    <div v-if="user" class="user-info">
      <h2>환영합니다, {{ user.display_name }}</h2>
      <p>{{ user.email }}</p>
    </div>

    <!-- 재생목록 -->
    <!-- <div class="playlists" v-if="playlists.length">
      <h2>내 재생목록</h2>
      <ul>
        <li
          v-for="playlist in playlists"
          :key="playlist.id"
          @click="loadPlaylistTracks(playlist.id)"
        >
          {{ playlist.name }} ({{ playlist.tracks.total }} 곡)
        </li>
      </ul>
    </div> -->

    <!-- 선택된 재생목록의 트랙 -->
    <!-- <div v-if="currentPlaylistTracks.length">
      <h2>{{ currentPlaylistName }}의 트랙</h2>
      <ul>
        <li
          v-for="track in currentPlaylistTracks"
          :key="track.track.id"
          @click="playTrack(track.track.id)"
        >
          {{ track.track.name }} - {{ track.track.artists[0].name }}
        </li>
      </ul>
    </div> -->
  </div>
</template>

<script setup>
import { ref } from "vue";
import { authorize, handleRedirectCallback, getCurrentUser } from "@/api/spotify";

// 상태 변수
const user = ref(null);

// 사용자 초기화
const initialize = async () => {
  try {
    handleRedirectCallback(); // Access Token 처리
    user.value = await getCurrentUser(); // 사용자 정보 가져오기
  } catch (error) {
    console.error("Spotify API 초기화 중 오류:", error);
  }
};

// 로그아웃 기능
const logout = () => {
  // 1. 로컬 데이터 삭제
  localStorage.clear();
  sessionStorage.clear();
  user.value = null;

  // 2. Spotify 로그아웃 URL 호출
  const spotifyLogoutUrl = "https://accounts.spotify.com/en/logout";

  // 3. 새로운 탭에서 Spotify 로그아웃 페이지 열기
  const newWindow = window.open(spotifyLogoutUrl, "_blank");

  // 4. 로그아웃 완료 후 메인 경로로 이동
  setTimeout(() => {
    if (newWindow) newWindow.close(); // 새 창 닫기
    window.location.href = "/"; // 애플리케이션 루트 경로로 이동
  }, 100); // 2초 대기 후 리디렉션
};

// 컴포넌트 초기화
if (window.location.hash.includes("access_token")) {
  initialize();
}
// import { ref } from "vue";
// import { authorize, fetchPlaylists, fetchPlaylistTracks, searchTracks } from "@/api/spotify";
// import { useUserStore } from "@/stores/user";
// import { getCurrentUser } from "@/api/spotify";

// const userStore = useUserStore();
// const user = ref(null);
// const playlists = ref([]);
// const currentPlaylistTracks = ref([]);
// const currentPlaylistName = ref("");

// // 사용자 정보 및 재생목록을 초기화
// const initialize = async () => {
//   user.value = await getCurrentUser();
//   playlists.value = await fetchPlaylists();
// };

// // 선택한 재생목록의 트랙을 가져오는 함수
// const loadPlaylistTracks = async (playlistId) => {
//   currentPlaylistTracks.value = await fetchPlaylistTracks(playlistId);
//   const playlist = playlists.value.find((p) => p.id === playlistId);
//   currentPlaylistName.value = playlist.name;
// };

// // 트랙을 클릭하면 재생하는 함수 (단순히 console에 로그를 남기는 예시)
// const playTrack = (trackId) => {
//   console.log("재생할 트랙 ID:", trackId);
//   // 실제 재생 로직은 Spotify Web Playback SDK를 사용해야 합니다.
// };

// if (window.location.hash.includes("access_token")) {
//   initialize();
// }
</script>

<style scoped>
.music-app {
  font-family: Arial, sans-serif;
  padding: 20px;
}

.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background: #1db954;
  color: white;
}

.user-info {
  margin-top: 20px;
}

.playlists {
  margin-top: 20px;
}
</style>
