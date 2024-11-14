<template>
  <div class="container">
    <SearchFilter
      v-model:selectedCity="selectedCity"
      v-model:selectedDistrict="selectedDistrict"
      v-model:selectedType="selectedType"
      v-model:searchQuery="searchQuery"
      @search="handleSearch"
    />

    <section class="main-content">
      <PlaceList :places="places" @load-more="loadMorePlaces" />
      <MapContainer />
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import HeaderNav from "../components/HeaderNav.vue";
import SearchFilter from "../components/SearchFilter.vue";
import PlaceList from "../components/PlaceList.vue";
import MapContainer from "../components/MapContainer.vue";

// 컴포넌트 등록
const selectedCity = ref("");
const selectedDistrict = ref("");
const selectedType = ref("");
const searchQuery = ref("");
const places = ref([]);
const page = ref(1);
const loading = ref(false);

// 검색 처리 함수
function handleSearch() {
  page.value = 1;
  places.value = [];
  loadMorePlaces();
}

// 장소 불러오기 함수
function loadMorePlaces() {
  if (loading.value) return;
  loading.value = true;

  setTimeout(() => {
    const newPlaces = Array.from({ length: 10 }, (_, i) => ({
      id: (page.value - 1) * 10 + i + 1,
      name: `Place ${(page.value - 1) * 10 + i + 1}`,
      details: "Sample details about the place",
      rating: (Math.random() * 5).toFixed(1),
      host: "Mercedes Vito",
      guests: Math.floor(Math.random() * 4) + 1,
      bedroom: Math.floor(Math.random() * 3) + 1,
      bathroom: Math.floor(Math.random() * 2) + 1,
      image: "https://via.placeholder.com/150",
    }));
    places.value.push(...newPlaces);
    page.value += 1;
    loading.value = false;
  }, 1000);
}

// 컴포넌트가 마운트되었을 때 첫 장소 로드
onMounted(() => {
  loadMorePlaces();
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.main-content {
  display: flex;
}
</style>
