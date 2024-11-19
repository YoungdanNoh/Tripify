import axios from "axios";

const SPOTIFY_AUTH_URL = "https://accounts.spotify.com/authorize";
const SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

const CLIENT_ID = import.meta.env.VITE_SPOTIFY_CLIENT_ID; // .env에 정의된 클라이언트 ID
const REDIRECT_URI = "http://localhost:5173/spotify"; // Spotify Dashboard와 일치해야 함
// 기존 SCOPES에 추가
const SCOPES = [
  "user-read-private", // 사용자 정보 읽기
  "user-read-email", // 사용자 이메일 읽기 권한
  "streaming", // 스트리밍 권한
  "user-modify-playback-state", // 플레이어 상태 제어
  "user-read-playback-state", // 현재 재생 상태 읽기
].join(" ");

export let accessToken = ""; // Access Token 저장

// **1. Spotify 로그인**
export const authorize = () => {
  const authUrl = `${SPOTIFY_AUTH_URL}?response_type=token&client_id=${CLIENT_ID}&redirect_uri=${encodeURIComponent(
    REDIRECT_URI
  )}&scope=${encodeURIComponent(SCOPES)}`;

  console.log(authUrl);
  window.location = authUrl; // Spotify 로그인 페이지로 이동
};

// **2. Redirect Callback 처리**
export const handleRedirectCallback = () => {
  const hashParams = new URLSearchParams(window.location.hash.substring(1)); // URL의 해시(#) 파라미터 파싱
  const token = hashParams.get("access_token");

  if (token) {
    accessToken = token; // Access Token 설정
  } else {
    throw new Error("Authorization failed. Access token is missing.");
  }
};

// **3. 현재 사용자 정보 가져오기**
export const getCurrentUser = async () => {
  if (!accessToken) {
    throw new Error("Access token is not set. Please authorize first.");
  }

  const response = await axios.get(`${SPOTIFY_API_BASE_URL}/me`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  return response.data; // 사용자 데이터 반환
};
