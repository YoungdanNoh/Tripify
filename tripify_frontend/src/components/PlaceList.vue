<template>
  <div class="places-list" @scroll="handleScroll">
    <ul>
      <PlaceItem v-for="place in getPlaces" :key="place.place_id" :place="place" @click="showPlaceDetails(place)" />
    </ul>
    <PlaceModal v-if="selectedPlace" :place="selectedPlace" @close="closeModal" />
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import PlaceItem from './PlaceItem.vue';
import { usePlaceStore } from '@/stores/place';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';
import PlaceModal from './PlaceModal.vue';

const store = usePlaceStore();
const { getPlaces } = storeToRefs(store);
const selectedPlace = ref(null);

const props = defineProps({
  places: Array,
});

const emit = defineEmits(['load-more']);

function handleScroll(event) {
  const bottom = event.target.scrollHeight - event.target.scrollTop === event.target.clientHeight;
  if (bottom) {
    emit('load-more');
  }
}

function showPlaceDetails(place) {
  selectedPlace.value = place;
}

function closeModal() {
  selectedPlace.value = null;
}

</script>

<style scoped>
.places-list {
  width: 50%;
  height: 80vh;
  overflow-y: auto;
}
</style>
