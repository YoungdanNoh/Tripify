<script setup>
import { ref, watch, onMounted } from "vue";
import { usePlaceStore } from "@/stores/place";
import { storeToRefs } from "pinia";

const store = usePlaceStore();
const { getPlaces, highlightedPlaceId } = storeToRefs(store);

let map;
const markers = ref([]);
const highlightedMarker = ref(null); // 현재 강조된 마커

// getPlaces 값이 변경될 때마다 loadMarkers 호출
watch(getPlaces, (newPlaces) => {
  if (newPlaces && newPlaces.length > 0) {
    loadMarkers(newPlaces);
  }
});

watch(highlightedPlaceId, (newPlaceId) => {
  if (newPlaceId) {
    highlightMarker(newPlaceId);
  } else {
    resetMarkers();
  }
});

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 초기 중심
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // getPlaces가 비어 있지 않은 경우에만 loadMarkers 호출
  if (getPlaces.value && getPlaces.value.length > 0) {
    loadMarkers(getPlaces.value);
  }
};

const loadMarkers = (places) => {
  //deleteMarkers();
  markers.value = [];

  places.forEach((place) => {
    const position = new kakao.maps.LatLng(place.latitude, place.longitude);
    const marker = new kakao.maps.Marker({
      map: map,
      position: position,
      title: place.title,
      clickable: true,
    });

    kakao.maps.event.addListener(marker, "click", async () => {
      console.log("is..",place.place_id);
      
      await store.setSelectedPlace(place.place_id); // 장소 정보 로드
      console.log("are...",store.selectedPlace);
      
    });

    markers.value.push({ marker, placeId: place.place_id });
  });

  const bounds = new kakao.maps.LatLngBounds();
  markers.value.forEach(({ marker }) => bounds.extend(marker.getPosition()));
  map.setBounds(bounds);
};

// 특정 마커 강조
const highlightMarker = (placeId) => {
  resetMarkers(); // 다른 마커 리셋

  const target = markers.value.find((m) => m.placeId === placeId);

  if (target) {
    const { marker } = target;

    // 강조 스타일 설정
    const highlightImage = new kakao.maps.MarkerImage(
      "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
      new kakao.maps.Size(35, 50), // 이미지 크기
      { offset: new kakao.maps.Point(14, 50) } // 중심 좌표
    );
    marker.setImage(highlightImage); // 강조 이미지 설정
    marker.setZIndex(1); // zIndex를 높여서 위로 표시

    highlightedMarker.value = marker; // 강조된 마커 저장
  }
};

// 모든 마커 리셋
const resetMarkers = () => {
  if (highlightedMarker.value) {
    const defaultImage = new kakao.maps.MarkerImage(
      "https://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png",
      new kakao.maps.Size(26, 37),
      { offset: new kakao.maps.Point(14, 37) }
    );
    highlightedMarker.value.setImage(defaultImage); // 기본 이미지로 변경
    highlightedMarker.value.setZIndex(0); // zIndex 기본값으로 변경
    highlightedMarker.value = null;
  }
};
</script>


<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 700px;
}
</style>
