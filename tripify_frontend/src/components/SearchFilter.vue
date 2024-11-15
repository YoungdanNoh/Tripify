<template>
  <section class="search-filter">
    <div class="container mt-3">
      <div class="row mb-2 g-1">
        <!-- VSelect 컴포넌트의 너비를 줄이고, 간격을 줄이기 위해 `col-4` 클래스 사용 -->
        <div class="col-3">
          <VSelect :selectOption="getSidoList" @onKeySelect="onChangeSido" class="w-100" />
        </div>

        <div class="col-3">
          <VSelect :selectOption="getGugunList" @onKeySelect="onChangeGugun" class="w-100" />
        </div>

        <div class="col-3">
          <VSelect :selectOption="getTypeList" @onKeySelect="onChangeType" class="w-100" />
        </div>

        <div class="col-3 d-flex">
          <input
            v-model="keyword"
            type="text"
            class="form-control form-control-sm flex-grow-1"
            placeholder="검색어 입력"
          />
          <button @click="search" class="btn btn-primary ms-2 btn-sm">검색</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { defineProps, defineEmits, onMounted, ref, computed } from 'vue';
import { usePlaceStore } from '@/stores/place';
import { storeToRefs } from 'pinia';
import VSelect from '@/components/common/VSelect.vue';

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const store = usePlaceStore();
const { getSidoList, getGugunList, getTypeList } = storeToRefs(store);
const { listSigun, listGugun, listType } = store;

const emit = defineEmits(['selectedSido', 'selectedGugun', 'selectedType', 'emitSearch']);

const keyword = ref('');

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});

onMounted(async () => {
  await listSigun(); // 데이터 로드
  await listType(); // 데이터 로드
  //console.log(sigun);
});

const onChangeSido = (val) => {
  emit('selectedSido', val);
  listGugun({ sido: val });
};

const onChangeGugun = (val) => {
  emit('selectedGugun', val);
};

const onChangeType = (val) => {
  emit('selectedType', val);
};

const search = () => {
  emit('emitSearch', keyword.value);
};

// // sigun 객체를 배열로 변환
// const sigunArray = computed(() => {
//   return Object.entries(sigun.value).map(([sigun_code, item]) => ({
//     sigun_code, // sigun_code를 키로 그대로 사용
//     sido_name: item.sido_name,
//   }));
// });

// // 구/군 리스트 필터링 (computed로 바로 처리)
// const filteredDistricts = computed(() => {
//   //console.log("Selected City:", selectedCity.value); // selectedCity 값 확인
//   if (!selectedCity.value || !sigun.value) return []; // selectedCity가 없거나 sigun 데이터가 없다면 빈 배열 반환

//   const selected = sigun.value[selectedCity.value]; // selectedCity에 해당하는 sigun 데이터 찾기
//   //console.log("Selected:", selected); // selected 객체 확인

//   return selected ? selected.gugun_name : [];
// });

// 값이 선택될 때마다 부모에게 전달하기
function updateSelectedCity() {
  emit('selectedCity', selectedCity.value);
}
</script>

<style scoped>
.search-filter {
  margin-left: 5px; /* 버튼과 input 사이의 마진 */
}

.search-filter .btn-sm {
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
}
</style>
