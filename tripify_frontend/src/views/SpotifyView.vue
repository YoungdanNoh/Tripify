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

    <div>
      <h1>Spotify Player</h1>
      <div id="spotify-player-container"></div>
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
import { ref, onMounted } from "vue";
import { accessToken, authorize, handleRedirectCallback, getCurrentUser } from "@/api/spotify";

// 상태 변수
const user = ref(null);

// Spotify 플레이어 초기화 함수
function initializeSpotifyPlayer() {
  if (!accessToken) {
    console.error("Access token이 없습니다.");
    return;
  }

  const player = new window.Spotify.Player({
    name: "Vue Spotify Player",
    getOAuthToken: (cb) => {
      cb(accessToken); // 여기서 OAuth 토큰을 반환합니다.
    },
    volume: 0.5,
  });

  // 플레이어 이벤트 리스너
  player.addListener("ready", ({ device_id }) => {
    console.log("Spotify Player is ready with device ID", device_id);
  });

  player.addListener("not_ready", ({ device_id }) => {
    console.log("Spotify Player is not ready", device_id);
  });

  // Spotify 플레이어 연결
  player.connect().then((success) => {
    if (success) {
      console.log("Spotify Player 연결 성공");
    } else {
      console.error("Spotify Player 연결 실패");
    }
  });
}

// 컴포넌트가 마운트될 때 Spotify Player 초기화
onMounted(() => {
  window.vueApp = { initializeSpotifyPlayer };
  if (window.location.hash.includes("access_token")) {
    initialize();
  }
});

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

#spotify-player-container {
  width: 100%;
  height: 300px;
  background-color: #000;
}
</style>
