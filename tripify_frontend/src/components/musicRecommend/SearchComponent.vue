<template>
  <div class="search-container">
    <!-- 키워드 입력 -->
    <div class="keyword-container">
      <input
        v-model="inputKeyword"
        @keyup.enter="addKeyword"
        type="text"
        placeholder="키워드를 입력해보세요!"
        class="keyword-input"
      />
      <button @click="addKeyword" class="add-button">추가</button>
      <button @click="searchMusic" class="search-button">검색</button>
    </div>

    <!-- 태그 형태의 키워드 목록 -->
    <div class="tag-list">
      <div v-for="(keyword, index) in keywords" :key="index" class="tag">
        {{ keyword }}
        <button @click="removeKeyword(index)" class="remove-button">×</button>
      </div>
    </div>

    <!-- 검색 결과 -->
    <div v-show="loadingState" class="loading-spinner">
      <VueSpinnerAudio size="50" color="green" />
    </div>
    <div v-if="gptRecommends.length" class="gptRecommends">
      <h3>검색 결과</h3>
      <div v-for="(item, index) in gptRecommends" :key="index" class="result-item">
        <!-- <img :src="item.image" alt="Album Art" class="album-art" /> -->
        <div class="track-info">
          <h4>{{ item.title }}</h4>
          <p>{{ item.artist }}</p>
          <!-- <a :href="item.url" target="_blank">Spotify에서 보기</a> -->
        </div>
        <div class="button-container">
          <button @click="startPlayList(item)">이 음악으로 재생목록 만들기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useMusicStore } from "@/stores/music"; // Pinia store import
//import { isLoading } from "@/api/music"; // 로�� 상태 관리
import { VueSpinnerAudio } from "vue3-spinners";

import { storeToRefs } from "pinia";
const store = useMusicStore(); // Pinia store instance

//const { getLoading } = store; // loading 포함

// 상태 관리
const inputKeyword = ref("");
const keywords = computed(() => store.keywords); // 키워드 목록
const gptRecommends = computed(() => store.gptRecommends);
const similarTracks = computed(() => store.similarTracks);

const route = useRoute(); //경로정보 이용 표시

const loadingState = computed(() => store.getLoading);

// 키워드 추가
const addKeyword = () => {
  store.addKeyword(inputKeyword.value);
  inputKeyword.value = ""; // 입력 필드 초기화
};

// 키워드 제거
const removeKeyword = (index) => {
  store.removeKeyword(index);
};

// 음악 검색
const searchMusic = async () => {
  //console.log("검색 시작 전 상태:", getLoading); // 시작 전 상태 확인
  if (!store.accessToken) {
    await store.getAccessToken(); // 액세스 토큰이 없다면 발급
  }
  await store.searchMusicGpt(); // 검색 실행
  //console.log("검색 종료 후 상태:", getLoading); // 실행 후 상태 확인
};

const startPlayList = async (item) => {
  store.searchTrack(item.title, item.artist);
};

// 컴포넌트 마운트 시 액세스 토큰 발급
onMounted(async () => {
  if (!store.accessToken) {
    store.getAccessToken(); // 페이지 로드 시 토큰 발급
  }
  await store.resetKeywords();

  if (route.query.location) {
    inputKeyword.value = route.query.location;
    addKeyword();
    inputKeyword.value = route.query.mood;
    addKeyword();
    inputKeyword.value = route.query.place_name;
    addKeyword();
    searchMusic(); //노래 검색
    //console.log('log here', route.query.location);
  }
});
</script>

<style scoped>
.keyword-container {
  display: flex;
  align-items: center;
  /* 수직 중앙 정렬 */
  gap: 10px;
  /* 요소 간 간격 */
}

.keyword-input {
  flex: 1;
  /* 입력란이 남은 공간을 차지하게 함 */
  padding: 8px;
  border: 1px solid #ccc;
}

.search-container {
  max-width: 600px;
  margin: 20px auto;
  font-family: Arial, sans-serif;
}

.keyword-input {
  width: calc(100% - 100px);
  padding: 10px;
  margin-right: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.add-button,
.search-button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-button:hover,
.search-button:hover {
  background-color: #0056b3;
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  margin-top: 20px;
}

.tag {
  display: flex;
  align-items: center;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 20px;
  padding: 5px 10px;
  margin: 5px;
  font-size: 14px;
}

.remove-button {
  background: none;
  border: none;
  color: #888;
  margin-left: 10px;
  cursor: pointer;
}

.remove-button:hover {
  color: red;
}

.results {
  margin-top: 20px;
}

.result-item {
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

.result-item {
  display: flex;
  align-items: center;
  /* 세로 중앙 정렬 */
  justify-content: space-between;
  /* 양 끝 정렬 */
  margin-bottom: 15px;
  /* 항목 간 간격 */
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
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

.button-container {
  display: flex;
  justify-content: flex-end;
  /* 오른쪽 정렬 */
}

button {
  padding: 8px 16px;
  background-color: #1db954;
  /* Spotify 색상 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #1ed760;
  /* 호버 시 색상 */
}

.loading-spinner {
  margin-top: 100px;
  display: flex;
  justify-content: center; /* 로딩 스피너를 중앙 정렬 */
}
</style>
