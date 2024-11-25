import axios from "axios";
import { ref } from "vue";

const SPOTIFY_CLIENT_ID = import.meta.env.VITE_SPOTIFY_CLIENT_ID;
const SPOTIFY_CLIENT_SECRET = import.meta.env.VITE_SPOTIFY_CLIENT_SECRET;
//export const isLoading = ref(false);

let accessToken = "";

// Access Token 발급 함수
export const fetchAccessToken = async () => {
  const url = "https://accounts.spotify.com/api/token";
  const credentials = btoa(`${SPOTIFY_CLIENT_ID}:${SPOTIFY_CLIENT_SECRET}`);
  try {
    const response = await axios.post(
      url,
      new URLSearchParams({ grant_type: "client_credentials" }).toString(),
      {
        headers: {
          Authorization: `Basic ${credentials}`,
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }
    );
    accessToken = response.data.access_token;
    return response.data.access_token;
  } catch (error) {
    console.error("Access Token 발급 실패:", error);
    throw new Error("Spotify Access Token 발급에 실패했습니다.");
  }
};

export const searchMusic = async (keywords) => {
  try {
    // 키워드 배열을 '+'로 연결
    const query = keywords.join("+");
    const apiUrl = `http://localhost/music/${query}`;

    // API 호출
    const response = await fetch(apiUrl, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    // 응답 처리
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data; // music 데이터 반환
  } catch (error) {
    console.error("Error fetching music:", error);
    return null; // 에러 발생 시 null 반환
  }
};

// 음악 제목과 가수명으로 특정 트랙을 찾아 trackId 반환
export const searchTrackId = async (trackName, artistName) => {
  const accessToken = await fetchAccessToken();
  const url = `https://api.spotify.com/v1/search?q=track:${trackName} artist:${artistName}&type=track&limit=1`;

  try {
    const response = await axios.get(url, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    const track = response.data.tracks.items[0]; // 첫 번째 트랙 반환
    return track ? track.id : null; // trackId 반환
  } catch (error) {
    console.error("음악 검색 실패:", error);
    throw new Error("음악 검색에 실패했습니다.");
  }
};

export const getSimilarTracks = async (trackId) => {
  const accessToken = await fetchAccessToken();
  const url = `https://api.spotify.com/v1/recommendations?seed_tracks=${trackId}&limit=6`;

  try {
    const response = await axios.get(url, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data.tracks.map((track) => ({
      id: track.id,
      name: track.name,
      artist: track.artists[0]?.name,
      album: track.album.name,
      albumImage: track.album.images[0]?.url,
      spotifyUrl: track.external_urls.spotify,
    })); // 추천 트랙 정보 배열로 반환
  } catch (error) {
    console.error("추천 트랙 가져오기 실패:", error);
    throw new Error("추천 트랙을 가져오는 데 실패했습니다.");
  }
};

export const fetchPlaylistData = async (playlistId) => {
  const baseUrl = "http://localhost/music/playlist"; // {{baseurl}} 값 대체
  try {
    const response = await axios.get(`${baseUrl}/${playlistId}`, {
      headers: { "Content-Type": "application/json" },
    });
    const spotifyTrackIds = response.data.trackIds.map((track) => track.spotifyTrackId);
    const result = await fetchSpotifyTracks(spotifyTrackIds);
    return result; // 플레이리스트 데이터 반환
  } catch (error) {
    console.error("플레이리스트 데이터 가져오기 실패:", error);
    throw new Error("플레이리스트 데이터를 가져오는 데 실패했습니다.");
  }
};

// Spotify API URL
const SPOTIFY_API_URL = 'https://api.spotify.com/v1';

// spotifyTrackIds 배열에 있는 각 트랙 ID로부터 음악 정보를 가져오는 함수
async function fetchSpotifyTracks(spotifyTrackIds) {
  try {
    // Access Token이 비어 있는 경우 또는 갱신 필요 시 발급
    if (!accessToken) {
      accessToken = await fetchAccessToken(); // fetchAccessToken이 완료된 후 값 설정
    }

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };

    // 각 트랙 ID로부터 정보를 가져오는 요청
    const trackInfoPromises = spotifyTrackIds.map((trackId) =>
      axios.get(`${SPOTIFY_API_URL}/tracks/${trackId}`, { headers })
    );

    // 모든 요청 처리
    const trackResponses = await Promise.all(trackInfoPromises);

    // 응답 데이터에서 필요한 정보만 추출
    const trackInfos = trackResponses.map((response) => {
      const track = response.data;
      return {
        id: track.id,
        name: track.name,
        artist: track.artists[0]?.name, // 첫 번째 아티스트 이름
        album: track.album.name, // 앨범 이름
        albumImage: track.album.images[0]?.url, // 앨범 이미지
        spotifyUrl: track.external_urls.spotify, // Spotify URL
      };
    });

    return trackInfos; // 음악 정보를 배열 형태로 반환
  } catch (error) {
    console.error("Error fetching Spotify tracks:", error.message);
    throw error;
  }
}

// 재생목록 저장 함수
export const registerPlaylist = async (planPlaceId, trackIds) => {
  try {
    const response = await axios.post("http://localhost/music/playlist", {
      planPlaceId,
      trackIds,
    });

    if (response.data.success) {
      return { success: true, message: "재생목록이 성공적으로 저장되었습니다!" };
    } else {
      return { success: false, message: response.data.message };
    }
  } catch (error) {
    console.error("API 요청 실패:", error.message);
    return { success: false, message: "재생목록 저장 중 오류가 발생했습니다." };
  }
};