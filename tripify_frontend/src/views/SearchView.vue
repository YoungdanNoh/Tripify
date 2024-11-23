<template>
  <div class="container">
    <SearchFilter
      @selectedSido="updateSelectedSido"
      @selectedGugun="updateSelectedGugun"
      @selectedType="updateSelectedType"
      @emitSearch="searchKeyword"
    />

    <section class="main-content">
      <div v-if="getPlaces.length === 0" class="no-places">검색해주세요</div>
      <PlaceList @load-more="loadMorePlaces" />

      <MapContainer />
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import SearchFilter from '../components/SearchFilter.vue';
import PlaceList from '../components/PlaceList.vue';
import MapContainer from '../components/MapContainer.vue';

import { usePlaceStore } from '@/stores/place';
import {useUserStore} from '@/stores/user';
import { storeToRefs } from 'pinia';

const store = usePlaceStore();
const userStore = useUserStore();
const { getPlaces } = storeToRefs(store);
const { listPlaces, resetPlaces } = store;

// 컴포넌트 등록
const selectedCity = ref('');
const selectedDistrict = ref('');
const selectedType = ref('');
const searchQuery = ref('');
const places = ref([]);
const page = ref(1);
const loading = ref(false);

const sido = ref('');
const gugun = ref('');
const type = ref('');
const keyword = ref('');
const pgno = ref(1);

// 검색 처리 함수
function handleSearch() {
  page.value = 1;
  places.value = [];
  pgno.value = 0;
  console.log('Search Keyword:', getPlaces.value);
  resetPlaces();
  loadMorePlaces();
}

// 장소 불러오기 함수
function loadMorePlaces() {
  if (loading.value) return;
  loading.value = true;

  setTimeout(() => {
    pgno.value += 1;

    const location = {
      sido: sido.value,
      gugun: gugun.value,
      type: type.value,
      word: keyword.value,
      pgno: pgno.value,
      userId: userStore.getUserId(),
    };

    //console.log(location.userId);
    
    listPlaces(location);

    loading.value = false;
  }, 1000);

  //console.log(getPlaces.value);
}

/*
// 컴포넌트가 마운트되었을 때 첫 장소 로드
onMounted(() => {
  loadMorePlaces();
}); */

function updateSelectedSido(Sido) {
  //시도 코드를 가져온다.
  sido.value = Sido;
  //console.log('Selected Sido:', Sido);
}

function updateSelectedGugun(Gugun) {
  //구군 코드를 가져온다.
  gugun.value = Gugun;
  //console.log('Selected Gugun:', Gugun);
}

function updateSelectedType(Type) {
  //관광지 타입 코드를 가져온다.
  type.value = Type;
  //console.log('Selected Type:', Type);
}

function searchKeyword(Keyword) {
  keyword.value = Keyword;
  //console.log('Search Keyword:', keyword);
  handleSearch();
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.main-content {
  display: flex;
}

.no-places {
  white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 설정 */
}
</style>
