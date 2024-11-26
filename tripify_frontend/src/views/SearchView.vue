<template>
  <div class="container">
    <SearchFilter @selectedSido="updateSelectedSido" @selectedGugun="updateSelectedGugun"
      @selectedType="updateSelectedType" @emitSearch="searchKeyword" />

    <section class="main-content">
      <div v-if="getPlaces.length === 0" class="no-places">검색해주세요</div>
      <PlaceList @load-more="loadMorePlaces" @select-place="showPlaceDetails" />
      <MapContainer @select-place="showPlaceDetails" />
    </section>

    <PlaceModal v-if="selectedPlace" :place="selectedPlace" @close="closeModal" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import SearchFilter from '../components/SearchFilter.vue';
import PlaceList from '../components/PlaceList.vue';
import MapContainer from '../components/MapContainer.vue';
import PlaceModal from '../components/PlaceModal.vue';

import { usePlaceStore } from '@/stores/place';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';

const store = usePlaceStore();
const userStore = useUserStore();
const { getPlaces } = storeToRefs(store);
const { listPlaces, resetPlaces } = store;
const selectedPlace = computed(() => store.selectedPlace)

const sido = ref('');
const gugun = ref('');
const type = ref('');
const keyword = ref('');
const pgno = ref(1);
const loading = ref(false);

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

    listPlaces(location);
    loading.value = false;
  }, 1000);
}

function showPlaceDetails(placeId) {
  store.setSelectedPlace(placeId);
}

function closeModal() {
  store.clearSelectedPlace();
}

function updateSelectedSido(Sido) {
  sido.value = Sido;
}

function updateSelectedGugun(Gugun) {
  gugun.value = Gugun;
}

function updateSelectedType(Type) {
  type.value = Type;
}

function searchKeyword(Keyword) {
  keyword.value = Keyword;
  handleSearch();
}

function handleSearch() {
  pgno.value = 0;
  resetPlaces();
  loadMorePlaces();
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
  white-space: nowrap;
  /* 텍스트가 줄바꿈되지 않도록 설정 */
}
</style>
