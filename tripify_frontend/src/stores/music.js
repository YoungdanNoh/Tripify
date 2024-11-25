import { ref, computed, watchEffect } from "vue";
import { defineStore } from "pinia";
import { fetchAccessToken, searchTrackId, searchMusic, getSimilarTracks } from "@/api/music"; // Spotify API 함수 가져오기

export const useMusicStore = defineStore("music", () => {
  // 1. data
  const trackId = ref(null); // 현재 검색된 트랙 ID
  const similarTracks = ref([]); // 추천된 비슷한 트랙들
  const loading = ref(false); // 로딩 상태
  const error = ref(null); // 오류 메시지
  const keywords = ref([]); // 검색 키워드 목록
  const results = ref([]); // 검색 결과
  const accessToken = ref(fetchAccessToken()); // Access Token
  const gptRecommends = ref([]); //

  // 2. getters
  const getTrackId = computed(() => trackId.value);
  const getLoading = computed(() => loading.value);
  const getError = computed(() => error.value);
  const getKeywords = computed(() => keywords.value);
  const getResults = computed(() => results.value);
  const getGptRecommends = computed(() => gptRecommends.value);

  // 3. actions
  const addKeyword = (keyword) => {
    if (keyword && !keywords.value.includes(keyword)) {
      keywords.value.push(keyword);
    }
  };

  const resetKeywords = () => {
    keywords.value = [];
    gptRecommends.value = [];
  };

  const removeKeyword = (index) => {
    keywords.value.splice(index, 1);
  };

  const searchMusicGpt = () => {
    searchMusic(keywords.value).then((data) => {
      if (data && data.music) {
        // 제목과 아티스트를 모두 포함한 배열 생성
        const musics = data.music.map((track) => {
          return {
            title: track.title,
            artist: track.artist,
          };
        });

        console.log(musics); // [{ title, artist }, ...] 출력
        gptRecommends.value = musics; // 제목과 아티스트 배열 저장
      } else {
        console.warn("검색 결과에 음악 데이터가 없습니다.");
      }
    });
  };

  const searchTrack = async (trackName, artistName) => {
    loading.value = true;
    error.value = null;

    try {
      const fetchedTrackId = await searchTrackId(trackName, artistName);
      if (fetchedTrackId) {
        trackId.value = fetchedTrackId;
        getSimilarTracks(fetchedTrackId).then((tracks) => {
          similarTracks.value = tracks;
          console.log(tracks);
        });
      } else {
        throw new Error("해당 트랙을 찾을 수 없습니다.");
      }
    } catch (err) {
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  };

  // accessToken 값 변경 시 처리 로직 추가 (예: console.log, 업데이트 등)
  watchEffect(() => {
    console.log("AccessToken updated:", accessToken.value);
  });

  return {
    trackId,
    similarTracks,
    loading,
    error,
    keywords,
    results,
    accessToken,
    gptRecommends,
    getTrackId,
    getLoading,
    getError,
    getKeywords,
    getResults,
    getGptRecommends,
    addKeyword,
    resetKeywords,
    removeKeyword,
    searchMusicGpt,
    searchTrack,
  };
});
