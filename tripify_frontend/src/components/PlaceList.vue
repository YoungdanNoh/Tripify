<template>
  <div class="places-list" @scroll="handleScroll">
    <ul>
      <PlaceItem v-for="place in getPlaces" :key="place.place_id" :place="place"
        @click="showPlaceDetails(place.place_id)" />
    </ul>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import PlaceItem from './PlaceItem.vue';
import { usePlaceStore } from '@/stores/place';
import { storeToRefs } from 'pinia';

const props = defineProps({
  places: Array,
});

const emit = defineEmits(['load-more', 'select-place']);

const store = usePlaceStore();
const { getPlaces } = storeToRefs(store);

function handleScroll(event) {
  const bottom = event.target.scrollHeight - event.target.scrollTop - event.target.clientHeight < 300;
  if (bottom) {
    emit('load-more');
  }
}

function showPlaceDetails(placeId) {
  emit('select-place', placeId);
}
</script>

<style scoped>
.places-list {
  width: 100%;
  height: 80vh;
  overflow-y: auto;
}
</style>