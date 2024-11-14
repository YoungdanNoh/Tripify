<template>
  <div class="places-list" @scroll="handleScroll">
    <ul>
      <PlaceItem v-for="place in places" :key="place.id" :place="place" />
    </ul>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import PlaceItem from "./PlaceItem.vue";

const props = defineProps({
  places: Array,
});

const emit = defineEmits(["load-more"]);

function handleScroll(event) {
  const bottom = event.target.scrollHeight - event.target.scrollTop === event.target.clientHeight;
  if (bottom) {
    emit("load-more");
  }
}
</script>

<style scoped>
.places-list {
  width: 50%;
  height: 80vh;
  overflow-y: auto;
}
</style>
