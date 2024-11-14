<template>
    <section class="search-filter">
        <div class="filter">
            <select :value="selectedCity" @change="$emit('update:selectedCity', $event.target.value)">
                <option disabled selected>시/도 선택</option>
                <!-- 여기 옵션 추가 -->
            </select>
            <select :value="selectedDistrict" @change="$emit('update:selectedDistrict', $event.target.value)">
                <option disabled selected>구/군 선택</option>
                <!-- 여기 옵션 추가 -->
            </select>
            <select :value="selectedType" @change="$emit('update:selectedType', $event.target.value)">
                <option disabled selected>관광지 유형 선택</option>
                <!-- 여기 옵션 추가 -->
            </select>
            <input type="text" :value="searchQuery" @input="$emit('update:searchQuery', $event.target.value)"
                placeholder="검색어 입력" />
            <button @click="$emit('search')">검색</button>
        </div>
        <div class="sorting">
            <button>Free cancellation</button>
            <button>Price</button>
            <button>Instant Book</button>
            <button>기타 옵션</button>
        </div>
    </section>
</template>
  
<script>
export default {
    props: {
        selectedCity: String,
        selectedDistrict: String,
        selectedType: String,
        searchQuery: String,
    },
};
</script>
  
<style scoped>
.search-filter {
    display: flex;
    justify-content: space-between;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}
</style>
  