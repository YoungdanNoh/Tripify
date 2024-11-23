import axios from "axios";

const SPOTIFY_CLIENT_ID = import.meta.env.VITE_SPOTIFY_CLIENT_ID;
const SPOTIFY_CLIENT_SECRET = import.meta.env.VITE_SPOTIFY_CLIENT_SECRET;

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
  const url = `https://api.spotify.com/v1/recommendations?seed_tracks=${trackId}&limit=10`;

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
