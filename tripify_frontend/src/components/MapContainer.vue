<script setup>
import { ref, watch, onMounted } from 'vue';
import { usePlaceStore } from '@/stores/place';
import { storeToRefs } from 'pinia';

const store = usePlaceStore();
const { getPlaces, pgno } = storeToRefs(store);

let map;
const markers = ref([]);

const props = defineProps({ stations: Array, selectStation: Object });

// getPlaces 값이 변경될 때마다 loadMarkers 호출
watch(getPlaces, (newPlaces) => {
  if (newPlaces && newPlaces.length > 0) {
    loadMarkers(newPlaces);
  }
});

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById('map');
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
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커를 생성합니다
  markers.value = [];
  places.forEach((place) => {
    const position = new kakao.maps.LatLng(place.latitude, place.longitude);
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position, // 마커를 표시할 위치
      title: place.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
    });
    markers.value.push(marker);
  });

  // LatLngBounds 객체 명시적으로 생성
  const bounds = new kakao.maps.LatLngBounds();

  // 마커들의 위치를 bounds에 추가
  markers.value.forEach((marker) => {
    bounds.extend(marker.getPosition());
  });

  // 첫 로드 시 setBounds()로 지도 자동 확대 및 중심 설정
  //console.log('pgno.value:', pgno.value);
  if (pgno.value == 1) {
    map.setBounds(bounds); // 지도 영역을 자동으로 맞추고, 해당 영역을 중심으로 이동
  }
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
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
