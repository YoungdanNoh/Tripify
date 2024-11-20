<template>
  <div class="modal-backdrop1" @click.self="close">
    <div class="modal-content">
      <button class="close-button" @click="close">X</button>
      <h3>{{ place.title }}</h3>
      <img :src="place.first_image1 || defaultImage" alt="Place Image" />
      <p><strong>주소:</strong> {{ place.addr1 }} {{ place.addr2 }}</p>
      <p>
        <strong>설명:</strong> {{ place.description || "설명이 없습니다." }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import defaultImage from "@/assets/noImage.png";

const props = defineProps({
  place: Object,
});

const emit = defineEmits(["close"]);

function close() {
  emit("close");
}
</script>

<style scoped>
.modal-backdrop1 {
  --bs-backdrop-opacity: 1; /* 불투명하게 설정 */
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background-color: white; /* 완전히 불투명한 배경 */
  border-radius: 8px;
  padding: 20px;
  max-width: 500px;
  width: 90%;
  position: relative;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 */
}

.modal-content img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 15px;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
}
</style>
