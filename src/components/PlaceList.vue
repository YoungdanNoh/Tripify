<template>
  <div class="places-list" @scroll="handleScroll">
    <ul>
      <PlaceItem v-for="place in places" :key="place.id" :place="place" />
    </ul>
  </div>
</template>

<script>
import PlaceItem from "./PlaceItem.vue";

export default {
  components: { PlaceItem },
  props: ["places"],
  methods: {
    handleScroll(event) {
      const bottom =
        event.target.scrollHeight - event.target.scrollTop === event.target.clientHeight;
      if (bottom) {
        this.$emit("load-more");
      }
    },
  },
};
</script>

<style scoped>
.places-list {
  width: 50%;
  height: 80vh;
  overflow-y: auto;
}
</style>
