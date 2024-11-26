<template>
  <div class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <button class="close-button" @click="closeModal">×</button>
      <div>
        <!-- 상단에 데이터 표시 -->
        <h3>추천 음악 목록</h3>
        <p><strong>플랜번호:</strong> {{ data.plan_place_id }}</p>
        <p><strong>장소:</strong> {{ data.location }}</p>
        <p><strong>감성:</strong> {{ data.mood }}</p>
        <p><strong>장소 이름:</strong> {{ data.place_name }}</p>
      </div>
      <div v-if="recommendationList.length > 0">
        <div v-for="(track, index) in recommendationList" :key="index" class="recommend-item">
          <img :src="track.albumImage" alt="Album Art" class="album-art" />
          <div class="track-info">
            <a :href="track.spotifyUrl" target="_blank" class="track-name">
              <h4>{{ track.name }}</h4>
            </a>
            <p>{{ track.artist }}</p>
          </div>
        </div>
      </div>
      <div v-else>
        <p>추천 음악을 가져오는 중입니다...</p>
      </div>
      <button @click="registPlaylistToThisPlace">현재 장소에 재생목록 추가하기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { searchMusic, searchTrackId, getSimilarTracks, registerPlaylist } from "@/api/music";

// Props 정의
const props = defineProps(["data"]);
const emit = defineEmits(["close"]);

const recommendationList = ref([]);

const plan_place_id = ref(0)

// 추천 음악 로직 구현
const fetchRecommendations = async () => {
  try {
    // Step 0: 키워드 기반 첫 번째 음악 가져오기
    plan_place_id.value = props.data.plan_place_id;
    const keyword = [props.data.location, props.data.mood, props.data.place_name];
    const musicResponse = await searchMusic(keyword);

    if (!musicResponse || !musicResponse.music || musicResponse.music.length === 0) {
      console.error("음악 추천 실패: 결과 없음");
      recommendationList.value = [];
      return;
    }

    const firstMusic = musicResponse.music[0]; // 첫 번째 음악 사용
    const { title, artist } = firstMusic;

    // Step 1: 첫 번째 음악의 Spotify Track ID 가져오기
    const trackId = await searchTrackId(title, artist);
    if (!trackId) {
      console.error("Spotify Track ID 검색 실패");
      recommendationList.value = [];
      return;
    }

    // Step 2: Spotify에서 유사 음악 추천받기
    recommendationList.value = await getSimilarTracks(trackId);
  } catch (error) {
    console.error("추천 음악 목록 가져오기 실패:", error);
    recommendationList.value = [];
  }
};

const registPlaylistToThisPlace = async () => {
  if (!plan_place_id.value || recommendationList.value.length === 0) {
    console.log(plan_place_id.value, recommendationList.value);
    
    alert("재생목록을 저장할 장소 ID 또는 추천 음악이 없습니다.");
    return;
  }

  const trackIds = recommendationList.value.map((track) => track.id);

  // API 호출
  const result = await registerPlaylist(plan_place_id.value, trackIds);

  if (result.success) {
    alert(result.message);
  } else {
    alert(`오류: ${result.message}`);
  }
  closeModal();
};

// 컴포넌트가 마운트되면 추천 음악 가져오기 실행
onMounted(() => {
  fetchRecommendations();
});

// 모달 닫기
const closeModal = () => {
  emit("close"); // 부모 컴포넌트로 닫기 이벤트 전달
  recommendationList.value = [];
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
  overflow-y: auto;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
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

.track-name {
  text-decoration: none;
  color: #333;
  font-weight: bold;
  transition: all 0.3s ease;
}

.track-name:hover {
  color: #1db954;
  text-decoration: underline;
}
</style>
