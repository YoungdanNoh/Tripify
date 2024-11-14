<template>
  <section class="search-filter">
    <div class="filter">
      <select :value="selectedCity" @change="updateSelectedCity($event.target.value)">
        <option value="" disabled selected>시/도 선택</option>
        <!-- 시/도 옵션 추가 -->
      </select>
      <select :value="selectedDistrict" @change="updateSelectedDistrict($event.target.value)">
        <option value="" disabled selected>구/군 선택</option>
        <!-- 구/군 옵션 추가 -->
      </select>
      <select :value="selectedType" @change="updateSelectedType($event.target.value)">
        <option value="" disabled selected>관광지 유형 선택</option>
        <!-- 관광지 유형 옵션 추가 -->
      </select>
    </div>
    <div class="keyword">
      <input
        type="text"
        :value="searchQuery"
        @input="updateSearchQuery($event.target.value)"
        placeholder="검색어 입력"
      />
      <button @click="emitSearch">검색</button>
    </div>
  </section>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  selectedCity: String,
  selectedDistrict: String,
  selectedType: String,
  searchQuery: String,
});

const emit = defineEmits([
  "update:selectedCity",
  "update:selectedDistrict",
  "update:selectedType",
  "update:searchQuery",
  "search",
]);

function updateSelectedCity(value) {
  emit("update:selectedCity", value);
}

function updateSelectedDistrict(value) {
  emit("update:selectedDistrict", value);
}

function updateSelectedType(value) {
  emit("update:selectedType", value);
}

function updateSearchQuery(value) {
  emit("update:searchQuery", value);
}

function emitSearch() {
  emit("search");
}
</script>

<style scoped>
.search-filter {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}
</style>
