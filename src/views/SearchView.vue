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

<script>
import HeaderNav from "../components/HeaderNav.vue";
import SearchFilter from "../components/SearchFilter.vue";
import PlaceList from "../components/PlaceList.vue";
import MapContainer from "../components/MapContainer.vue";

export default {
  components: {
    HeaderNav,
    SearchFilter,
    PlaceList,
    MapContainer,
  },
  data() {
    return {
      selectedCity: "",
      selectedDistrict: "",
      selectedType: "",
      searchQuery: "",
      places: [],
      page: 1,
      loading: false,
    };
  },
  methods: {
    handleSearch() {
      this.page = 1;
      this.places = [];
      this.loadMorePlaces();
    },
    loadMorePlaces() {
      if (this.loading) return;
      this.loading = true;

      setTimeout(() => {
        const newPlaces = Array.from({ length: 10 }, (_, i) => ({
          id: (this.page - 1) * 10 + i + 1,
          name: `Place ${(this.page - 1) * 10 + i + 1}`,
          details: "Sample details about the place",
          rating: (Math.random() * 5).toFixed(1),
          host: "Mercedes Vito",
          guests: Math.floor(Math.random() * 4) + 1,
          bedroom: Math.floor(Math.random() * 3) + 1,
          bathroom: Math.floor(Math.random() * 2) + 1,
          image: "https://via.placeholder.com/150",
        }));
        this.places.push(...newPlaces);
        this.page += 1;
        this.loading = false;
      }, 1000);
    },
  },
  mounted() {
    this.loadMorePlaces();
  },
};
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
